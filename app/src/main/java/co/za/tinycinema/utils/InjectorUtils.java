/*
 * Copyright (C) 2017 The Android Open Source Project
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

package co.za.tinycinema.utils;


import android.content.Context;



import co.za.tinycinema.Constants;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MoviesDatabase;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.data.remote.Service;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresViewModelFactory;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;
import co.za.tinycinema.features.Library.MoviesInLibraryViewModelFactory;
import co.za.tinycinema.features.Library.domain.usecase.GetMoviesFromLibrary;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides static methods to inject the various classes needed for TinyCinema
 */

//This is for Dependency injection, which is the idea that you should make
// required components available for a class, instead of creating them within the class itself
//One of the benefits of this is that components are easier to replace when you're testing
public class InjectorUtils  {


    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static Service getMoviesApi(Retrofit retrofit) {
        return retrofit.create(Service.class);
    }


    public static Repository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        RemoteDataSource networkDataSource = RemoteDataSource.getInstance(getMoviesApi(getRetrofit()), context.getApplicationContext());
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance(context.getApplicationContext());
        LocalDataSource localDataSource = LocalDataSource.getInstance(moviesDatabase.moviesDao(),
                moviesDatabase.dateDao(),
                executors);
        return Repository.getInstance(networkDataSource, localDataSource, context.getApplicationContext(), executors);
    }

    public static RemoteDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        return RemoteDataSource.getInstance(getMoviesApi(getRetrofit()), context.getApplicationContext());
    }


//    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
//        SunshineRepository repository = provideRepository(context.getApplicationContext());
//        return new DetailViewModelFactory(repository, date);
//    }
//
    public static MoviesInTheatresViewModelFactory provideMainActivityViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        GetMoviesInTheatres getMoviesInTheatres = GetMoviesInTheatres.getInstance(repository,context.getApplicationContext());
        return new MoviesInTheatresViewModelFactory(getMoviesInTheatres);
    }

    public static MoviesInLibraryViewModelFactory provideMoviesInLibraryViewModelFactory(Context context){
        Repository repository = provideRepository(context.getApplicationContext());
        GetMoviesFromLibrary getMoviesFromLibrary  = GetMoviesFromLibrary.getInstance(repository);
        return new MoviesInLibraryViewModelFactory(getMoviesFromLibrary);
    }

}