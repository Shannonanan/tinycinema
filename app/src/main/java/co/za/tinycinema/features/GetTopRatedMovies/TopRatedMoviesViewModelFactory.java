package co.za.tinycinema.features.GetTopRatedMovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import co.za.tinycinema.features.GetTopRatedMovies.domain.usecase.GetTopRatedMovies;

public class TopRatedMoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private GetTopRatedMovies getTopRatedMoviesUsecase;

    public TopRatedMoviesViewModelFactory(GetTopRatedMovies getTopRatedMovies) {
        this.getTopRatedMoviesUsecase = getTopRatedMovies;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new TopRatedMoviesPresenter(getTopRatedMoviesUsecase);
    }
}
