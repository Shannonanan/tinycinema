package co.za.tinycinema.features.GetMoviesInTheatres;


import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.za.tinycinema.common.UseCase;
import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;

public class MoviesInTheatresPresenter {

    private GetMoviesInTheatres getMoviesInTheatresUseCase;
    private final UseCaseHandler mUseCaseHandler;
    private MoviesInTheatresContract mContractView;

    public MoviesInTheatresPresenter(GetMoviesInTheatres getMoviesInTheatresUseCase, UseCaseHandler mUseCaseHandler) {
        this.getMoviesInTheatresUseCase = getMoviesInTheatresUseCase;
        this.mUseCaseHandler = mUseCaseHandler;
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

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String formattedDate = df.format(c);



        mUseCaseHandler.execute(getMoviesInTheatresUseCase, new GetMoviesInTheatres.RequestValues(formattedDate),
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
                    public void onError() {
                        if (!mContractView.isActive()) {
                            return;
                        }
                        mContractView.showLoadingTasksError();
                    }
                });
    }

    private void processInfo(List<Result> moviesResult) {
        mContractView.renderInView(moviesResult);
    }

}
