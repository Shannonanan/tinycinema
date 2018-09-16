package co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.airbnb.lottie.L;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;



public class GetMoviesInTheatres
        //extends UseCase<GetMoviesInTheatres.RequestValues, GetMoviesInTheatres.ResponseValue>
        {

    private final Repository repository;
    private Context context;
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = GetMoviesInTheatres.class.getSimpleName();
    public static GetMoviesInTheatres sInstance = null;

    public GetMoviesInTheatres(Repository repository, Context context) {
        this.repository = repository;
        this.context = context;
    }

 //   @Override
    public LiveData<List<MovieResultEntity>> executeGetMoviesUseCase() {
         return  this.repository.getAllMoviesInTheatre();
        }


//        public void executeGetMoviesUseCase(final DataSource.LoadInfoCallback loadInfoCallback) {
//            this.repository.getAllMoviesInTheatre(context, new DataSource.LoadInfoCallback() {
//                @Override
//                public void onDataLoaded(List<MovieResultEntity> results, boolean offline) {
//                    loadInfoCallback.onDataLoaded(results, false);
//                }
//
//                @Override
//                public void onDataNotAvailable(String noDataAvailable) {
//
//                }
//            });


        //{
//            @Override
//            public void onDataLoaded(List<Result> results, boolean offline) {
//                getUseCaseCallback().onSuccess(new ResponseValue(results, offline));
//            }
//
//            @Override
//            public void onDataNotAvailable(String dataNotAvailable) {
//                getUseCaseCallback().onError(dataNotAvailable);
//            }
//        });



            /**
             * Get the singleton for this class
             */
            public static GetMoviesInTheatres getInstance(Repository repository, Context context) {
                Log.d(LOG_TAG, "Getting the network data source");
                if (sInstance == null) {
                    synchronized (LOCK) {
                        sInstance = new GetMoviesInTheatres(repository, context.getApplicationContext());
                        Log.d(LOG_TAG, "Made new network data source");
                    }
                }
                return sInstance;
            }

    public static final class RequestValues implements UseCase.RequestValues {


        public RequestValues() {

        }

    }

    //this is for your usecase callback
    public static final class ResponseValue implements UseCase.ResponseValue {
        private List<Result> mResults;
        private boolean offline;

        public ResponseValue(List<Result> mResults, boolean offline) {
            this.mResults = mResults;
            this.offline = offline;
        }

        public List<Result> getInfo() {
            return mResults;
        }

        public boolean getNetWorkStatus() {
            return offline;}

    }
}
