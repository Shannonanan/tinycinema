package co.za.tinycinema.features.Library;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.Library.domain.usecase.DeleteMoviesFromLibrary;
import co.za.tinycinema.features.Library.domain.usecase.GetMoviesFromLibrary;


public class LibraryPresenter extends ViewModel{

    private GetMoviesFromLibrary getMoviesFromLibraryUsecase;
    private LiveData<List<MovieResultEntity>> moviesFromLibrary;

    public LibraryPresenter(GetMoviesFromLibrary getMoviesFromLibraryUsecase) {
        this.getMoviesFromLibraryUsecase = getMoviesFromLibraryUsecase;
        moviesFromLibrary = getMoviesFromLibraryUsecase.executeUseCase(true);
    }

    public LiveData<List<MovieResultEntity>> getmMoviesFromLibrary() {
        return moviesFromLibrary;
    }


}
