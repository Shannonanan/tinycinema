package co.za.tinycinema.features.Library;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
import co.za.tinycinema.utils.InjectorUtils;

public class LibraryActivity extends BaseActivity implements LibraryContract.Listener {

   // @Inject
    LibraryPresenter libraryPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    LibraryContract mViewMvc;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(LibraryContract.class, null);
        setContentView(mViewMvc.getRootView());
        if(!isThereInternetConnection()){
            Toast.makeText(this, getString(R.string.offline),Toast.LENGTH_LONG).show();
        }
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        MoviesInLibraryViewModelFactory moviesInLibraryViewModelFactory =
                InjectorUtils.provideMoviesInLibraryViewModelFactory(this);

        libraryPresenter = ViewModelProviders.of
                (this,moviesInLibraryViewModelFactory).get(LibraryPresenter.class);

        libraryPresenter.getmMoviesFromLibrary().observe(this, new Observer<List<MovieResultEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieResultEntity> movieResultEntities) {
                mViewMvc.renderInView(movieResultEntities, true);
                if (movieResultEntities.size() != 0)hideLoadingScreen();
                else showLoading();
            }
        });
       // this.libraryPresenter.setView(mViewMvc);

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
        //  this.moviesInTheatresPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
        // moviesInTheatresPresenter.start();
    }




    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnMoviePosterClicked(MovieResultEntity movieResult) {
        //Go to Detail activity
        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));
    }


    @Override
    public void onDeleteButtonClicked(MovieResultEntity result) {
      //  libraryPresenter.deleteFromLocalLibrary(result);
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
