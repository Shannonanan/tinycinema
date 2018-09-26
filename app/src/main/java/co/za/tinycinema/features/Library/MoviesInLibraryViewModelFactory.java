package co.za.tinycinema.features.Library;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresPresenter;
import co.za.tinycinema.features.Library.domain.usecase.GetMoviesFromLibrary;

public class MoviesInLibraryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private GetMoviesFromLibrary getMoviesFromLibraryUsecase;

    public MoviesInLibraryViewModelFactory(GetMoviesFromLibrary getMoviesFromLibraryUsecase) {
        this.getMoviesFromLibraryUsecase = getMoviesFromLibraryUsecase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new LibraryPresenter(getMoviesFromLibraryUsecase);
    }
}
