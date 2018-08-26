package co.za.tinycinema.di.application;

import android.app.Application;

import javax.inject.Singleton;

import co.za.tinycinema.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    AppExecutors getAppExecutors(){
        return new AppExecutors();
    }



}

