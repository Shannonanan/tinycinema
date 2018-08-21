package co.za.tinycinema.di.presentation;

import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresActivity;
import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(MoviesInTheatresActivity moviesInTheatresActivity);

}
