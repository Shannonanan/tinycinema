package co.za.tinycinema.features.GetMoviesInTheatres;

import android.os.Bundle;

import javax.inject.Inject;

import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;

public class MoviesInTheatresActivity extends BaseActivity implements MoviesInTheatresContract.Listener {

    @Inject
    MoviesInTheatresPresenter moviesInTheatresPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    MoviesInTheatresContract mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(MoviesInTheatresContract.class, null);
        setContentView(mViewMvc.getRootView());

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.moviesInTheatresPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
        moviesInTheatresPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnMoviePosterClicked(Result movieResult) {
        //Go to Detail activity
        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));
    }
}
