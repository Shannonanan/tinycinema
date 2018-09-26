package co.za.tinycinema.features.GetTopRatedMovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresPresenter;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
import co.za.tinycinema.utils.InjectorUtils;

public class TopRatedMoviesActivity extends BaseActivity implements TopRatedContract.Listener {

  //  @Inject
    TopRatedMoviesPresenter topRatedMoviesPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    TopRatedContract mViewMvc;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(TopRatedContract.class, null);
        setContentView(mViewMvc.getRootView());

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        TopRatedMoviesViewModelFactory topRatedMoviesViewModelFactory =
                InjectorUtils.provideToRatedViewModelFactory(this.getApplicationContext());
        topRatedMoviesPresenter = ViewModelProviders.of
                (this,topRatedMoviesViewModelFactory).get(TopRatedMoviesPresenter.class);

        topRatedMoviesPresenter.getTopRatedMovies().observe(this, new Observer<List<MovieResultEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieResultEntity> movieResultEntities) {
                mViewMvc.renderInView(movieResultEntities,false);
                if (movieResultEntities.size() != 0)hideLoadingScreen();
                else showLoading();
            }
        });
    }

    private void hideLoadingScreen() {
        if(mLoadingIndicator != null)
            mLoadingIndicator.setVisibility(View.GONE);
    }

    private void showLoading() {
        if(mLoadingIndicator != null)
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.topRatedMoviesPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnMoviePosterClicked(MovieResultEntity movieResult) {
        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));
    }

    @Override
    public void OnSaveMovieClciked(MovieResultEntity result) {
      //  topRatedMoviesPresenter.saveInfoToLocal(result);
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
    public void OnDeleteMovieClicked(boolean type,MovieResultEntity result) {
      //  topRatedMoviesPresenter.deleteMovieFromLocal(type, result);
    }
}

