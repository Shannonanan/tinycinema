package co.za.tinycinema.di.application;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import co.za.tinycinema.data.local.DateDao;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MoviesDao;
import co.za.tinycinema.data.local.MoviesDatabase;
import co.za.tinycinema.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;
//https://medium.com/@marco_cattaneo/integrate-dagger-2-with-room-persistence-library-in-few-lines-abf48328eaeb

@Module
public class RoomModule {

    private MoviesDatabase moviesDatabase;


    public RoomModule(Application mApplication) {
        this.moviesDatabase = Room.databaseBuilder(mApplication,MoviesDatabase.class,
                "movies.db").build();
    }

    @Singleton
    @Provides
    MoviesDatabase providesRoomDataBase() {
        return moviesDatabase;
    }

    @Singleton
    @Provides
    MoviesDao providesMovieDao(MoviesDatabase movieDatabase){
        return movieDatabase.moviesDao();
    }

    @Singleton
    @Provides
    DateDao providesDataDao(MoviesDatabase moviesDatabase){
        return moviesDatabase.dateDao();
    }

//    @Singleton
//    @Provides
//    LibraryDao providesLibraryDao(MoviesDatabase moviesDatabase){ return moviesDatabase.libraryDao();
//    }

    @Singleton
    @Provides
    LocalDataSource movieLocalDataSource(MoviesDao movieDao, DateDao dateDao){
        return new LocalDataSource(movieDao, dateDao);
    }


}
