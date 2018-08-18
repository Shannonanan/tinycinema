package co.za.tinycinema.data.remote;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.MoviesInTheatresModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/discover/movie/{primary_release_date.gte}/{primary_release_date.lte}")
    Call<MoviesInTheatresModel>  getMoviesInTheatres(@Path("primary_release_date.gte") String gte,@Path("primary_release_date.lte")String lte, @Query("api_key") String api_key);
}
