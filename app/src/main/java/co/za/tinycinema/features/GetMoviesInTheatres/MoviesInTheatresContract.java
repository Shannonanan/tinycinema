package co.za.tinycinema.features.GetMoviesInTheatres;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MoviesInTheatresContract extends ObservableViewMvc<MoviesInTheatresContract.Listener> {

    interface Listener {
        void OnMoviePosterClicked(Result movieResult);
        void OnSaveButtonClicked(Result result);
        void renderStatusOfSave(String status);
    }

    void renderInView(List<Result> movieInfo);

    void setLoadingIndicator(boolean active);
    void renderStatusOfSave(String status);
    void showLoadingTasksError(String error);

    boolean isActive();
}
