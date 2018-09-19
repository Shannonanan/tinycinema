package co.za.tinycinema.features.Library.domain.usecase;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class DeleteMoviesFromLibrary extends  UseCase<DeleteMoviesFromLibrary.RequestValues, DeleteMoviesFromLibrary.ResponseValues> {

        private Repository repository;

    public DeleteMoviesFromLibrary(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        this.repository.deleteMovieFromLibrary(requestValues.entityToDelete, new DataSource.DeleteInfoCallback() {
            @Override
            public void deleteStatusSuccess(String status) {
                getUseCaseCallback().onSuccess(new ResponseValues(status));
            }

            @Override
            public void deleteStatusFailed(String status) {
                getUseCaseCallback().onError(status);
            }
        });
    }

    //The static keyword is used to create methods that will
// exist independently of any instances created for the class.
    public static final class RequestValues implements UseCase.RequestValues{
        MovieResultEntity entityToDelete;

        public RequestValues(MovieResultEntity entityToDelete) {
            this.entityToDelete = entityToDelete;
        }

        public static RequestValues toDelete(MovieResultEntity entity){
            return new RequestValues(entity);
        }
    }

    public static final class ResponseValues implements UseCase.ResponseValue{
        String callback;
      //  List<Result> movieResults;

        public ResponseValues(String callback) {
            this.callback = callback;
           // this.movieResults = movieResults;
        }

        public String forCallback() {
            return callback;
        }
      //  public List<Result> forListRefresh(){
        //    return movieResults;
       // }
    }
}
