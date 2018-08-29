package co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase;

import android.content.Context;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class GetMoviesInTheatres extends UseCase<GetMoviesInTheatres.RequestValues, GetMoviesInTheatres.ResponseValue> {

    private final Repository repository;
    private Context context;

    public GetMoviesInTheatres(Repository repository, Context context) {
        this.repository = repository;
        this.context = context;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.getAllMoviesInTheatre(context, new DataSource.LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> results, boolean offline) {
                getUseCaseCallback().onSuccess(new ResponseValue(results, offline));
            }

            @Override
            public void onDataNotAvailable(String dataNotAvailable) {
                getUseCaseCallback().onError(dataNotAvailable);
            }
        });

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
