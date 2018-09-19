package co.za.tinycinema.features.GetMoviesInTheatres;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;


public class MoviesInTheatresPresenter extends ViewModel {

    private GetMoviesInTheatres getMoviesInTheatresUseCase;


    private final LiveData<List<MovieResultEntity>> mMovieResults;
 //   private final UseCaseHandler mUseCaseHandler;
    private MoviesInTheatresContract mContractView;
  //  private SaveMovieToLocal saveMovieToLocalUsecase;
 //   private DeleteMoviesInLocal deleteMoviesInLocalUsecase;


    public MoviesInTheatresPresenter(
            GetMoviesInTheatres getMoviesInTheatresUseCase)
              //                       UseCaseHandler mUseCaseHandler,
               //                      SaveMovieToLocal saveMovieToLocal,
               //                      DeleteMoviesInLocal deleteMoviesInLocalUsecase)
             {
        this.getMoviesInTheatresUseCase = getMoviesInTheatresUseCase;
   //     this.mUseCaseHandler = mUseCaseHandler;
  //      this.saveMovieToLocalUsecase = saveMovieToLocal;
   //     this.deleteMoviesInLocalUsecase = deleteMoviesInLocalUsecase;
        mMovieResults = getMoviesInTheatresUseCase.executeGetMoviesUseCase();
    }


    public LiveData<List<MovieResultEntity>> getmMovieResults() {
        return mMovieResults;
    }

//    public void getMoviesRemotely(){
//        getMoviesInTheatresUseCase.executeGetMoviesUseCase(new DataSource.LoadInfoCallback() {
//            @Override
//            public void onDataLoaded(List<MovieResultEntity> results, boolean offline) {
//                mContractView.renderInView(results, false);
//            }
//
//            @Override
//            public void onDataNotAvailable(String noDataAvailable) {
//
//            }
//        });
//    }


    public void setView(@NonNull MoviesInTheatresContract view) {
        this.mContractView = view;
    }


  //  public void start() {
 //       loadInfo(true);
 //   }

//    private void loadInfo(final boolean showLoadingUI) {
//        if (showLoadingUI) {
//            mContractView.showLoading();
//            mContractView.setLoadingIndicator(true);
//        }

//        mUseCaseHandler.execute(getMoviesInTheatresUseCase, new GetMoviesInTheatres.RequestValues(),
//                new UseCase.UseCaseCallback<GetMoviesInTheatres.ResponseValue>() {
//                    @Override
//                    public void onSuccess(GetMoviesInTheatres.ResponseValue response) {
//
//                        if (showLoadingUI) {
//                            mContractView.setLoadingIndicator(false);
//                            mContractView.hideLoading();
//                        }
//
//                        List<Result> moviesResult = new ArrayList<>();
//                        moviesResult.addAll(response.getInfo());
//                        processInfo(moviesResult, response.getNetWorkStatus());
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        if (!mContractView.isActive()) {
//                            return;
//                        }
//                        mContractView.showLoadingTasksError(error);
//                    }
//                });
//    }

//    private void processInfo(List<Result> moviesResult, boolean networkStatus) {
//        mContractView.renderInView(moviesResult, networkStatus);
//    }





  //  private void processResponseOfSave(String s) {
  //      mContractView.renderStatusOfSave(s);
  //  }


    public MovieResultEntity transform(Result result) {
        MovieResultEntity movieResultEntity = null;
        if (result != null) {
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
            movieResultEntity.setVoteAverage(result.getVoteAverage());
            movieResultEntity.setToprated(false);
        }
        return movieResultEntity;
    }


}
