package co.za.tinycinema.data.remote;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.R;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.MoviesInTheatresModel;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetReviews.Domain.model.ReviewResponseModel;
import co.za.tinycinema.features.ShowDetails.domain.model.Videos;
import co.za.tinycinema.utils.AppExecutors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {

    private final Service service;
    private MutableLiveData<List<MovieResultEntity>> results;
    private MutableLiveData<List<MovieResultEntity>> resultsTopRated;
    private MutableLiveData<List<co.za.tinycinema.features.GetReviews.Domain.model.Result>> resultsOfReviews;
    private List<MovieResultEntity> rawResults;
    private List<co.za.tinycinema.features.GetReviews.Domain.model.Result> rawResultsReviews;
    private List<co.za.tinycinema.features.ShowDetails.domain.model.Result> rawResultsVideos;
    private final Context mContext;
    //   private static final String LOG_TAG = MoviesSyncIntentService.class.getSimpleName();
    private static RemoteDataSource sInstance = null;
    private static final Object LOCK = new Object();
    String videoId;

    @Nullable
    private Call<MoviesInTheatresModel> mCall;

    private Call<ReviewResponseModel> mCallReviews;
    private Call<Videos> mCallVideos;

    public RemoteDataSource(Service service, Context context) {
        this.service = service;
        results = new MutableLiveData<>();
        resultsOfReviews = new MutableLiveData<>();
        resultsTopRated = new MutableLiveData<>();
        rawResults = new ArrayList<>();
        rawResultsReviews = new ArrayList<>();
        rawResultsVideos = new ArrayList<>();
        this.mContext = context;
    }

    public LiveData<List<MovieResultEntity>> getCurrenrMoviesInTheatres() {
        return results;
    }


    public LiveData<List<MovieResultEntity>> getAllMoviesInTheatre() {
        mCall = service.getMoviesInTheatres("");
        mCall.enqueue(new Callback<MoviesInTheatresModel>() {
            @Override
            public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        // update the value held in results with the new data.
                        // do this using postValue() since the call will be done off of the main thread.
                        for (Result result : response.body().getResults()) {
                            rawResults.add(transform(result));
                        }
                        results.postValue(rawResults);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                // callback.onDataNotAvailable(context.getString(R.string.no_data_available));
            }
        });
        return results;
    }


//    public void getAllMoviesInTheatre(Context context, final LoadInfoCallback callback) {
//            mCall = service.getMoviesInTheatres("");
//            mCall.enqueue(new Callback<MoviesInTheatresModel>() {
//                @Override
//                public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
//                    if (response.body() != null) {
//                        if (response.isSuccessful()) {
//                            // update the value held in results with the new data.
//                            // do this using postValue() since the call will be done off of the main thread.
//                            for (Result result:response.body().getResults()) {
//                                rawResults.add(transform(result));
//                            }
//
//                            callback.onDataLoaded(rawResults,false);
//                         //   results.postValue(rawResults);
//
//                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
//                   // callback.onDataNotAvailable(context.getString(R.string.no_data_available));
//                }
//            });
//    }


    public MovieResultEntity transform(Result result) {
        MovieResultEntity movieResultEntity = null;
        if (result != null) {
            movieResultEntity = new MovieResultEntity();
            movieResultEntity.setId(result.getId());
          //  movieResultEntity.setAdult(result.getAdult());
            movieResultEntity.setBackdropPath(result.getBackdropPath());
            movieResultEntity.setOriginalLanguage(result.getOriginalLanguage());
            movieResultEntity.setOriginalTitle(result.getOriginalTitle());
            movieResultEntity.setOverview(result.getOverview());
            movieResultEntity.setPopularity(result.getPopularity());
            movieResultEntity.setPosterPath(result.getPosterPath());
            movieResultEntity.setReleaseDate(result.getReleaseDate());
            movieResultEntity.setTitle(result.getTitle());
            movieResultEntity.setVoteAverage(result.getVoteAverage());
            movieResultEntity.setToprated("0");
            movieResultEntity.setFavourite("0");
            movieResultEntity.setToWatch("0");
        }
        return movieResultEntity;
    }


    /**
     * Get the singleton for this class
     */
    public static RemoteDataSource getInstance(Service service, Context context) {
        //  Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RemoteDataSource(service, context.getApplicationContext());
                //        Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    /**
     * Schedules a repeating job service which fetches the weather.
     */
    public void scheduleRecurringFetchMoviesrSync() {
//        Driver driver = new GooglePlayDriver(mContext);
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
//
//        // Create the Job to periodically sync Sunshine
//        Job syncSunshineJob = dispatcher.newJobBuilder()
//                /* The Service that will be used to sync Sunshine's data */
//                .setService(SunshineFirebaseJobService.class)
//                /* Set the UNIQUE tag used to identify this Job */
//                .setTag(SUNSHINE_SYNC_TAG)
//                /*
//                 * Network constraints on which this Job should run. We choose to run on any
//                 * network, but you can also choose to run only on un-metered networks or when the
//                 * device is charging. It might be a good idea to include a preference for this,
//                 * as some users may not want to download any data on their mobile plan. ($$$)
//                 */
//                .setConstraints(Constraint.ON_ANY_NETWORK)
//                /*
//                 * setLifetime sets how long this job should persist. The options are to keep the
//                 * Job "forever" or to have it die the next time the device boots up.
//                 */
//                .setLifetime(Lifetime.FOREVER)
//                /*
//                 * We want Sunshine's weather data to stay up to date, so we tell this Job to recur.
//                 */
//                .setRecurring(true)
//                /*
//                 * We want the weather data to be synced every 3 to 4 hours. The first argument for
//                 * Trigger's static executionWindow method is the start of the time frame when the
//                 * sync should be performed. The second argument is the latest point in time at
//                 * which the data should be synced. Please note that this end time is not
//                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
//                 */
//                .setTrigger(Trigger.executionWindow(
//                        SYNC_INTERVAL_SECONDS,
//                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
//                /*
//                 * If a Job with the tag with provided already exists, this new job will replace
//                 * the old one.
//                 */
//                .setReplaceCurrent(true)
//                /* Once the Job is ready, call the builder's build method to return the Job */
//                .build();
//
//        // Schedule the Job with the dispatcher
//        dispatcher.schedule(syncSunshineJob);
//        Log.d(LOG_TAG, "Job scheduled");
    }


    public LiveData<List<MovieResultEntity>> getTopRatedMovies() {
        rawResults.clear();
        mCall = service.getTopVotedMoviesInTheatres("");
        mCall.enqueue(new Callback<MoviesInTheatresModel>() {
            @Override
            public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        for (Result result : response.body().getResults()) {
                            rawResults.add(transform(result));
                        }

                        resultsTopRated.postValue(rawResults);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                // callback.onDataNotAvailable(context.getString(R.string.no_data_available));
            }
        });
        return resultsTopRated;
    }


    @Override
    public void deleteMovie(Integer entity) {

    }

    @Override
    public void saveMovie(MovieResultEntity result, SaveInfoCallback callback) {

    }

    @Override
    public void deleteMovieFromLibrary(MovieResultEntity entity, DeleteInfoCallback callback) {

    }

    public void startFetchMoviesService() {
        Intent intentToFetch = new Intent(mContext, MoviesSyncIntentService.class);
        mContext.startService(intentToFetch);
        // Log.d(LOG_TAG, "Service created");
    }

    public LiveData<List<co.za.tinycinema.features.GetReviews.Domain.model.Result>> getReviews(int movieId) {
        rawResultsReviews.clear();
        mCallReviews = service.getReviews(movieId, "");
        mCallReviews.enqueue(new Callback<ReviewResponseModel>() {
            @Override
            public void onResponse(Call<ReviewResponseModel> call, Response<ReviewResponseModel> response) {
                if(response.body() != null) {
                    if (response.isSuccessful()) {
                        rawResultsReviews.addAll(response.body().getResults());
                    }
                        resultsOfReviews.postValue(rawResultsReviews);
                    }

            }

            @Override
            public void onFailure(Call<ReviewResponseModel> call, Throwable t) {

            }
        });
        return resultsOfReviews;
    }

    public String getVideoId(Integer id, final GetVideoIdCallback callback) {
        rawResultsVideos.clear();
        mCallVideos = service.getVideos(id, "");
        mCallVideos.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                if(response.body() != null) {
                    if (response.isSuccessful()) {
                        rawResultsVideos.addAll(response.body().getResults());
                        if(!rawResultsVideos.get(0).getKey().isEmpty())
                            callback.getIdSuccess(videoId = rawResultsVideos.get(0).getKey());
                    }
                    else{
                        callback.getIdFailed("nothing to see");

                    }
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                callback.getIdFailed("nothing to see");
            }
        });
        return videoId;
    }
}
