package co.za.tinycinema.features.GetTopRatedMovies.domain.usecase;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class GetTopRatedMovies extends UseCase<GetTopRatedMovies.RequestValues, GetTopRatedMovies.ResponseValue> {

    private final Repository repository;

    public GetTopRatedMovies(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.getHighestRatedMovies(new DataSource.LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<Result> results) {
                getUseCaseCallback().onSuccess(new ResponseValue(results));
            }

            @Override
            public void onDataNotAvailable(String error) {
                getUseCaseCallback().onError(error);
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

        public ResponseValue(List<Result> mResults) {
            this.mResults = mResults;
        }

        public List<Result> getInfo() {
            return mResults;
        }
    }
}
