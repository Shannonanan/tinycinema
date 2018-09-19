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
            public void deleteStatusSuccess( String status) {
                getUseCaseCallback().onSuccess(new ResponseValues(status));
            }

            @Override
            public void deleteStatusFailed(String status) {
                getUseCaseCallback().onError(status);
            }
        });
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        String callback;


        public ResponseValues(String callback) {
            this.callback = callback;

        }

        public String forCallback() {
            return callback;
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        MovieResultEntity movieToDelete;
        boolean type;

        public RequestValues(MovieResultEntity movieToSave) {
            this.movieToDelete = movieToSave;
            this.type = type;
        }

        public static RequestValues toDeleteMovie(MovieResultEntity movie) {
            return new RequestValues(movie);
        }

    }
}
