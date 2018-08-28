package co.za.tinycinema.features.GetTopRatedMovies;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.DeleteMoviesInLocal;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.SaveMovieToLocal;
import co.za.tinycinema.features.GetTopRatedMovies.domain.usecase.GetTopRatedMovies;

public class TopRatedMoviesPresenter {

    private GetTopRatedMovies getTopRatedMoviesUsecase;
    private final UseCaseHandler useCaseHandler;
    private TopRatedContract topRatedContract;
    private SaveMovieToLocal saveMovieToLocal;
    private DeleteMoviesInLocal deleteMoviesInLocalUsecase;

    public TopRatedMoviesPresenter(GetTopRatedMovies getTopRatedMoviesUsecase, UseCaseHandler useCaseHandler,
                                   SaveMovieToLocal saveMovieToLocal,DeleteMoviesInLocal deleteMoviesInLocalUsecase) {
        this.getTopRatedMoviesUsecase = getTopRatedMoviesUsecase;
        this.useCaseHandler = useCaseHandler;
        this.saveMovieToLocal = saveMovieToLocal;
        this.deleteMoviesInLocalUsecase = deleteMoviesInLocalUsecase;
    }

    public void setView(@NonNull TopRatedContract topRatedContract){
        this.topRatedContract = topRatedContract;
    }

    public void loadTopRatedMovies(final boolean showLoadingUI){

        if (showLoadingUI) {
            topRatedContract.showLoading();
            topRatedContract.setLoadingIndicator(true);
        }
        this.useCaseHandler.execute(getTopRatedMoviesUsecase, getTopRatedMoviesUsecase.getRequestValues(),
                new UseCase.UseCaseCallback<GetTopRatedMovies.ResponseValue>() {
            @Override
            public void onSuccess(GetTopRatedMovies.ResponseValue response) {
                if (showLoadingUI) {
                    topRatedContract.setLoadingIndicator(false);
                    topRatedContract.hideLoading();
                }

                List<Result> moviesResult = new ArrayList<>();
                moviesResult.addAll(response.getInfo());
                processInfo(moviesResult, response.networkStatus());
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void processInfo(List<Result> moviesResult, boolean networkStatus) {
        topRatedContract.renderInView(moviesResult, networkStatus);
    }

    public void saveInfoToLocal(Result result) {
        topRatedContract.showLoading();
        topRatedContract.setLoadingIndicator(true);

        useCaseHandler.execute(saveMovieToLocal, new SaveMovieToLocal.RequestValues(transform(result)),
                new UseCase.UseCaseCallback<SaveMovieToLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveMovieToLocal.ResponseValues response) {
                        topRatedContract.setLoadingIndicator(false);
                        topRatedContract.hideLoading();

                        processResponseOfSave(response.saveInfoResult());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    public void deleteMovieFromLocal(boolean type, Result result){
        topRatedContract.showLoading();
        topRatedContract.setLoadingIndicator(true);

        useCaseHandler.execute(deleteMoviesInLocalUsecase, new DeleteMoviesInLocal.RequestValues(type,transform(result)),
                new UseCase.UseCaseCallback<DeleteMoviesInLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(DeleteMoviesInLocal.ResponseValues response) {
                        topRatedContract.setLoadingIndicator(false);
                        topRatedContract.hideLoading();

                        processInfo(response.forListRefresh(), true);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void processResponseOfSave(String s) {
        topRatedContract.renderStatusOfSave(s);
    }


    public MovieResultEntity transform(Result result){
        MovieResultEntity movieResultEntity = null;
        if(result != null){
            movieResultEntity = new MovieResultEntity();
            movieResultEntity.setId(result.getId());
            movieResultEntity.setAdult(result.getAdult());
            movieResultEntity.setBackdropPath(result.getBackdropPath());
            movieResultEntity.setOriginalLanguage(result.getOriginalLanguage());
            movieResultEntity.setOriginalTitle(result.getOriginalTitle());
            movieResultEntity.setOverview(result.getOverview());
            movieResultEntity.setPopularity(result.getPopularity());
            movieResultEntity.setPosterPath(result.getPosterPath());
            movieResultEntity.setReleaseDate(result.getReleaseDate());
            movieResultEntity.setTitle(result.getTitle());
            movieResultEntity.setToprated(true);
        }
        return movieResultEntity;
    }
}
