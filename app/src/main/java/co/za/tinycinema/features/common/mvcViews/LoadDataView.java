package co.za.tinycinema.features.common.mvcViews;


import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Get a {@link Context}.
     */
    Context applicationContext();

    /**
     * Get a {@link Context}.
     */
    Context activityContext();
}
