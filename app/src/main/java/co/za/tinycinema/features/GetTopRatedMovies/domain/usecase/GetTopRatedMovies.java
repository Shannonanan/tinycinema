//package co.za.tinycinema.features.GetTopRatedMovies.domain.usecase;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.util.List;
//
//import co.za.tinycinema.common.UseCase;
//import co.za.tinycinema.data.DataSource;
//import co.za.tinycinema.data.Repository;
//import co.za.tinycinema.data.remote.MoviesSyncIntentService;
//import co.za.tinycinema.data.remote.RemoteDataSource;
//import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
//
//public class GetTopRatedMovies extends UseCase<GetTopRatedMovies.RequestValues, GetTopRatedMovies.ResponseValue> {
//
//    private final Repository repository;
//    private Context context;
//    private static GetTopRatedMovies sInstance = null;
//    private static final Object LOCK = new Object();
//    private static final String LOG_TAG = GetTopRatedMovies.class.getSimpleName();
//
//    public GetTopRatedMovies(Repository repository, Context context) {
//        this.repository = repository;
//        this.context = context;
//    }
//
//    @Override
//    protected void executeUseCase(RequestValues requestValues) {
//        this.repository.getHighestRatedMovies(context, new DataSource.LoadInfoCallback() {
//            @Override
//            public void onDataLoaded(List<Result> results, boolean offline) {
//                getUseCaseCallback().onSuccess(new ResponseValue(results, offline));
//            }
//
//            @Override
//            public void onDataNotAvailable(String error) {
//                getUseCaseCallback().onError(error);
//            }
//        });
//    }
//
//    public static final class RequestValues implements UseCase.RequestValues {
//
//        public RequestValues() {
//        }
//
//    }
//
//
//    /**
//     * Get the singleton for this class
//     */
//    public static GetTopRatedMovies getInstance(Repository repository, Context context) {
//        Log.d(LOG_TAG, "Getting the network data source");
//        if (sInstance == null) {
//            synchronized (LOCK) {
//                sInstance = new GetTopRatedMovies(repository, context.getApplicationContext());
//                Log.d(LOG_TAG, "Made new network data source");
//            }
//        }
//        return sInstance;
//    }
//
//    //this is for your usecase callback
//    public static final class ResponseValue implements UseCase.ResponseValue {
//        private List<Result> mResults;
//        boolean offline;
//
//        public ResponseValue(List<Result> mResults, boolean offline) {
//            this.mResults = mResults;
//            this.offline = offline;
//        }
//
//        public List<Result> getInfo() {
//            return mResults;
//        }
//
//        public boolean networkStatus(){
//            return offline;
//        }
//    }
//}
