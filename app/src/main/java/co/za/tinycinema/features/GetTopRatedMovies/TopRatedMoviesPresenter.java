package co.za.tinycinema.features.GetTopRatedMovies;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetTopRatedMovies.domain.usecase.GetTopRatedMovies;

public class TopRatedMoviesPresenter {

    private GetTopRatedMovies getTopRatedMoviesUsecase;
    private final UseCaseHandler useCaseHandler;
    private TopRatedContract topRatedContract;

    public TopRatedMoviesPresenter(GetTopRatedMovies getTopRatedMoviesUsecase, UseCaseHandler useCaseHandler) {
        this.getTopRatedMoviesUsecase = getTopRatedMoviesUsecase;
        this.useCaseHandler = useCaseHandler;
    }

    public void setView(@NonNull TopRatedContract topRatedContract){
        this.topRatedContract = topRatedContract;
    }

    public void loadTopRatedMovies(final boolean showLoadingUI){
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
                processInfo(moviesResult);
            }

            @Override
            public void onError() {

            }
        });

    }

    private void processInfo(List<Result> moviesResult) {
        topRatedContract.renderInView(moviesResult);
    }
}
