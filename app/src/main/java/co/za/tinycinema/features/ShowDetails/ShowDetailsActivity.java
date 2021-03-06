package co.za.tinycinema.features.ShowDetails;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import co.za.tinycinema.R;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.data.local.MoviesDatabase;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresContract;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetReviews.GetReviewsActivity;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesActivity;
import co.za.tinycinema.features.Library.LibraryActivity;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
import co.za.tinycinema.utils.AppExecutors;
import co.za.tinycinema.utils.InjectorUtils;
import co.za.tinycinema.utils.WatchVideos;

public class ShowDetailsActivity extends BaseActivity implements ShowDetailsContract.Listener {

    @Inject ShowDetailsPresenter showDetailsPresenter;
    @Inject ViewMvcFactory viewMvcFactory;
    Repository repository;

    ShowDetailsContract mViewMvc;
    MovieResultEntity result;

    @Inject AppExecutors mExecutors;

    private static final String INTENT_EXTRA_MOVIE_RESULT = "INTENT_EXTRA_MOVIE_RESULT";

    public static Intent getCallingIntent(Context context, MovieResultEntity result){
        Intent callingIntent = new Intent(context, ShowDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_MOVIE_RESULT, result);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(ShowDetailsContract.class, null);
        setContentView(mViewMvc.getRootView());
        result = (MovieResultEntity) getIntent().getSerializableExtra(INTENT_EXTRA_MOVIE_RESULT);
        repository =InjectorUtils.provideRepository(getApplicationContext());

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.my_toolbar_details);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setTitle("");
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        this.showDetailsPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
        showDetailsPresenter.setupViews(result);
      //  showDetailsPresenter.checkIfInLocal(result.getId());

       checkIfInlocal(result.getId());
    }

    private void checkIfInlocal(final Integer id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                repository.checkMovieSaveTest(id, new DataSource.SavedMovieToLibraryCallback() {
                    @Override
                    public void savedStatusSuccess(final Boolean status) {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mViewMvc.renderCheckMovieSavedInView(status);
                            }
                        });

                    }

                    @Override
                    public void savedStatusFailed(String error) {

                    }
                });

            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onSaveMovieToLocalClicked(final MovieResultEntity movieResultEntity) {
          //  showDetailsPresenter.saveToLocalFavourites(movieResultEntity);
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               movieResultEntity.setFavourite("1");
               repository.saveMovieIndividual(movieResultEntity);
           }
       };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void onRemoveMovieFromLocalClicked(final MovieResultEntity movieResultEntity) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                repository.deleteMovie(movieResultEntity.getId());
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void renderStatusOfSave(String status) {
        if(status.equals(this.getString(R.string.success))){

            Toast.makeText(this,getString(R.string.saved_successfully),Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,getString(R.string.save_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onReviewClicked(Integer movieId) {
        Intent intent = new Intent(this, GetReviewsActivity.class);
        intent.putExtra("MOVIE_ID",movieId);
        startActivity(intent);
    }

    @Override
    public void onTrailerBtnClicked(Integer movieId) {
        showDetailsPresenter.getVideoId(movieId);
    }

    @Override
    public void watchVideo(String getVideoId) {
        WatchVideos.watchYoutubeVideo(this, getVideoId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_reviews:
                if(isThereInternetConnection()){
                    Intent intent = new Intent(this, GetReviewsActivity.class);
                    intent.putExtra("MOVIE_ID",result.getId());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, getString(R.string.offline), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_trailer:
                if(isThereInternetConnection()){
                    showDetailsPresenter.getVideoId(result.getId());
                }else{
                    Toast.makeText(this, getString(R.string.offline), Toast.LENGTH_LONG).show();
                }
                break;
           case android.R.id.home:
            onBackPressed();
            break;
            case R.id.action_watched:
                Toast.makeText(this, getString(R.string.added), Toast.LENGTH_LONG).show();
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
