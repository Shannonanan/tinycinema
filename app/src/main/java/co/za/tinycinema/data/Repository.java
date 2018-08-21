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

    Context mContext;
    ImageLoader imageLoader;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    public Map<String, Result> mResults;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    public Repository(RemoteDataSource mRemoteDataSource,
                      LocalDataSource mLocalDataSource,
                      Context context
    ) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
     //   this.imageLoader = imageLoader;
        this.mContext = context;
    }


    //check for internet, if none call to local to see if any saved data in the local repository, if none alert user
    @Override
    public void getAllMoviesInTheatre(final String date, final LoadInfoCallback callback) {
        //check internet
        //if exists call remote
        if(isThereInternetConnection()){
        getRemoteMoviesInTheatres(date, callback);}
        else {
            // Query the local storage if available.
            mLocalDataSource.getAllMoviesInTheatre(date, new LoadInfoCallback() {
                @Override
                public void onDataLoaded(List<Result> mMovieResultPosters) {
                    // refreshCache(tasks);
                    callback.onDataLoaded(new ArrayList<>(mMovieResultPosters));
                }

                @Override
                public void onDataNotAvailable() {
                    getRemoteMoviesInTheatres(date, callback);
                }
            });
        }
//test ci
        //if empty alert user

        // Respond immediately with cache if available and not dirty
        if (mResults != null && !mCacheIsDirty) {
            callback.onDataLoaded(new ArrayList<>(mResults.values()));
            return;
        }

        if (mCacheIsDirty) {
         //    If the cache is dirty we need to fetch new data from the network.

        }
        else {


        }
    }

    @Override
    public void getMostPopularMovies(LoadInfoCallback callback) {

    }

    @Override
    public void getHighestRatedMovies(LoadInfoCallback callback) {

    }


    private void getRemoteMoviesInTheatres(String date, @NonNull final LoadInfoCallback callback) {
        mRemoteDataSource.getAllMoviesInTheatre(date, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> movieResults) {
                refreshCache(movieResults);
            //    refreshLocalDataSource(info);
                callback.onDataLoaded(new ArrayList<>(movieResults));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Result> listInfo) {
        //TODO: fix cache
        if (mResults == null) {
            mResults = new LinkedHashMap<>();
        }
        mResults.clear();
        mCacheIsDirty = false;
    }

//    private void refreshLocalDataSource(List<EarthInfoPojos> marbles) {
//        mLocalDataSource.deleteAllInfo();
//        List<EarthInfoObjEnhanced> marbless =  new ArrayList<>(convertSchemaToEntity(marbles));
//        for (EarthInfoObjEnhanced info : marbless) {
//            mLocalDataSource.saveTask(info);
//        }
//    }

//    private List<EarthInfoObjEnhanced> convertSchemaToEntity(List<EarthInfoPojos> earthInfoSchema) {
//        List<EarthInfoObjEnhanced> info = new ArrayList<>(earthInfoSchema.size());
//        for (EarthInfoPojos schema : earthInfoSchema) {
//            info.add(new EarthInfoObjEnhanced(schema.getIdentifier(),schema.getCaption(),schema.getImage(),
//                    schema.getVersion(), schema.getDate()));
//        }
//        return info;
//    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }


    @Override
    public void deleteAllInfo() {
        mRemoteDataSource.deleteAllInfo();
        mLocalDataSource.deleteAllInfo();

//        if (mCachedEarthInfo == null) {
//            mCachedEarthInfo = new LinkedHashMap<>();
//        }
//        mCachedEarthInfo.clear();
    }

    @Override
    public void saveTask(Result marbles) {
       // checkNotNull(marbles);
        mRemoteDataSource.saveTask(marbles);
        mLocalDataSource.saveTask(marbles);

        // Do in memory cache update to keep the app UI up to date
//        if (mCachedEarthInfo == null) {
//            mCachedEarthInfo = new LinkedHashMap<>();
//        }

//        EarthInfoPojos schema = new EarthInfoPojos(marbles.getIdentifier(),
//                marbles.getCaption(),marbles.getImage(),
//                marbles.getVersion(), marbles.getDate());
//        mCachedEarthInfo.put(marbles.getIdentifier(), schema);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *used for testing
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