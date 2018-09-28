package co.za.tinycinema.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * {@link Dao} which provides an api for all data operations with the {@link MoviesDatabase}
 */
@Dao
public interface MoviesDao {

//pull the whole list and ceck if each movie already exists by id, if so check if favourited or towatch is true and dont replace
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @TypeConverter
    void bulkInsert(List<MovieResultEntity> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieResultEntity saveMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(MovieResultEntity director);

    @Query("SELECT * from movie WHERE toprated = :topRated AND favourite = :fav AND toWatch = :toWatch")
    LiveData<List<MovieResultEntity>> getAllMovies(String topRated, String fav, String toWatch);

    @Query("SELECT * from movie WHERE favourite = :fav")
    LiveData<List<MovieResultEntity>> getAllMoviesInLibrary(String fav);

    @Query("SELECT * From movie")
    LiveData<List<MovieResultEntity>> getAllMoviesLibrary();

    @Query("SELECT * from movie ")
    List<MovieResultEntity> getAllMoviesNow();

    @Query("DELETE FROM movie WHERE id= :id")
    int deleteMovie(Integer id);


    @Query("SELECT COUNT(id) FROM movie WHERE id == :id")
    Integer checkMovieWasSaved(Integer id);

//    @Query("Select * from movie WHERE id = :id")
//      MovieResultEntity checkMovie(Integer id);
}
