package co.za.tinycinema.features.GetReviews.Domain.usecase;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;

import co.za.tinycinema.data.Repository;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;
import co.za.tinycinema.features.GetReviews.Domain.model.Result;

public class GetReviews {

    private Repository repository;
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = GetReviews.class.getSimpleName();
    public static GetReviews sInstance = null;

    public GetReviews(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Result>> executeGetReviewsUseCase(int movieId){
        return repository.getReviews(movieId);
    }

    /**
     * Get the singleton for this class
     */
    public static GetReviews getInstance(Repository repository) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new GetReviews(repository);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }
}
