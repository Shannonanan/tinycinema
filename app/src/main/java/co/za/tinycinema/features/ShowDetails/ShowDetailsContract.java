package co.za.tinycinema.features.ShowDetails;

import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface ShowDetailsContract extends ObservableViewMvc<ShowDetailsContract.Listener> {

    interface  Listener{
        void onSaveMovieToLocalClicked(MovieResultEntity movieResultEntity);
        void onRemoveMovieFromLocalClicked(MovieResultEntity movieResultEntity);
        void renderStatusOfSave(String status);

    }

    void setupViews(MovieResultEntity result);
    void setLoadingIndicator(boolean active);
    void renderStatusOfSave(String status);
    void renderDeleteInView(String success);
}
