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


}
