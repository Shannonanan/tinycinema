package co.za.tinycinema.data.local;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.utils.AppExecutors;

public class LocalDataSource implements DataSource {
    private MoviesDao moviesDao;
    private DateDao dateDao;

    private final List<Integer> listOfmOVIES;

    private final AppExecutors mExecutors;
    Context context;
    private static LocalDataSource sInstance = null;
    private static final Object LOCK = new Object();

    public LocalDataSource(MoviesDao moviesDao,DateDao dateDao,
                           AppExecutors mExecutors) {
        this.moviesDao = moviesDao;
        this.mExecutors = mExecutors;
        this.dateDao = dateDao;
        listOfmOVIES = new ArrayList<>();
    }


    public void bulkInsert(List<MovieResultEntity>movieResultEntities){
        moviesDao.bulkInsert(movieResultEntities);
    }

    public List<MovieResultEntity> getAllMoviesNow(){
        return moviesDao.getAllMoviesNow();
    }


    //Livedata makes it asynchronous, before I created a new runnable to pass to an Executor method
    public LiveData<List<MovieResultEntity>> getAllMoviesInTheatres() {
        return moviesDao.getAllMovies(false, false, false);
    }

    public int checkDate(final Date date){
        return dateDao.checkDate(date);
    }

    public void checkMovieSaved(final Integer entityId, final SavedMovieToLibraryCallback saveInfoCallback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               Integer checkCount =  moviesDao.checkMovieWasSaved(entityId);
                saveInfoCallback.savedStatusSuccess(checkCount == 1);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void addDateSaved(DateSavedEntity date){
        dateDao.insertDate(date);
    }

//    public void getMoviesFromLibrary(final LoadInfoCallback loadInfoCallback){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
//                List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
//                loadInfoCallback.onDataLoaded(transformedFromLocal, true);
//            }
//        };
//        mExecutors.diskIO().execute(runnable);
//    }

    private List<Result> transformToSchema(List<MovieResultEntity> movieResultEntities) {
        List<Result> transfromedFromLocal = null;
        if(movieResultEntities != null){
            transfromedFromLocal = new ArrayList<>();
            for (MovieResultEntity entity: movieResultEntities) {
                transfromedFromLocal.add(new Result(entity.getVoteCount(), entity.getId(), entity.getVideo(), entity.getVoteAverage(),
                        entity.getTitle(), entity.getPopularity(), entity.getPosterPath(),entity.getOriginalLanguage(),
                        entity.getOriginalTitle(), entity.getBackdropPath(), entity.getAdult(),
                        entity.getOverview(), entity.getReleaseDate(), true));
            }
        }

        return transfromedFromLocal;
    }


//    @Override
//    public void getAllMoviesInTheatre(Context context, LoadInfoCallback callback) {
//
//    }


    public void getHighestRatedMovies(Context context, final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
             //   List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(true);
             //   List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
            //    callback.onDataLoaded(transformedFromLocal, true);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteMovie(final boolean type, final MovieResultEntity entity, final DeleteInfoCallback callback) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
             //   List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(type);
              //  List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess("success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }

    public void deleteMovieFromLibrary(final MovieResultEntity entity, final DeleteInfoCallback callback){
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
              //  List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
               // List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess("success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }


    @Override
    public void saveMovie(final MovieResultEntity result, final SaveInfoCallback callback) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                result.setFavourite(true);
                result.setToWatch(false);
                result.setToprated(false);
                saveMovieNoCallBack(result);
           //  long rownumber =  moviesDao.insertMovie(result);
            callback.savedStatusSuccess("success");
            }
        };
        mExecutors.diskIO().execute(saveRunnable);
    }




//    private MovieLibraryEntity transform(MovieResultEntity result) {
//        MovieLibraryEntity movieLibraryEntity = null;
//        if(result != null){
//            movieLibraryEntity = new MovieLibraryEntity();
//            movieLibraryEntity.setId(result.getId());
//            movieLibraryEntity.setAdult(result.getAdult());
//            movieLibraryEntity.setBackdropPath(result.getBackdropPath());
//            movieLibraryEntity.setOriginalLanguage(result.getOriginalLanguage());
//            movieLibraryEntity.setOriginalTitle(result.getOriginalTitle());
//            movieLibraryEntity.setOverview(result.getOverview());
//            movieLibraryEntity.setPopularity(result.getPopularity());
//            movieLibraryEntity.setPosterPath(result.getPosterPath());
//            movieLibraryEntity.setReleaseDate(result.getReleaseDate());
//            movieLibraryEntity.setTitle(result.getTitle());
//            movieLibraryEntity.setVoteAverage(result.getVoteAverage());
//            movieLibraryEntity.setToprated(false);
//        }
//        return movieLibraryEntity;
//    }

//    private List<MovieResultEntity> transformList(List<MovieLibraryEntity> result) {
//        List<MovieResultEntity> movieResultEntity =new ArrayList<>();
//        if(result != null){
//            for (MovieLibraryEntity entity: result) {
//                MovieResultEntity movieResultEntity1 = new MovieResultEntity();
//                movieResultEntity1.setId(entity.getId());
//                movieResultEntity1.setAdult(entity.getAdult());
//                movieResultEntity1.setBackdropPath(entity.getBackdropPath());
//                movieResultEntity1.setOriginalLanguage(entity.getOriginalLanguage());
//                movieResultEntity1.setOriginalTitle(entity.getOriginalTitle());
//                movieResultEntity1.setOverview(entity.getOverview());
//                movieResultEntity1.setPopularity(entity.getPopularity());
//                movieResultEntity1.setPosterPath(entity.getPosterPath());
//                movieResultEntity1.setReleaseDate(entity.getReleaseDate());
//                movieResultEntity1.setTitle(entity.getTitle());
//                movieResultEntity1.setVoteAverage(entity.getVoteAverage());
//                movieResultEntity1.setToprated(false);
//                movieResultEntity.add(movieResultEntity1);
//            }
//
//        }
//        return movieResultEntity;
//    }


    /**
     * Get the singleton for this class
     */
    public static LocalDataSource getInstance(MoviesDao moviesDao,DateDao dateDao,
                                              AppExecutors mExecutors) {
      //  Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalDataSource(moviesDao,dateDao,mExecutors);
               // Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    public LiveData<List<MovieResultEntity>> getAllMoviesFromLibrary(Boolean fav) {
        return moviesDao.getAllMovies(false,false,false);
    }


    public void saveMovieNoCallBack(MovieResultEntity result) {
        moviesDao.insertMovie(result);
    }
}
