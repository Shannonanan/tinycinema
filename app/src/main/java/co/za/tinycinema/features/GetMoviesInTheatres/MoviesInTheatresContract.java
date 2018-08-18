package co.za.tinycinema.features.GetMoviesInTheatres;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MoviesInTheatresContract extends ObservableViewMvc<MoviesInTheatresContract.Listener> {

    public interface Listener{

    }

    void renderInView(List<Result> movieInfo);
    void setLoadingIndicator(boolean active);
    void showLoadingTasksError();
    boolean isActive();
}
