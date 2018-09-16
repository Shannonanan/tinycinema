//package co.za.tinycinema.features.Library;
//
//import android.support.annotation.NonNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import co.za.tinycinema.common.UseCase;
//import co.za.tinycinema.common.UseCaseHandler;
//import co.za.tinycinema.data.local.MovieResultEntity;
//import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
//import co.za.tinycinema.features.Library.domain.usecase.DeleteMoviesFromLibrary;
//import co.za.tinycinema.features.Library.domain.usecase.GetMoviesFromLibrary;
//
//
//public class LibraryPresenter {
//
//    private final UseCaseHandler mUseCaseHandler;
//    private LibraryContract mContractView;
//    private GetMoviesFromLibrary getMoviesFromLibraryUsecase;
//    private DeleteMoviesFromLibrary deleteMoviesFromLibraryUsecase;
//
//    public LibraryPresenter(UseCaseHandler mUseCaseHandler,
//                                     GetMoviesFromLibrary getMoviesFromLibraryUsecase,
//                                     DeleteMoviesFromLibrary deleteMoviesFromLibrary) {
//        this.mUseCaseHandler = mUseCaseHandler;
//        this.getMoviesFromLibraryUsecase = getMoviesFromLibraryUsecase;
//        this.deleteMoviesFromLibraryUsecase = deleteMoviesFromLibrary;
//    }
//
//
//    public void setView(@NonNull LibraryContract view) {
//        this.mContractView = view;
//    }
//
//
//    public void start() {
//        getMoviesFromLibrary();
//    }
//
//    public void getMoviesFromLibrary(){
//        mContractView.showLoading();
//        mContractView.setLoadingIndicator(true);
//
//        mUseCaseHandler.execute(getMoviesFromLibraryUsecase, new GetMoviesFromLibrary.RequestValues(),
//                new UseCase.UseCaseCallback<GetMoviesFromLibrary.ResponseValues>() {
//                    @Override
//                    public void onSuccess(GetMoviesFromLibrary.ResponseValues response) {
//                        mContractView.setLoadingIndicator(false);
//                        mContractView.hideLoading();
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
//
//    }
//
//    private void processInfo(List<Result> moviesResult, boolean networkStatus) {
//        mContractView.renderInView(moviesResult, networkStatus);
//    }
//
//    /**
//     * deletes entities when coming from the library activity whether offline or not
//     * @param result
//     *
//     */
//    public void deleteFromLocalLibrary(Result result){
//        mContractView.showLoading();
//        mContractView.setLoadingIndicator(true);
//
//        mUseCaseHandler.execute(deleteMoviesFromLibraryUsecase, new DeleteMoviesFromLibrary.RequestValues(transform(result)),
//                new UseCase.UseCaseCallback<DeleteMoviesFromLibrary.ResponseValues>() {
//                    @Override
//                    public void onSuccess(DeleteMoviesFromLibrary.ResponseValues response) {
//                        mContractView.setLoadingIndicator(false);
//                        mContractView.hideLoading();
//
//                        processResponseOfDelete(response.forCallback(), response.forListRefresh());
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//                });
//    }
//
//    private void processResponseOfDelete(String s, List<Result> refreshList) {
//        mContractView.renderInView(refreshList, true);
//    }
//
//
//
//
//    public MovieResultEntity transform(Result result){
//        MovieResultEntity movieResultEntity = null;
//        if(result != null){
//            movieResultEntity = new MovieResultEntity();
//            movieResultEntity.setId(result.getId());
//            movieResultEntity.setAdult(result.getAdult());
//            movieResultEntity.setBackdropPath(result.getBackdropPath());
//            movieResultEntity.setOriginalLanguage(result.getOriginalLanguage());
//            movieResultEntity.setOriginalTitle(result.getOriginalTitle());
//            movieResultEntity.setOverview(result.getOverview());
//            movieResultEntity.setPopularity(result.getPopularity());
//            movieResultEntity.setPosterPath(result.getPosterPath());
//            movieResultEntity.setReleaseDate(result.getReleaseDate());
//            movieResultEntity.setTitle(result.getTitle());
//            movieResultEntity.setToprated(false);
//        }
//        return movieResultEntity;
//    }
//
//}
