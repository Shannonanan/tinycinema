package co.za.tinycinema.features.ShowDetails.domain.usecase;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;

public class CheckSavedMovieInLocal extends UseCase<CheckSavedMovieInLocal.RequestValues, CheckSavedMovieInLocal.ResponseValues> {

    private Repository repository;

    public CheckSavedMovieInLocal(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.checkMovieSaved(requestValues.movieID, new DataSource.SavedMovieToLibraryCallback() {
            @Override
            public void savedStatusSuccess(Boolean status) {
                getUseCaseCallback().onSuccess(new ResponseValues(status));
            }

            @Override
            public void savedStatusFailed(String error) {
                getUseCaseCallback().onError(error);
            }
        });
    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        Boolean callback;

        public ResponseValues(Boolean callback) {
            this.callback = callback;

        }

        public Boolean forCallback() {
            return callback;
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        Integer movieID;

        public RequestValues(Integer movieID) {
            this.movieID = movieID;
        }

        public static RequestValues toCheckSavedMovie(Integer movieId) {
            return new RequestValues(movieId);
        }

    }
}
