package co.za.tinycinema.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static co.za.tinycinema.data.local.MoviesInTheatresModelEntity.TABLE_NAME3;


@Entity(tableName = TABLE_NAME3)
@TypeConverters(ObjectConverter.class)
public class MoviesInTheatresModelEntity {

    public static final String TABLE_NAME3 = "moviesList";

    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    int id;

    @TypeConverters(ObjectConverter.class)
    public List<MovieResultEntity> results;

    @TypeConverter
    public List<MovieResultEntity> getResults() {
        return results;
    }

    @TypeConverter
    public void setResults(List<MovieResultEntity> results) {
        this.results = results;
    }
}
