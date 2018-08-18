package co.za.tinycinema.di.application;

import android.app.Application;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
//https://medium.com/@marco_cattaneo/integrate-dagger-2-with-room-persistence-library-in-few-lines-abf48328eaeb

@Module
public class RoomModule {
//
//    private EarthInfoDatabase earthInfoDatabase;
//
//
//    public RoomModule(Application mApplication) {
//        this.earthInfoDatabase = Room.databaseBuilder(mApplication,EarthInfoDatabase.class,
//                "EarthInfoObjEnhanced.db").build();
//    }
//
//    @Singleton
//    @Provides
//    EarthInfoDatabase providesRoomDataBase() {
//        return earthInfoDatabase;
//    }
//
//    @Singleton
//    @Provides
//    EarthDao providesEarthDao(EarthInfoDatabase earthInfoDatabase){
//        return earthInfoDatabase.earthDao();
//    }
//
//    @Singleton
//    @Provides
//    EpicDataSource epicLocalDataSource(EarthDao earthDao, AppExecutors appExecutors){
//        return new EpicLocalDataSource(earthDao, appExecutors);
//    }


}
