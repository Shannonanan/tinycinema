package co.za.tinycinema.features.ShowDetails;

import android.support.annotation.NonNull;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.ShowDetails.domain.usecase.CheckSavedMovieInLocal;
import co.za.tinycinema.features.ShowDetails.domain.usecase.DeleteMoviesInLocal;
import co.za.tinycinema.features.ShowDetails.domain.usecase.GetVideoId;
import co.za.tinycinema.features.ShowDetails.domain.usecase.SaveMovieToLocal;

public class ShowDetailsPresenter {

    private ShowDetailsContract showDetailsContract;
    private SaveMovieToLocal saveMovieToLocalUseCase;
    private DeleteMoviesInLocal deleteMoviesInLocalUseCase;
    private CheckSavedMovieInLocal checkSavedMovieInLocal;
    private GetVideoId getVideoIdUsecase;
    private UseCaseHandler mUseCaseHandler;

    public ShowDetailsPresenter(SaveMovieToLocal saveMovieToLocalUseCase,
                                DeleteMoviesInLocal deleteMoviesInLocalUseCase,
                                CheckSavedMovieInLocal checkSavedMovieInLocal, GetVideoId getVideoIdUsecase, UseCaseHandler useCaseHandler) {
        this.saveMovieToLocalUseCase = saveMovieToLocalUseCase;
        this.deleteMoviesInLocalUseCase = deleteMoviesInLocalUseCase;
        this.checkSavedMovieInLocal = checkSavedMovieInLocal;
        this.getVideoIdUsecase = getVideoIdUsecase;
        this.mUseCaseHandler = useCaseHandler;
    }


    public void setView(@NonNull ShowDetailsContract view) {
        this.showDetailsContract = view;
    }

    public void setupViews(MovieResultEntity result) {
        showDetailsContract.setupViews(result);
    }

    public void saveToLocalFavourites(MovieResultEntity entity) {

        showDetailsContract.showLoading();
        showDetailsContract.setLoadingIndicator(true);

        mUseCaseHandler.execute(saveMovieToLocalUseCase, new SaveMovieToLocal.RequestValues(entity),
                new UseCase.UseCaseCallback<SaveMovieToLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveMovieToLocal.ResponseValues response) {
                        showDetailsContract.setLoadingIndicator(false);
                        showDetailsContract.hideLoading();

                        processResponseOfSave(response.saveInfoResult());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });


    }

    private void processResponseOfSave(String s) {
        showDetailsContract.renderStatusOfSave(s);
    }

    /**
     * deletes entities from favourites
     */
    public void removeFromLocal( MovieResultEntity movieResultEntity) {
        showDetailsContract.showLoading();
        showDetailsContract.setLoadingIndicator(true);

        mUseCaseHandler.execute(deleteMoviesInLocalUseCase, new DeleteMoviesInLocal.RequestValues(movieResultEntity),
                new UseCase.UseCaseCallback<DeleteMoviesInLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(DeleteMoviesInLocal.ResponseValues response) {
                        showDetailsContract.setLoadingIndicator(false);
                        showDetailsContract.hideLoading();

                        processResponseOfDelete(response.forCallback());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void processResponseOfDelete(String s) {
        showDetailsContract.renderDeleteInView(s);
    }

    public void checkIfInLocal(Integer id) {
        showDetailsContract.showLoading();
        showDetailsContract.setLoadingIndicator(true);
        mUseCaseHandler.execute(checkSavedMovieInLocal, new CheckSavedMovieInLocal.RequestValues(id),
                new UseCase.UseCaseCallback<CheckSavedMovieInLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(CheckSavedMovieInLocal.ResponseValues response) {
                        showDetailsContract.setLoadingIndicator(false);
                        showDetailsContract.hideLoading();

                        processResponseOfCheck(response.forCallback());
                    }

                    @Override
                    public void onError(String error) {

                    }

                });
    }

    private void processResponseOfCheck(Boolean status) {
        showDetailsContract.renderCheckMovieSavedInView(status);
    }

    public void getVideoId(Integer movieId) {
        showDetailsContract.showLoading();
        showDetailsContract.setLoadingIndicator(true);

        mUseCaseHandler.execute(getVideoIdUsecase, new GetVideoId.RequestValues(movieId),
                new UseCase.UseCaseCallback<GetVideoId.ResponseValues>() {
                    @Override
                    public void onSuccess(GetVideoId.ResponseValues response) {
                        showDetailsContract.setLoadingIndicator(false);
                        showDetailsContract.hideLoading();

                        processResponseOfVideoId(response.saveVideoIdResult());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void processResponseOfVideoId(String s) {
        showDetailsContract.renderGetVideoId(s);
    }
}
