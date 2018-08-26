package co.za.tinycinema.data.local;


import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.utils.AppExecutors;

public class LocalDataSource implements DataSource {
    MoviesDao moviesDao;
    private final AppExecutors mExecutors;

    public LocalDataSource(MoviesDao moviesDao, AppExecutors mExecutors) {
        this.moviesDao = moviesDao;
        this.mExecutors = mExecutors;
    }


    @Override
    public void getAllMoviesInTheatre(final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
              List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(false);
              List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.onDataLoaded(transformedFromLocal);
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
                        entity.getOverview(), entity.getReleaseDate()));
            }
        }

        return transfromedFromLocal;
    }



    @Override
    public void getHighestRatedMovies(final LoadInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<MovieResultEntity> moviesInTheatresModelEntity = moviesDao.getAllMovies(true);
                List<Result> transformedFromLocal = new ArrayList<>(transformToSchema(moviesInTheatresModelEntity));
                callback.onDataLoaded(transformedFromLocal);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllInfo() {

    }

    @Override
    public void saveMovie(final MovieResultEntity result, final SaveInfoCallback callback) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
             long getResponse = moviesDao.insertMovie(result);
            callback.savedStatusSuccess("successful");
            }
        };
        mExecutors.diskIO().execute(saveRunnable);
    }


    @Override
    public void refreshTasks() {

    }
}
