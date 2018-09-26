package co.za.tinycinema.features.Library;

import java.util.List;


import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface LibraryContract extends ObservableViewMvc<LibraryContract.Listener> {


    void showCount(List<Integer> checkCount);

    interface Listener {
        void OnMoviePosterClicked(MovieResultEntity movieResult);
        void onDeleteButtonClicked(MovieResultEntity result);
    }

    void renderInView(List<MovieResultEntity> movieInfo, boolean networkStatus);
    void setLoadingIndicator(boolean active);
    void showLoadingTasksError(String error);
    void showNothingView();
    void hideNothingView();
    boolean isActive();
}
