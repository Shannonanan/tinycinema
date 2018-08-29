package co.za.tinycinema.data.remote;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.R;
import co.za.tinycinema.data.DataSource;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.MoviesInTheatresModel;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {

    private final Service service;


    @Nullable
    private Call<MoviesInTheatresModel> mCall;

    public RemoteDataSource(Service service ) {
        this.service = service;
    }


    @Override
    public void getAllMoviesInTheatre(final Context context, final LoadInfoCallback callback) {
       final List<Result> results = new ArrayList<>();

            mCall = service.getMoviesInTheatres("");
            mCall.enqueue(new Callback<MoviesInTheatresModel>() {
                @Override
                public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                    if (response.body() != null) {
                        if (response.isSuccessful()) {
                       results.addAll(response.body().getResults());

                        }

                        callback.onDataLoaded(results, false);
                    }

                }

                @Override
                public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                    callback.onDataNotAvailable(context.getString(R.string.no_data_available));
                }
            });
    }


    @Override
    public void getHighestRatedMovies(final Context context, final LoadInfoCallback callback) {

        final List<Result> results = new ArrayList<>();

        mCall = service.getTopVotedMoviesInTheatres("");
        mCall.enqueue(new Callback<MoviesInTheatresModel>() {
            @Override
            public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                if(response.body()!=null){
                    if(response.isSuccessful()){

                        results.addAll(response.body().getResults());
                        callback.onDataLoaded(results, false);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                    callback.onDataNotAvailable(context.getString(R.string.no_data_available));
            }
        });
    }

    @Override
    public void getMoviesFromLibrary(LoadInfoCallback callback) {

    }

    @Override
    public void deleteMovie(boolean type,MovieResultEntity entity, DeleteInfoCallback callback) {

    }


    @Override
    public void saveMovie(MovieResultEntity result, SaveInfoCallback callback) {

    }

}
