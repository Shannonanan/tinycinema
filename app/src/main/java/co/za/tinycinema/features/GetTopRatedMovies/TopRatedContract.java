package co.za.tinycinema.features.GetTopRatedMovies;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface TopRatedContract extends ObservableViewMvc<TopRatedContract.Listener>{

    interface Listener{
        void OnMoviePosterClicked(Result movieResult);
        void OnSaveMovieClciked(Result result);
        void renderStatusOfSave(String status);
        void OnDeleteMovieClicked(boolean type, Result status);
    }


    void renderInView(List<Result> movieInfo, boolean networkStatus);
    void setLoadingIndicator(boolean active);
    void renderStatusOfSave(String status);
    void showLoadingTasksError();
    boolean isActive();
    void showNothingView();
    void hideNothingView();
}
