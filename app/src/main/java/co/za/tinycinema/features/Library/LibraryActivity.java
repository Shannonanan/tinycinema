//package co.za.tinycinema.features.Library;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//
//import android.widget.Toast;
//import javax.inject.Inject;
//import co.za.tinycinema.R;
//import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
//import co.za.tinycinema.features.ShowDetails.ShowDetailsActivity;
//import co.za.tinycinema.features.common.BaseActivity;
//import co.za.tinycinema.features.common.mvcViews.ViewMvcFactory;
//
//public class LibraryActivity extends BaseActivity implements LibraryContract.Listener {
//
//    @Inject
//    LibraryPresenter libraryPresenter;
//    @Inject
//    ViewMvcFactory viewMvcFactory;
//
//    LibraryContract mViewMvc;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getPresentationComponent().inject(this);
//        mViewMvc = viewMvcFactory.newInstance(LibraryContract.class, null);
//        setContentView(mViewMvc.getRootView());
//        if(!isThereInternetConnection()){
//            Toast.makeText(this, getString(R.string.offline),Toast.LENGTH_LONG).show();
//        }
//        this.libraryPresenter.setView(mViewMvc);
//        libraryPresenter.start();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //  this.moviesInTheatresPresenter.setView(mViewMvc);
//        mViewMvc.registerListener(this);
//        // moviesInTheatresPresenter.start();
//    }
//
//
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void OnMoviePosterClicked(Result movieResult) {
//        //Go to Detail activity
//        startActivity(ShowDetailsActivity.getCallingIntent(this, movieResult));
//    }
//
//
//    @Override
//    public void onDeleteButtonClicked(Result result) {
//        libraryPresenter.deleteFromLocalLibrary(result);
//    }
//
//
//    /**
//     * Checks if the device has any active internet connection.
//     *
//     * @return true device with internet connection, otherwise false.
//     */
//    private boolean isThereInternetConnection() {
//        boolean isConnected;
//
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
//
//        return isConnected;
//    }
//
//
//}
