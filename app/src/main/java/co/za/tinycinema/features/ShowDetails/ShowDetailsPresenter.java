package co.za.tinycinema.features.ShowDetails;

import android.support.annotation.NonNull;

import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresContract;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class ShowDetailsPresenter  {

    ShowDetailsContract showDetailsContract;

    public void setView(@NonNull ShowDetailsContract view) {
        this.showDetailsContract = view;
    }

    public void setupViews(MovieResultEntity result)
    {
        showDetailsContract.setupViews(result);
    }


}
