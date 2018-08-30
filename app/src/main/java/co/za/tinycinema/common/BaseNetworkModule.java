package co.za.tinycinema.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public abstract class BaseNetworkModule implements DataSource {

    public final RemoteDataSource mRemoteDataSource;

    public final LocalDataSource mLocalDataSource;

    public Context mContext;


    public BaseNetworkModule(RemoteDataSource mRemoteDataSource,
                             LocalDataSource mLocalDataSource,
                             Context context
    ) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        this.mContext = context;
    }


    @Override
    public abstract void getAllMoviesInTheatre(Context context, LoadInfoCallback callback);


    public void getLocalMoviesInTheatres(@NonNull final LoadInfoCallback callback, Context context) {
        mLocalDataSource.getAllMoviesInTheatre(context, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> mMovieResultPosters, boolean offlne) {
                // refreshCache(tasks);
                callback.onDataLoaded(new ArrayList<>(mMovieResultPosters), true);
            }

            @Override
            public void onDataNotAvailable(String noDataAvailable) {
                callback.onDataNotAvailable(noDataAvailable);
            }
        });
    }

    public void getRemoteMoviesInTheatres(@NonNull final LoadInfoCallback callback, Context context) {
        mRemoteDataSource.getAllMoviesInTheatre(context, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> movieResults, boolean offline) {
                //    refreshLocalDataSource(info);
                callback.onDataLoaded(new ArrayList<>(movieResults), offline);
            }

            @Override
            public void onDataNotAvailable(String noDataAvailable) {
                callback.onDataNotAvailable(noDataAvailable);
            }
        });
    }

    @Override
    public abstract void getHighestRatedMovies(Context context, LoadInfoCallback callback);


    public void getHighestRatedMoviesRemote(Context context, final LoadInfoCallback callback) {
        mRemoteDataSource.getHighestRatedMovies(context, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> results, boolean offline) {
                if (results != null) {
                    callback.onDataLoaded(results, offline);
                }
            }

            @Override
            public void onDataNotAvailable(String noDataAvailable) {
                callback.onDataNotAvailable(noDataAvailable);
            }
        });
    }

    public void getHighestRatedMoviesLocal(Context context, final LoadInfoCallback callback) {
        mLocalDataSource.getHighestRatedMovies(context, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> mMovieResultPosters, boolean offline) {
                // refreshCache(tasks);
                callback.onDataLoaded(new ArrayList<>(mMovieResultPosters), offline);
            }

            @Override
            public void onDataNotAvailable(String noDataAvailable) {
                callback.onDataNotAvailable(noDataAvailable);
            }
        });
    }

    @Override
    public abstract void getMoviesFromLibrary(final LoadInfoCallback callback);

    public void getMoviesFromLibraryLocal(final LoadInfoCallback callback) {
        mLocalDataSource.getMoviesFromLibrary(new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> mMovieResultPosters, boolean offlne) {
                // refreshCache(tasks);
                callback.onDataLoaded(new ArrayList<>(mMovieResultPosters), true);
            }

            @Override
            public void onDataNotAvailable(String noDataAvailable) {
                callback.onDataNotAvailable(noDataAvailable);
            }
        });
    }

    @Override
    public abstract void deleteMovie(boolean type, MovieResultEntity entity, DeleteInfoCallback callback);

    public void deleteMovieLocalWithType(boolean type, MovieResultEntity entity, final DeleteInfoCallback callback) {
        mLocalDataSource.deleteMovie(type, entity, new DeleteInfoCallback() {
            @Override
            public void deleteStatusSuccess(List<Result> latestResults, String status) {
                callback.deleteStatusSuccess(latestResults, status);
            }

            @Override
            public void deleteStatusFailed(String status) {

            }
        });
    }

    @Override
    public abstract void saveMovie(MovieResultEntity result, SaveInfoCallback callback);

    public void saveMovieLocal(MovieResultEntity result, final SaveInfoCallback callback) {
        mLocalDataSource.saveMovie(result, new SaveInfoCallback() {
            @Override
            public void savedStatusSuccess(String status) {
                callback.savedStatusSuccess(status);
            }

            @Override
            public void savedStatusFailed(String error) {
                callback.savedStatusFailed(error);
            }
        });
    }

    @Override
    public abstract void deleteMovieFromLibrary(MovieResultEntity entity, DeleteInfoCallback callback);

    public void deleteMovieFromLibraryWithoutType(MovieResultEntity entity, final DeleteInfoCallback callback) {
        mLocalDataSource.deleteMovieFromLibrary(entity, new DeleteInfoCallback() {
            @Override
            public void deleteStatusSuccess(List<Result> latestResults, String status) {
                callback.deleteStatusSuccess(latestResults, status);
            }

            @Override
            public void deleteStatusFailed(String status) {

            }
        });
    }


}
