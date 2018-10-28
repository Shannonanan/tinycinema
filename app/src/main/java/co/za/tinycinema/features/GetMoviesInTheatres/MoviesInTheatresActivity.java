package co.za.tinycinema.features.GetMoviesInTheatres;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
//import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
//import co.za.tinycinema.features.Library.LibraryActivity;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
import co.za.tinycinema.features.Library.LibraryActivity;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
import co.za.tinycinema.utils.InjectorUtils;

public class MoviesInTheatresActivity extends BaseActivity implements MoviesInTheatresContract.Listener {

    MoviesInTheatresPresenter moviesInTheatresPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    MoviesInTheatresContract mViewMvc;

    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(MoviesInTheatresContract.class, null);
        setContentView(mViewMvc.getRootView());
        if(!isThereInternetConnection()){
            Toast.makeText(this, getString(R.string.offline),Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        MoviesInTheatresViewModelFactory moviesInTheatresViewModelFactory =
                InjectorUtils.provideMainActivityViewModelFactory(this.getApplicationContext());
        moviesInTheatresPresenter = ViewModelProviders.of
                (this,moviesInTheatresViewModelFactory).get(MoviesInTheatresPresenter.class);

        moviesInTheatresPresenter.getmMovieResults().observe(this, new Observer<List<MovieResultEntity>>() {
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
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }




    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onDeleteButtonClicked(boolean type, Result result) {
       // moviesInTheatresPresenter.deleteMovieFromLocal(type, result);
    }

    @Override
    public void OnMoviePosterClicked(MovieResultEntity movieResult) {
        //Go to Detail activity
        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.toggle_top_rated:
                if(isThereInternetConnection()){
                Intent intent = new Intent(this, TopRatedMoviesActivity.class);
                startActivity(intent);}
                else{
                    Toast.makeText(this, getString(R.string.offline), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.toggle_most_pop:
                if(isThereInternetConnection()){
                moviesInTheatresPresenter.getmMovieResults();}else{
                    Toast.makeText(this, getString(R.string.offline), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.library:
                Intent goToLibrary = new Intent(this, LibraryActivity.class);
                startActivity(goToLibrary);
            default:
                break;
        }

        return true;
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }


}
