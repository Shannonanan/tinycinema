package co.za.tinycinema.di.application;


import javax.inject.Singleton;

import co.za.tinycinema.Constants;
import co.za.tinycinema.data.remote.Service;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkingModule
{

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    Service getMoviesApi(Retrofit retrofit) {
        return retrofit.create(Service.class);
    }

}
