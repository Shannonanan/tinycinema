package co.za.tinycinema.features.GetTopRatedMovies;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.BaseViewMvc;

public class TopRatedMoviesViewImpl extends BaseViewMvc<TopRatedContract.Listener> implements TopRatedContract {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_progress_lottie)
    RelativeLayout rl_progress;
    @BindView(R.id.animation_view)
    LottieAnimationView pb_progress;
    private boolean isActive;

    private TopRatedAdapter topRatedAdapter;

    public TopRatedMoviesViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_top_rated_movies, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);
        setupAdapter();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(topRatedAdapter);
    }

    private void setupAdapter() {
        topRatedAdapter = new TopRatedAdapter(getContext());

        TopRatedAdapter.OnMovieClickedListener onMovieClickedListener = new TopRatedAdapter.OnMovieClickedListener() {
            @Override
            public void onMovieClicked(Result result) {
                for (Listener listener : getListeners()) {
                    listener.OnMoviePosterClicked(result);
                }
            }
        };

        topRatedAdapter.setOnItemClickListener(onMovieClickedListener);
    }


    @Override
    public void renderInView(List<Result> movieInfo) {
        if (movieInfo != null) {
            this.topRatedAdapter.setInfoCollection(movieInfo);
        }
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        isActive = active;
    }

    @Override
    public void showLoadingTasksError() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rl_progress != null) {
            this.rl_progress.setVisibility(View.GONE);
        }
    }

    @Override
    public Context applicationContext() {
        return this.getContext().getApplicationContext();
    }

    @Override
    public Context activityContext() {
        return this.getContext();
    }
}
