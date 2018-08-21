package co.za.tinycinema.features.ShowDetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class ShowDetailsActivity extends AppCompatActivity {

    private static final String INTENT_EXTRA_MOVIE_RESULT = "INTENT_EXTRA_MOVIE_RESULT";

    public static Intent getCallingIntent(Context context, Result result){
        Intent callingIntent = new Intent(context, ShowDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_MOVIE_RESULT, result);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
    }
}
