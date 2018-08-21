package co.za.tinycinema;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresActivity;


public class SplashScreen extends AppCompatActivity {

    public LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("movie_loading.json");
        animationView.loop(true);
        animationView.playAnimation();

            int INTERNET_TIME_OUT = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent inent = new Intent(SplashScreen.this, MoviesInTheatresActivity.class);
                    startActivity(inent);
                    animationView.cancelAnimation();
                    finish();
                }
            }, INTERNET_TIME_OUT);

    }
}
