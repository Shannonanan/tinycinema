package co.za.tinycinema.features.ShowDetails.domain.usecase;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.MovieResultEntity;

public class GetVideoId extends UseCase<GetVideoId.RequestValues, GetVideoId.ResponseValues> {

    Repository repository;

    public GetVideoId(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
         repository.getVideoId(requestValues.movieId, new DataSource.GetVideoIdCallback() {
            @Override
            public void getIdSuccess(String id) {
                getUseCaseCallback().onSuccess(new ResponseValues(id));
            }

            @Override
            public void getIdFailed(String failed) {
                getUseCaseCallback().onError(failed);
            }
        });
    }


    public static final class RequestValues implements UseCase.RequestValues {

        Integer movieId;

        public RequestValues(Integer movieId)
        {
            this.movieId = movieId;
        }

        public static RequestValues toGetId(Integer movieId){
            return new RequestValues(movieId);
        }

    }

    public static final class ResponseValues implements UseCase.ResponseValue {
        String getVideoId;

        public ResponseValues(String getVideoId) {
            this.getVideoId = getVideoId;
        }
        public String saveVideoIdResult() {
            return getVideoId;
        }

    }
}
