package co.za.tinycinema.di.presentation;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import co.za.tinycinema.common.UseCaseHandler;
import co.za.tinycinema.data.Repository;
import co.za.tinycinema.data.local.LocalDataSource;
import co.za.tinycinema.data.remote.RemoteDataSource;
import co.za.tinycinema.data.remote.Service;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresPresenter;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.usecase.GetMoviesInTheatres;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesPresenter;
import co.za.tinycinema.features.GetTopRatedMovies.domain.usecase.GetTopRatedMovies;
import co.za.tinycinema.features.ShowDetails.ShowDetailsPresenter;
import co.za.tinycinema.features.common.ImageLoader;
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
    RemoteDataSource remoteDataSource(Service service){
        return new RemoteDataSource(service);
    }

    @Provides
    LocalDataSource localDataSource(){
        return new LocalDataSource();
    }

    @Provides
    Repository repository(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource, Context context){
        return new Repository(mRemoteDataSource, mLocalDataSource, context);
    }

    @Provides
    GetMoviesInTheatres getMoviesInTheatres(Repository repository){
        return new GetMoviesInTheatres(repository);
    }

    @Provides
    MoviesInTheatresPresenter getMoviesPresenter(GetMoviesInTheatres mGetMovies,
                                                 UseCaseHandler mUseCaseHandler) {
        return new MoviesInTheatresPresenter(mGetMovies, mUseCaseHandler);
    }

    @Provides
    ShowDetailsPresenter showDetailsPresenter(){
        return new ShowDetailsPresenter();
    }

    @Provides
    GetTopRatedMovies getTopRatedMovies(Repository repository){
        return  new GetTopRatedMovies(repository);
    }

    @Provides
    TopRatedMoviesPresenter topRatedMoviesPresenter(GetTopRatedMovies getTopRatedMoviesUsecase, UseCaseHandler useCaseHandler){
        return new TopRatedMoviesPresenter(getTopRatedMoviesUsecase, useCaseHandler);
    }


}
