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

import co.za.tinycinema.common.BaseNetworkModule;
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
public class Repository extends BaseNetworkModule {

    private static Repository INSTANCE = null;

    public Repository(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource, Context context) {
        super(mRemoteDataSource, mLocalDataSource, context);
    }

    //check for internet, if none call to local to see if any saved data in the local repository, if none alert user
    @Override
    public void getAllMoviesInTheatre(Context context, LoadInfoCallback callback) {

        if (isThereInternetConnection()) {
            getRemoteMoviesInTheatres(callback, mContext);
        } else {
            // Query the local storage if available.
            getLocalMoviesInTheatres(callback, mContext);
        }
    }

    @Override
    public void getHighestRatedMovies(Context context, final LoadInfoCallback callback) {
        if (isThereInternetConnection()) {
            getHighestRatedMoviesRemote(context, callback);
        } else {
            // Query the local storage if available.
            getHighestRatedMoviesLocal(context, callback);
        }
    }

    @Override
    public void getMoviesFromLibrary(final LoadInfoCallback callback) {
        getMoviesFromLibraryLocal(callback);
    }


    @Override
    public void deleteMovie(boolean type, MovieResultEntity entity, final DeleteInfoCallback callback) {
        deleteMovieLocalWithType(type, entity, callback);
    }

    public void deleteMovieFromLibrary(MovieResultEntity entity, final DeleteInfoCallback callback) {
        deleteMovieFromLibraryWithoutType(entity, callback);
    }

    @Override
    public void saveMovie(MovieResultEntity result, final SaveInfoCallback callback) {
        saveMovieLocal(result, callback);
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