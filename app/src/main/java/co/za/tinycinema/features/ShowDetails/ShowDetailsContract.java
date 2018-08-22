package co.za.tinycinema.features.ShowDetails;

import co.za.tinycinema.common.BaseObservable;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresContract;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface ShowDetailsContract extends ObservableViewMvc<ShowDetailsContract.Listener> {

    interface  Listener{

    }

    void setupViews(Result result);
}
