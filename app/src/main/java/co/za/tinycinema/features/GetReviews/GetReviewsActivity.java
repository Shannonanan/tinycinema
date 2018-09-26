package co.za.tinycinema.features.GetReviews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import co.za.tinycinema.R;
import co.za.tinycinema.features.GetReviews.Domain.model.Result;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
import co.za.tinycinema.utils.InjectorUtils;

public class GetReviewsActivity extends BaseActivity implements GetReviewsContract.Listener {

    GetReviewsViewModelPresenter getReviewsViewModelPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;

    GetReviewsContract mViewMvc;

    private ProgressBar mLoadingIndicator;

    public int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(GetReviewsContract.class, null);
        setContentView(mViewMvc.getRootView());
        if(!isThereInternetConnection()){
            Toast.makeText(this, getString(R.string.offline),Toast.LENGTH_LONG).show();
            return;
        }

        movieId = getIntent().getIntExtra("MOVIE_ID", 0);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicatorReviews);

        GetReviewsViewModelFactory getReviewsViewModelFactory =
                InjectorUtils.provideGetReviewsViewModelFactory(this.getApplicationContext());
        getReviewsViewModelPresenter = ViewModelProviders.of
                (this,getReviewsViewModelFactory).get(GetReviewsViewModelPresenter.class);

      //  getReviewsViewModelPresenter.getReviews(movieId);

        getReviewsViewModelPresenter.getReviews(movieId).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> reviewEntities) {
                mViewMvc.renderInView(reviewEntities);
                if (reviewEntities.size() != 0)hideLoadingScreen();
                else showLoading();
            }
        });


    }

    private void hideLoadingScreen() {
        if(mLoadingIndicator != null)
            mLoadingIndicator.setVisibility(View.GONE);
    }

    private void showLoading() {
        if(mLoadingIndicator == null)
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
