package co.za.tinycinema.features.GetTopRatedMovies;


import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;

public class TopRatedMoviesActivity extends BaseActivity implements TopRatedContract.Listener {

    @Inject
    TopRatedMoviesPresenter topRatedMoviesPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    TopRatedContract mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(TopRatedContract.class, null);
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.topRatedMoviesPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
        topRatedMoviesPresenter.loadTopRatedMovies(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnMoviePosterClicked(Result movieResult) {
        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));
    }

    @Override
    public void OnSaveMovieClciked(Result result) {
        topRatedMoviesPresenter.saveInfoToLocal(result);
    }

    @Override
    public void renderStatusOfSave(String status) {
        if(status.equals("success")){
            Toast.makeText(this,getString(R.string.saved_successfully),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,getString(R.string.save_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteMovieClicked(boolean type,Result result) {
        topRatedMoviesPresenter.deleteMovieFromLocal(type, result);
    }
}

