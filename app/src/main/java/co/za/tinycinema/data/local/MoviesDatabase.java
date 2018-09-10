package co.za.tinycinema.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = { MovieResultEntity.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "movies";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, MoviesDatabase.DATABASE_NAME).build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract MoviesDao moviesDao();


}
