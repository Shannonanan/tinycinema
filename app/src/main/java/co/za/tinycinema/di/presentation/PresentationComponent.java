package co.za.tinycinema.di.presentation;

import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresActivity;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
import co.za.tinycinema.features.Library.LibraryActivity;
import co.za.tinycinema.features.Library.LibraryContract;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(MoviesInTheatresActivity moviesInTheatresActivity);
    void inject(ShowDetailsActivity showDetailsActivity);
    void inject(TopRatedMoviesActivity topRatedMoviesActivity);
    void inject(LibraryActivity libraryActivity);
    void inject(LocalDataSource localDataSource);
}
