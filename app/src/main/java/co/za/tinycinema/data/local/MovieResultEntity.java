package co.za.tinycinema.data.local;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static co.za.tinycinema.data.local.MovieResultEntity.TABLE_NAME1;

@Entity(tableName = TABLE_NAME1)
public class MovieResultEntity implements Serializable{

    public static final String TABLE_NAME1 = "movie";


    @PrimaryKey(autoGenerate = true)
    private int idd;
    private Integer id;
    private Integer voteCount;
//    private Boolean video;
    private Double voteAverage;
    private String title;
    private Double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    //  private List<Integer> genreIds = null;
    private String backdropPath;
 //   private Boolean adult;
    private String overview;
    private String releaseDate;
    private String toprated;
    private String favourite;
    private String toWatch;


    @Ignore
    public MovieResultEntity() {
    }

    @Ignore
    public MovieResultEntity(Integer id, Integer voteCount,
                          //   Boolean video,
                             Double voteAverage, String title,
                             Double popularity, String posterPath, String originalLanguage,
                             String originalTitle, String backdropPath,
                             //Boolean adult,
                             String overview,
                             String releaseDate, String toprated, String favourite, String toWatch) {
        this.id = id;
        this.voteCount = voteCount;
   //     this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
   //     this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.toprated = toprated;
        this.favourite = favourite;
        this.toWatch = toWatch;
    }



    public MovieResultEntity(int idd,Integer id, Integer voteCount,
                             //Boolean video,
                             Double voteAverage, String title, Double popularity,
                             String posterPath, String originalLanguage, String originalTitle,
                             String backdropPath,
                          //   Boolean adult,
                             String overview, String releaseDate,
                             String toprated, String favourite, String toWatch) {
        this.idd = idd;
        this.id = id;
        this.voteCount = voteCount;
     //   this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
    //    this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.toprated = toprated;
        this.favourite = favourite;
        this.toWatch = toWatch;
    }


    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
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

  //  public Boolean getVideo() {
  //      return video;
  //  }

  //  public void setVideo(Boolean video) {
  //      this.video = video;
  //  }

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

//    public Boolean getAdult() {
//        return adult;
//    }
//
//    public void setAdult(Boolean adult) {
//        this.adult = adult;
//    }

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

    public String getToprated() {
        return toprated;
    }

    public void setToprated(String toprated) {
        this.toprated = toprated;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getToWatch() {
        return toWatch;
    }

    public void setToWatch(String toWatch) {
        this.toWatch = toWatch;
    }


}

