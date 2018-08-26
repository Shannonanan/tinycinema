package co.za.tinycinema.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

/**
 * {@link Dao} which provides an api for all data operations with the {@link MoviesDatabase}
 */
@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieResultEntity saveMovie);

    @Query("SELECT * from movies WHERE toprated = :type")
    List<MovieResultEntity> getAllMovies(boolean type);

    @Delete
    void deleteTask(MovieResultEntity entry);
}
