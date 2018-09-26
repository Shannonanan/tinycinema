package co.za.tinycinema.data.remote;

import java.util.Date;
import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.MoviesInTheatresModel;
import co.za.tinycinema.features.GetReviews.Domain.model.ReviewResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/movie/popular")
    Call<MoviesInTheatresModel>  getMoviesInTheatres(@Query("api_key") String api_key);

    @GET("/3/movie/top_rated")
    Call<MoviesInTheatresModel>  getTopVotedMoviesInTheatres(@Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewResponseModel> getReviews(@Path("id") int movie_id, @Query("api_key") String api_key);

    ///movie/{id}/videos

}
