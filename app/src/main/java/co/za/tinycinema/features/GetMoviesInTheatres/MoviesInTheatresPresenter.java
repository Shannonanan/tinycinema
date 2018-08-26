package co.za.tinycinema.features.GetMoviesInTheatres;


import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.SaveMovieToLocal;

public class MoviesInTheatresPresenter {

    private GetMoviesInTheatres getMoviesInTheatresUseCase;
    private final UseCaseHandler mUseCaseHandler;
    private MoviesInTheatresContract mContractView;
    private SaveMovieToLocal saveMovieToLocalUsecase;

    public MoviesInTheatresPresenter(GetMoviesInTheatres getMoviesInTheatresUseCase, UseCaseHandler mUseCaseHandler, SaveMovieToLocal saveMovieToLocal) {
        this.getMoviesInTheatresUseCase = getMoviesInTheatresUseCase;
        this.mUseCaseHandler = mUseCaseHandler;
        this.saveMovieToLocalUsecase = saveMovieToLocal;
    }


    public void setView(@NonNull MoviesInTheatresContract view) {
        this.mContractView = view;
    }


    public void start() {
        loadInfo(true);
    }

    private void loadInfo(final boolean showLoadingUI) {
        if (showLoadingUI) {
            mContractView.showLoading();
            mContractView.setLoadingIndicator(true);
        }

        mUseCaseHandler.execute(getMoviesInTheatresUseCase, new GetMoviesInTheatres.RequestValues(),
                new UseCase.UseCaseCallback<GetMoviesInTheatres.ResponseValue>() {
                    @Override
                    public void onSuccess(GetMoviesInTheatres.ResponseValue response) {

                        if (showLoadingUI) {
                            mContractView.setLoadingIndicator(false);
                            mContractView.hideLoading();
                        }

                        List<Result> moviesResult = new ArrayList<>();
                        moviesResult.addAll(response.getInfo());
                        processInfo(moviesResult);
                    }

                    @Override
                    public void onError(String error) {
                        if (!mContractView.isActive()) {
                            return;
                        }
                        mContractView.showLoadingTasksError(error);
                    }
                });
    }

    private void processInfo(List<Result> moviesResult) {
        mContractView.renderInView(moviesResult);
    }

    public void saveInfoToLocal(Result result) {
        mContractView.showLoading();
        mContractView.setLoadingIndicator(true);

        mUseCaseHandler.execute(saveMovieToLocalUsecase, new SaveMovieToLocal.RequestValues(transform(result)),
                new UseCase.UseCaseCallback<SaveMovieToLocal.ResponseValues>() {
                    @Override
                    public void onSuccess(SaveMovieToLocal.ResponseValues response) {
                        mContractView.setLoadingIndicator(false);
                        mContractView.hideLoading();

                        processResponseOfSave(response.saveInfoResult());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    private void processResponseOfSave(String s) {
        mContractView.renderStatusOfSave(s);
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
            movieResultEntity.setToprated(false);
        }
        return movieResultEntity;
    }


}
