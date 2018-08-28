package co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class DeleteMoviesInLocal extends UseCase<DeleteMoviesInLocal.RequestValues, DeleteMoviesInLocal.ResponseValues> {

    private Repository repository;

    public DeleteMoviesInLocal(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.deleteMovie(requestValues.type, requestValues.movieToDelete, new DataSource.DeleteInfoCallback() {
            @Override
            public void deleteStatusSuccess(List<Result> latestResults, String status) {
                getUseCaseCallback().onSuccess(new ResponseValues(status, latestResults));
            }

            @Override
            public void deleteStatusFailed(String status) {
                getUseCaseCallback().onError(status);
            }
        });
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        String callback;
        List<Result> movieResults;

        public ResponseValues(String callback, List<Result> movieResults) {
            this.callback = callback;
            this.movieResults = movieResults;
        }

        public String forCallback() {
            return callback;
        }
        public List<Result> forListRefresh(){
            return movieResults;
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        MovieResultEntity movieToDelete;
        boolean type;

        public RequestValues(boolean type, MovieResultEntity movieToSave) {
            this.movieToDelete = movieToSave;
            this.type = type;
        }

        public static RequestValues toDeleteMovie(MovieResultEntity movie, boolean type) {
            return new RequestValues(type, movie);
        }

    }
}
