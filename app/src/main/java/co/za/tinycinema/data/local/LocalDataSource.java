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

  //  private final AppExecutors mExecutors;
    Context context;
    private static LocalDataSource sInstance = null;
    private static final Object LOCK = new Object();

    public LocalDataSource(MoviesDao moviesDao,DateDao dateDao
                          // AppExecutors mExecutors
    ) {
        this.moviesDao = moviesDao;
      //  this.mExecutors = mExecutors;
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
        return moviesDao.getAllMovies("0", "0", "0");
    }

    public int checkDate(final Date date){
        return dateDao.checkDate(date);
    }

    public void checkMovieSaved(Integer entityId, final SavedMovieToLibraryCallback saveInfoCallback){
                Integer checkCount =  moviesDao.checkMovieWasSaved(entityId);
                saveInfoCallback.savedStatusSuccess(checkCount == 1);
    }

    public void test(Integer entityId, SavedMovieToLibraryCallback saveInfoCallback){
        Integer checkCount =  moviesDao.checkMovieWasSaved(entityId);
        saveInfoCallback.savedStatusSuccess(checkCount == 1);
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




    public void getHighestRatedMovies(Context context, final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
             //   List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(true);
             //   List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
            //    callback.onDataLoaded(transformedFromLocal, true);
            }
        };
      //  mExecutors.diskIO().execute(runnable);
    }



    public void deleteMovieFromLibrary(final MovieResultEntity entity, final DeleteInfoCallback callback){
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity.getId());
              //  List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
               // List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess("success");
            }
        };

       // mExecutors.diskIO().execute(deleteRunnable);
    }


    @Override
    public void saveMovie(final MovieResultEntity result, final SaveInfoCallback callback) {
//        Runnable saveRunnable = new Runnable() {
//            @Override
//            public void run() {
//                result.setFavourite("1");
//                saveMovieNoCallBackInsert(result);
//           //  long rownumber =  moviesDao.insertMovie(result);
//            callback.savedStatusSuccess("success");
//            }
//        };
//        mExecutors.diskIO().execute(saveRunnable);

     //   mExecutors.diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                moviesDao.insertMovie(result);
//              //  saveMovieNoCallBackInsert(result);
//            }
//        });
    }


    /**
     * Get the singleton for this class
     */
    public static LocalDataSource getInstance(MoviesDao moviesDao,DateDao dateDao
                                            //  AppExecutors mExecutors
    ) {
      //  Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalDataSource(moviesDao,dateDao);
               // Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    public LiveData<List<MovieResultEntity>> getAllMoviesFromLibrary(Boolean fav) {
        return moviesDao.getAllMoviesInLibrary("1");
    }


    public void saveMovieNoCallBack(MovieResultEntity result) {
        moviesDao.update(result);
    }


    public void saveMovieNoCallBackInsert(MovieResultEntity result) {
        moviesDao.insertMovie(result);
    }

    @Override
    public void deleteMovie(Integer entity) {
        int deleted = moviesDao.deleteMovie(entity);
        String test = ";";
    }
}
