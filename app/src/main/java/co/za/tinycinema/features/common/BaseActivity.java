package co.za.tinycinema.features.common;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import co.za.tinycinema.MyApplication;
import co.za.tinycinema.di.application.ApplicationComponent;
import co.za.tinycinema.di.presentation.PresentationComponent;
import co.za.tinycinema.di.presentation.PresentationModule;

public abstract class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));

    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }


}
