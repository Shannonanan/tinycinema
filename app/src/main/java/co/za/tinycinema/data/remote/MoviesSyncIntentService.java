//package co.za.tinycinema.data.remote;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import co.za.tinycinema.utils.InjectorUtils;
//
//public class MoviesSyncIntentService extends IntentService {
//    private static final String LOG_TAG = MoviesSyncIntentService.class.getSimpleName();
//
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     *
//     * Used to name the worker thread, important only for debugging.
//     */
//    public MoviesSyncIntentService() {
//        super("MoviesSyncIntentService");
//    }
//
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.d(LOG_TAG, "Intent service started");
//        RemoteDataSource remoteDataSource =
//                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
//        remoteDataSource.getAllMoviesInTheatre();
//    }
//}
