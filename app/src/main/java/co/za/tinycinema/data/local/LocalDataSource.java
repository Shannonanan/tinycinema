package co.za.tinycinema.data.local;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.R;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.utils.AppExecutors;

public class LocalDataSource implements DataSource {
    MoviesDao moviesDao;
    private final AppExecutors mExecutors;
    Context context;

    public LocalDataSource(MoviesDao moviesDao, AppExecutors mExecutors) {
        this.moviesDao = moviesDao;
        this.mExecutors = mExecutors;
    }


    @Override
    public void getAllMoviesInTheatre(Context context, final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
              List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(false);
              List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.onDataLoaded(transformedFromLocal, true);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void getMoviesFromLibrary(final LoadInfoCallback loadInfoCallback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
                List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                loadInfoCallback.onDataLoaded(transformedFromLocal, true);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    private List<Result> transformToSchema(List<MovieResultEntity> movieResultEntities) {
        List<Result> transfromedFromLocal = null;
        if(movieResultEntities != null){
            transfromedFromLocal = new ArrayList<>();
            for (MovieResultEntity entity: movieResultEntities) {
                transfromedFromLocal.add(new Result(entity.getVoteCount(), entity.getId(), entity.getVideo(), entity.getVoteAverage(),
                        entity.getTitle(), entity.getPopularity(), entity.getPosterPath(),entity.getOriginalLanguage(),
                        entity.getOriginalTitle(), entity.getBackdropPath(), entity.getAdult(),
                        entity.getOverview(), entity.getReleaseDate(), true));
            }
        }

        return transfromedFromLocal;
    }



    @Override
    public void getHighestRatedMovies(Context context, final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(true);
                List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.onDataLoaded(transformedFromLocal, true);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteMovie(final boolean type, final MovieResultEntity entity, final DeleteInfoCallback callback) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(type);
                List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess(latestResults,"success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }

    public void deleteMovieFromLibrary(final MovieResultEntity entity, final DeleteInfoCallback callback){
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovie(entity);
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMoviesFromLibrary();
                List<Result> latestResults = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.deleteStatusSuccess(latestResults,"success");
            }
        };

        mExecutors.diskIO().execute(deleteRunnable);
    }


    @Override
    public void saveMovie(final MovieResultEntity result, final SaveInfoCallback callback) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
             long getResponse = moviesDao.insertMovie(result);
            callback.savedStatusSuccess("success");
            }
        };
        mExecutors.diskIO().execute(saveRunnable);
    }



}
