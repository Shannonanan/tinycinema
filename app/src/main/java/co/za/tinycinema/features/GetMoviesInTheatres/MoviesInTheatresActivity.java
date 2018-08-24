package co.za.tinycinema.features.GetMoviesInTheatres;


import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
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
                Intent intent = new Intent(this, TopRatedMoviesActivity.class);
                startActivity(intent);
                break;
            case R.id.toggle_most_pop:
                moviesInTheatresPresenter.start();
            default:
                break;
        }

        return true;
    }


}
