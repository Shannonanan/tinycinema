package co.za.tinycinema.features.GetTopRatedMovies;

import java.util.List;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface TopRatedContract extends ObservableViewMvc<TopRatedContract.Listener>{

    interface Listener{
        void OnMoviePosterClicked(Result movieResult);
    }


    void renderInView(List<Result> movieInfo);
    void setLoadingIndicator(boolean active);
    void showLoadingTasksError();
    boolean isActive();
}
