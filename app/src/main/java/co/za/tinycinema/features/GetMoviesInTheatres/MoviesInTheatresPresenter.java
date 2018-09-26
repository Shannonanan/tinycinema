package co.za.tinycinema.features.GetMoviesInTheatres;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;


public class MoviesInTheatresPresenter extends ViewModel {

    private GetMoviesInTheatres getMoviesInTheatresUseCase;
    private final LiveData<List<MovieResultEntity>> mMovieResults;

    public MoviesInTheatresPresenter(GetMoviesInTheatres getMoviesInTheatresUseCase) {
        this.getMoviesInTheatresUseCase = getMoviesInTheatresUseCase;
        mMovieResults = getMoviesInTheatresUseCase.executeGetMoviesUseCase();
    }


    public LiveData<List<MovieResultEntity>> getmMovieResults() {
        return mMovieResults;
    }



//    public MovieResultEntity transform(Result result) {
//        MovieResultEntity movieResultEntity = null;
//        if (result != null) {
//            movieResultEntity = new MovieResultEntity();
//            movieResultEntity.setId(result.getId());
//            movieResultEntity.setAdult(result.getAdult());
//            movieResultEntity.setBackdropPath(result.getBackdropPath());
//            movieResultEntity.setOriginalLanguage(result.getOriginalLanguage());
//            movieResultEntity.setOriginalTitle(result.getOriginalTitle());
//            movieResultEntity.setOverview(result.getOverview());
//            movieResultEntity.setPopularity(result.getPopularity());
//            movieResultEntity.setPosterPath(result.getPosterPath());
//            movieResultEntity.setReleaseDate(result.getReleaseDate());
//            movieResultEntity.setTitle(result.getTitle());
//            movieResultEntity.setVoteAverage(result.getVoteAverage());
//            movieResultEntity.setToprated(false);
//        }
//        return movieResultEntity;
//    }


}
