package co.za.tinycinema.features.Library.domain.usecase;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import java.util.List;

import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;



public class GetMoviesFromLibrary {

    private Repository repository;
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = GetMoviesFromLibrary.class.getSimpleName();
    public static GetMoviesFromLibrary sInstance = null;

    public GetMoviesFromLibrary(Repository repository) {
        this.repository = repository;
    }


    public LiveData<List<MovieResultEntity>> executeUseCase(boolean fav) {
       return this.repository.getMoviesFromLibrary(fav);
    }



    /**
     * Get the singleton for this class
     */
    public static GetMoviesFromLibrary getInstance(Repository repository) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new GetMoviesFromLibrary(repository);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

}
