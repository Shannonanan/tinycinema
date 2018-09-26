package co.za.tinycinema.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

//@Dao
//public interface LibraryDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long insertMovie(MovieLibraryEntity saveMovie);
//
//    @Query("SELECT  * FROM movie")
//    LiveData<List<MovieResultEntity>> getAllMoviesFromLibrary();
//
//    @Query("SELECT COUNT(id) FROM library")
//    List<Integer> getCountOfMoviesFromLibrary();
//}
