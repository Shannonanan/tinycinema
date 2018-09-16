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
    MoviesDao moviesDao;
    DateDao dateDao;
    private final AppExecutors mExecutors;
    Context context;
    private static LocalDataSource sInstance = null;
    private static final Object LOCK = new Object();

    public LocalDataSource(MoviesDao moviesDao,DateDao dateDao, AppExecutors mExecutors) {
        this.moviesDao = moviesDao;
        this.mExecutors = mExecutors;
        this.dateDao = dateDao;
    }


    public void bulkInsert(List<MovieResultEntity>movieResultEntities){
        moviesDao.bulkInsert(movieResultEntities);
    }


    //Livedata makes it asynchronous, before I created a new runnable to pass to an Executor method
    public LiveData<List<MovieResultEntity>> getAllMoviesInTheatres() {
        return moviesDao.getAllMovies(false);
    }

    public void checkDate(final Date date, final LoadDateCheckCallback loadDateCheckCallback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadDateCheckCallback.onDatesCheckedLoaded(dateDao.checkDate(date));
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

    @Override
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
    public void getMoviesFromLibrary(LoadInfoCallback callback) {

    }

    @Override
    public void deleteMovie(final boolean type, final MovieResultEntity entity, final DeleteInfoCallback callback) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
             //   List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(type);
              //  List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
              //  callback.deleteStatusSuccess(latestResults,"success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }

    public void deleteMovieFromLibrary(final MovieResultEntity entity, final DeleteInfoCallback callback){
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
                List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess(latestResults,"success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }


    @Override
    public void saveMovie(final MovieResultEntity result, final SaveInfoCallback callback) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
             long getResponse = moviesDao.insertMovie(result);
            callback.savedStatusSuccess("success");
            }
        };
        mExecutors.diskIO().execute(saveRunnable);
    }


    /**
     * Get the singleton for this class
     */
    public static LocalDataSource getInstance(MoviesDao moviesDao,DateDao dateDao, AppExecutors mExecutors) {
      //  Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalDataSource(moviesDao,dateDao,mExecutors);
               // Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


}
