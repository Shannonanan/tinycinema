package co.za.tinycinema.data.remote;

import java.util.Date;
import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.MoviesInTheatresModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/discover/movie?sort_by=popularity.des")
    Call<MoviesInTheatresModel>  getMoviesInTheatres(@Query("api_key") String api_key);

    @GET("/3/discover/movie?sort_by=vote_average.desc")
    Call<MoviesInTheatresModel>  getTopVotedMoviesInTheatres(@Query("api_key") String api_key);
}
