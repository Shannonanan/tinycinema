package co.za.tinycinema.features.ShowDetails;

import android.support.annotation.NonNull;

import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.DeleteMoviesInLocal;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.SaveMovieToLocal;

public class ShowDetailsPresenter  {

    private ShowDetailsContract showDetailsContract;
    private SaveMovieToLocal saveMovieToLocalUseCase;
    private DeleteMoviesInLocal deleteMoviesInLocalUseCase;
    private UseCaseHandler mUseCaseHandler;

    public ShowDetailsPresenter(SaveMovieToLocal saveMovieToLocalUseCase,
                                DeleteMoviesInLocal deleteMoviesInLocalUseCase,
                                UseCaseHandler useCaseHandler) {
        this.saveMovieToLocalUseCase = saveMovieToLocalUseCase;
        this.deleteMoviesInLocalUseCase = deleteMoviesInLocalUseCase;
        this.mUseCaseHandler = useCaseHandler;
    }


    public void setView(@NonNull ShowDetailsContract view) {
        this.showDetailsContract = view;
    }

    public void setupViews(MovieResultEntity result)
    {
        showDetailsContract.setupViews(result);
    }

    public void saveToLocalFavourites(MovieResultEntity entity){

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
    public void removeFromLocal(MovieResultEntity movieResultEntity) {
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

    public void checkIfInLocal(MovieResultEntity result) {
        //TODO:CHECK IF IN LOCAL
    }
}
