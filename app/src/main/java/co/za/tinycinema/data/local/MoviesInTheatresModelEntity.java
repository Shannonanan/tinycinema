package co.za.tinycinema.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import static co.za.tinycinema.data.local.MovieResultEntity.TABLE_NAME;


@Entity
public class MoviesInTheatresModelEntity {

   // public static final String TABLE_NAME = "movies";

    @PrimaryKey(autoGenerate = true)
    private List<MovieResultEntity> results = null;

    public List<MovieResultEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieResultEntity> results) {
        this.results = results;
    }
}
