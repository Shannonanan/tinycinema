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


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.airbnb.lottie.L;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.za.tinycinema.data.local.DateSavedEntity;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.ImageLoader;
import co.za.tinycinema.utils.AppExecutors;
import co.za.tinycinema.utils.MoviesDateUtils;


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class Repository {

    private static final String LOG_TAG = Repository.class.getSimpleName();

    private final AppExecutors mExecutors;
    private static Repository INSTANCE = null;
    private RemoteDataSource mRemoteDataSource;
    // if two methods see the same local variable, Java wants you to swear you will not change it ie.final
    private  LocalDataSource mLocalDataSource;
    private Context mContext;
    private boolean mInitialized = false;

    public Repository(RemoteDataSource mRemoteDataSource, final LocalDataSource mLocalDataSource, Context context,
                      AppExecutors executors) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        this.mContext = context;
        this.mExecutors = executors;

        LiveData<List<MovieResultEntity>> networkData = mRemoteDataSource.getCurrenrMoviesInTheatres();
        //use the observeForever method to observe mResults
        networkData.observeForever(new Observer<List<MovieResultEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MovieResultEntity> movieResultEntities) {
                //Note that database operations must be done off of the main thread.
                // Use your AppExecutor's disk I/O executor to provide the appropriate thread:
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //trigger a database save.
                      //  deleteOldData();
                        //saving todays date so next time you pull data, if it is still the same day you only need to pull
                        //data locally
                        Date today = MoviesDateUtils.getNormalizedUtcDateForToday();
                        DateSavedEntity entity = new DateSavedEntity(today);
                        mLocalDataSource.addDateSaved(entity);
                        mLocalDataSource.bulkInsert(movieResultEntities);
                        Log.d(LOG_TAG, "New values inserted");
                    }
                });
            }
        });
    }



    public LiveData<List<MovieResultEntity>>  getAllMoviesInTheatre(){
        if (isThereInternetConnection() && isFetchNeeded()) {
        return  mRemoteDataSource.getAllMoviesInTheatre();}
        else{
            return  mLocalDataSource.getAllMoviesInTheatres();
        }

    }

    //check for internet, if none call to local to see if any saved data in the local repository, if none alert user

    //adding a single source of truth, never pull directly from the network to display
    //pull from the local, where the data has been saved
//    public void getAllMoviesInTheatre(Context context, final DataSource.LoadInfoCallback loadInfoCallback) {
//        if (isThereInternetConnection()) {
//           // initializeData();
//             mRemoteDataSource.getAllMoviesInTheatre(context, new DataSource.LoadInfoCallback() {
//               @Override
//               public void onDataLoaded(List<MovieResultEntity> results, boolean offline) {
//                   loadInfoCallback.onDataLoaded(results,false);
//               }
//
//               @Override
//               public void onDataNotAvailable(String noDataAvailable) {
//
//               }
//           });
//       //     return  null;
//                    //mLocalDataSource.getAllMoviesInTheatres(context);
//        }else{
//            ;
//        }
//        // Query the local storage if available.
//
//
//    }

    public void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        // This method call triggers the app to create its task to synchronize movie data
        // periodically.
     //   mRemoteDataSource.scheduleRecurringFetchMoviesrSync();

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
//                if(isFetchNeeded()){
//                    startFetchMoviesService();}
            }
    });

    }

    private void startFetchMoviesService() {
        mRemoteDataSource.startFetchMoviesService();
    }



    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private void isFetchNeeded(final DataSource.LoadDateCheckCallback loadDateCheckCallback) {
        Log.d(LOG_TAG, "counting data");
        //trigger a count to see if data has been pulled for today
        Date today = MoviesDateUtils.getNormalizedUtcDateForToday();
        mLocalDataSource.checkDate(today, new DataSource.LoadDateCheckCallback() {
            @Override
            public void onDatesCheckedLoaded(List<DateSavedEntity> entities) {
                List<DateSavedEntity> count = new ArrayList<>(entities);
                loadDateCheckCallback.onDatesCheckedLoaded(count);
            }

            @Override
            public void onDatesCheckedFailed(String failed) {
                loadDateCheckCallback.onDatesCheckedFailed(failed);
            }
        });
    }

    public void getHighestRatedMovies(Context context, final DataSource.LoadInfoCallback callback) {
        if (isThereInternetConnection()) {
          //  mRemoteDataSource.getHighestRatedMovies(context, callback);
        } else {
            // Query the local storage if available.
          //  mLocalDataSource.getHighestRatedMovies(context, callback);
        }
    }


    public void getMoviesFromLibrary(final DataSource.LoadInfoCallback callback) {
        mLocalDataSource.getMoviesFromLibrary(callback);
    }


    public void deleteMovie(boolean type, MovieResultEntity entity, final DataSource.DeleteInfoCallback callback) {
        mLocalDataSource.deleteMovie(type, entity, callback);
    }

    public void deleteMovieFromLibrary(MovieResultEntity entity, final DataSource.DeleteInfoCallback callback) {
        mLocalDataSource.deleteMovieFromLibrary(entity, callback);
    }


    public void saveMovie(MovieResultEntity result, final DataSource.SaveInfoCallback callback) {
        mLocalDataSource.saveMovie(result, callback);
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
                                         LocalDataSource localDataSource, Context context, AppExecutors executors) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource, context, executors);
        }
        return INSTANCE;
    }


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