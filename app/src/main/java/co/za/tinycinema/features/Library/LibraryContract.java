package co.za.tinycinema.features.Library;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface LibraryContract extends ObservableViewMvc<LibraryContract.Listener> {


    interface Listener {
        void OnMoviePosterClicked(Result movieResult);
        void onDeleteButtonClicked(Result result);
    }

    void renderInView(List<Result> movieInfo, boolean networkStatus);
    void setLoadingIndicator(boolean active);
    void showLoadingTasksError(String error);
    void showNothingView();
    void hideNothingView();
    boolean isActive();
}
