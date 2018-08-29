/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.za.tinycinema.data;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.ImageLoader;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class Repository implements DataSource {

    private static Repository INSTANCE = null;

    private final RemoteDataSource mRemoteDataSource;

    private final LocalDataSource mLocalDataSource;

    private Context mContext;


    public Repository(RemoteDataSource mRemoteDataSource,
                      LocalDataSource mLocalDataSource,
                      Context context
    ) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        this.mContext = context;
    }

    //TODO make  a delete function and check why it crashes when going to detail when offline


    //check for internet, if none call to local to see if any saved data in the local repository, if none alert user
    @Override
    public void getAllMoviesInTheatre(Context context, final LoadInfoCallback callback) {
        //check internet
        //if exists call remote
        if (isThereInternetConnection()) {
            getRemoteMoviesInTheatres(callback, mContext);
        } else {
            // Query the local storage if available.
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

    }

    @Override
    public void getHighestRatedMovies(Context context, final LoadInfoCallback callback) {
        if (isThereInternetConnection()) {
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
        } else {
            // Query the local storage if available.
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
    }

    @Override
    public void getMoviesFromLibrary(final LoadInfoCallback callback) {
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


    private void getRemoteMoviesInTheatres(@NonNull final LoadInfoCallback callback, Context context) {
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
    public void deleteMovie(boolean type, MovieResultEntity entity, final DeleteInfoCallback callback) {
        //   mRemoteDataSource.deleteAllInfo();
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

    public void deleteMovieFromLibrary(MovieResultEntity entity, final DeleteInfoCallback callback) {
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

    @Override
    public void saveMovie(MovieResultEntity result, final SaveInfoCallback callback) {

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


    /**
     * Returns the single instance of this class, creating it if necessary.
     * used for testing
     *
     * @param remoteDataSource the backend data source
     * @param localDataSource  the device storage data source
     * @return the {@link Repository} instance
     */
    public static Repository getInstance(RemoteDataSource remoteDataSource,
                                         LocalDataSource localDataSource, ImageLoader imageLoader, Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource, context);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RemoteDataSource, LocalDataSource, ImageLoader, Context)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }


}