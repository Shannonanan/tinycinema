package co.za.tinycinema.features.GetMoviesInTheatres;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;

public class MoviesInTheatresViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private GetMoviesInTheatres getMoviesInTheatresUseCase;

    public MoviesInTheatresViewModelFactory(GetMoviesInTheatres getMoviesInTheatresUseCase) {
        this.getMoviesInTheatresUseCase = getMoviesInTheatresUseCase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MoviesInTheatresPresenter(getMoviesInTheatresUseCase);
    }

}
