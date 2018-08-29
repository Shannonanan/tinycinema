package co.za.tinycinema.features.GetMoviesInTheatres;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
import co.za.tinycinema.features.Library.LibraryActivity;
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
        if(!isThereInternetConnection()){
            Toast.makeText(this, getString(R.string.offline),Toast.LENGTH_LONG).show();
        }
        this.moviesInTheatresPresenter.setView(mViewMvc);
        moviesInTheatresPresenter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
      //  this.moviesInTheatresPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
       // moviesInTheatresPresenter.start();
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
    public void renderStatusOfSave(String status) {
        if(status.equals("successful")){

            Toast.makeText(this,getString(R.string.saved_successfully),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,getString(R.string.save_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteButtonClicked(boolean type, Result result) {
        moviesInTheatresPresenter.deleteMovieFromLocal(type, result);
    }

    @Override
    public void OnSaveButtonClicked(Result result) {
        moviesInTheatresPresenter.saveInfoToLocal(result);
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
