package co.za.tinycinema.features.ShowDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresContract;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.BaseActivity;
import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;

public class ShowDetailsActivity extends BaseActivity implements ShowDetailsContract.Listener {

    @Inject ShowDetailsPresenter showDetailsPresenter;
    @Inject ViewMvcFactory viewMvcFactory;


    ShowDetailsContract mViewMvc;
    MovieResultEntity result;

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

        //check if favourited
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.showDetailsPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);
        showDetailsPresenter.setupViews(result);
        showDetailsPresenter.checkIfInLocal(result);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onSaveMovieToLocalClicked(MovieResultEntity movieResultEntity) {
            showDetailsPresenter.saveToLocalFavourites(movieResultEntity);
    }

    @Override
    public void onRemoveMovieFromLocalClicked(MovieResultEntity movieResultEntity) {
        showDetailsPresenter.removeFromLocal(movieResultEntity);

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
}
