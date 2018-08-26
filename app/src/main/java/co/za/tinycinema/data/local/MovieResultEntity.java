package co.za.tinycinema.data.local;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

import static co.za.tinycinema.data.local.MovieResultEntity.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class MovieResultEntity {

    public static final String TABLE_NAME = "movies";

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer voteCount;
    private Boolean video;
    private Double voteAverage;
    private String title;
    private Double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    //  private List<Integer> genreIds = null;
    private String backdropPath;
    private Boolean adult;
    private String overview;
    private String releaseDate;
    private boolean toprated;

    public boolean isToprated() {
        return toprated;
    }

    public void setToprated(boolean toprated) {
        this.toprated = toprated;
    }


    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

//        public List<Integer> getGenreIds() {
//            return genreIds;
//        }
//
//        public void setGenreIds(List<Integer> genreIds) {
//            this.genreIds = genreIds;
//        }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}

