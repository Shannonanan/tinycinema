package co.za.tinycinema.data.remote;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.za.tinycinema.R;
import co.za.tinycinema.data.DataSource;
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
    public void getAllMoviesInTheatre(final LoadInfoCallback callback) {
       final List<Result> results = new ArrayList<>();

            mCall = service.getMoviesInTheatres("");
            mCall.enqueue(new Callback<MoviesInTheatresModel>() {
                @Override
                public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                    if (response.body() != null) {
                        if (response.isSuccessful()) {

                            for (Result result:response.body().getResults()) {
                                results.add(result);
                            }

                        }

                        callback.onDataLoaded(results);
                    }

                }

                @Override
                public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                    callback.onDataNotAvailable();
                }
            });
    }


    @Override
    public void getHighestRatedMovies(final LoadInfoCallback callback) {

        final List<Result> results = new ArrayList<>();

        mCall = service.getTopVotedMoviesInTheatres("");
        mCall.enqueue(new Callback<MoviesInTheatresModel>() {
            @Override
            public void onResponse(Call<MoviesInTheatresModel> call, Response<MoviesInTheatresModel> response) {
                if(response.body()!=null){
                    if(response.isSuccessful()){

                        results.addAll(response.body().getResults());
                        callback.onDataLoaded(results);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesInTheatresModel> call, Throwable t) {
                    callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllInfo() {

    }

    @Override
    public void saveTask(Result marbles) {

    }

    @Override
    public void refreshTasks() {

    }
}
