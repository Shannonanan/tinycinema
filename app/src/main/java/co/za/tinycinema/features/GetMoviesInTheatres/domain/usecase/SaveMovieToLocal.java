package co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class SaveMovieToLocal extends UseCase<SaveMovieToLocal.RequestValues, SaveMovieToLocal.ResponseValues>{

    private Repository repository;

    public SaveMovieToLocal(Repository repository) {
        this.repository = repository;
    }


    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.saveMovie(requestValues.movieToSave, new DataSource.SaveInfoCallback() {
            @Override
            public void savedStatusSuccess(String status) {
                getUseCaseCallback().onSuccess(new ResponseValues(status));
            }

            @Override
            public void savedStatusFailed(String error) {

            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        MovieResultEntity movieToSave;

        public RequestValues(MovieResultEntity movieToSave) {
            this.movieToSave = movieToSave;
        }

        public static RequestValues toSaveMovie(MovieResultEntity movie){
            return new RequestValues(movie);
        }

    }

    public static final class ResponseValues implements UseCase.ResponseValue {
            String status;

        public ResponseValues(String status) {
            this.status = status;
        }
        public String saveInfoResult() {
            return status;
        }

    }
}
