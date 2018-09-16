package co.za.tinycinema.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.data.remote.Service;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresPresenter;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.DeleteMoviesInLocal;
//import co.za.tinycinema.features.Library.LibraryPresenter;
import co.za.tinycinema.features.Library.domain.usecase.DeleteMoviesFromLibrary;
//import co.za.tinycinema.features.Library.domain.usecase.GetMoviesFromLibrary;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.SaveMovieToLocal;
//import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesPresenter;
//import co.za.tinycinema.features.GetTopRatedMovies.domain.usecase.GetTopRatedMovies;
import co.za.tinycinema.features.ShowDetails.ShowDetailsPresenter;
import co.za.tinycinema.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {


    private final Activity mActivity;

    public PresentationModule(Activity fragmentActivity) {
        mActivity = fragmentActivity;
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    Context context(Activity activity) {
        return activity;
    }


    @Provides
    UseCaseHandler getUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    @Provides
    RemoteDataSource remoteDataSource(Service service, Context context){
        return new RemoteDataSource(service, context);
    }

//    @Provides
//    LocalDataSource localDataSource(MoviesDao moviesDao, AppExecutors appExecutors){
//        return new LocalDataSource(moviesDao, appExecutors);
//    }

//    @Provides
//    AppExecutors appExecutors()
//    {return  new AppExecutors();
//    }

    @Provides
    Repository repository(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource, Context context, AppExecutors appExecutors){
        return new Repository(mRemoteDataSource, mLocalDataSource, context, appExecutors);
    }

    @Provides
    GetMoviesInTheatres getMoviesInTheatres(Repository repository, Context context){
        return new GetMoviesInTheatres(repository, context);
    }

    @Provides
    SaveMovieToLocal saveMovieToLocal(Repository repository){
        return new SaveMovieToLocal(repository);
    }

    @Provides
    MoviesInTheatresPresenter getMoviesPresenter(GetMoviesInTheatres mGetMovies
                                               //  UseCaseHandler mUseCaseHandler, SaveMovieToLocal saveMovieToLocal,
                                               //  DeleteMoviesInLocal deleteMoviesInLocal)
    ) {
        return new MoviesInTheatresPresenter(mGetMovies);
    }

//    @Provides
//    GetMoviesFromLibrary getMoviesFromLibrary(Repository repository){
//        return new GetMoviesFromLibrary(repository);
//    }

    @Provides
    ShowDetailsPresenter showDetailsPresenter(){
        return new ShowDetailsPresenter();
    }

//    @Provides
//    GetTopRatedMovies getTopRatedMovies(Repository repository, Context context){
//        return  new GetTopRatedMovies(repository, context);
//    }
//
//    @Provides
//    TopRatedMoviesPresenter topRatedMoviesPresenter(GetTopRatedMovies getTopRatedMoviesUsecase,
//                                                    UseCaseHandler useCaseHandler,
//                                                    SaveMovieToLocal saveMovieToLocal,
//                                                    DeleteMoviesInLocal deleteMoviesInLocal){
//        return new TopRatedMoviesPresenter(getTopRatedMoviesUsecase, useCaseHandler, saveMovieToLocal,deleteMoviesInLocal);
//    }

    @Provides
    DeleteMoviesInLocal deleteMoviesInLocal(Repository repository){
        return new DeleteMoviesInLocal(repository);
    }

    @Provides
    DeleteMoviesFromLibrary deleteMoviesFromLibrary(Repository repository){
        return new DeleteMoviesFromLibrary(repository);
    }

//    @Provides
//    LibraryPresenter libraryPresenter(UseCaseHandler mUseCaseHandler,
//                                      GetMoviesFromLibrary getMoviesFromLibraryUsecase,
//                                      DeleteMoviesFromLibrary deleteMoviesFromLibrary){
//        return new LibraryPresenter(mUseCaseHandler,getMoviesFromLibraryUsecase,deleteMoviesFromLibrary);
//    }


}
