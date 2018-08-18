package co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class GetMoviesInTheatres extends UseCase<GetMoviesInTheatres.RequestValues, GetMoviesInTheatres.ResponseValue> {

    private final Repository repository;

    public GetMoviesInTheatres(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.getAllMoviesInTheatre(requestValues.mDate, new DataSource.LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> results) {
                getUseCaseCallback().onSuccess(new ResponseValue(results));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mDate;

        public RequestValues(String mDate) {
            this.mDate = mDate;
        }

        public String getMovies() {
            return mDate;
        }
    }

    //this is for your usecase callback
    public static final class ResponseValue implements UseCase.ResponseValue {
        private List<Result> mResults;

        public ResponseValue(List<Result> mResults) {
            this.mResults = mResults;
        }

        public List<Result> getInfo() {
            return mResults;
        }
    }
}
