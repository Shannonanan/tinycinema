package co.za.tinycinema.features.Library;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.common.mvcViews.BaseViewMvc;

public class LibraryViewImpl  extends BaseViewMvc<LibraryContract.Listener>
        implements LibraryContract {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_progress_lottie)
    RelativeLayout rl_progress;
    @BindView(R.id.animation_view_load)
    LottieAnimationView pb_progress;
    private boolean isActive;
    @BindView(R.id.nothing_layout)
    RelativeLayout nothing_view;

    private LibraryAdapter libraryAdapter;


    public LibraryViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);
        setupAdapter();
        setupRecyclerView();
    }

    private void setupAdapter() {
        libraryAdapter = new LibraryAdapter(getContext());

        LibraryAdapter.OnMoviePosterClicked onMoviePosterClicked = new LibraryAdapter.OnMoviePosterClicked() {
            @Override
            public void onMoviePosterClicked(MovieResultEntity result) {
                for (Listener listener : getListeners()) {
                    listener.OnMoviePosterClicked(result);
                }
            }

            @Override
            public void onDeleteButtonClicked(MovieResultEntity result) {
                for (Listener list : getListeners()) {
                    list.onDeleteButtonClicked(result);

                }
            }
        };

        libraryAdapter.setOnItemClickListener(onMoviePosterClicked);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(libraryAdapter);
    }


    @Override
    public void showCount(List<Integer> checkCount) {
        List<Integer> intLIst = new ArrayList<>();
        intLIst.addAll(checkCount);
        String check = intLIst.toString();
      //  Toast.makeText(getContext(), check, Toast.LENGTH_LONG).show();
    }

    @Override
    public void renderInView(List<MovieResultEntity> movieInfo, boolean networkStatus) {
        if (movieInfo != null) {
            this.libraryAdapter.setInfoCollection(movieInfo, networkStatus);
        }
        if (movieInfo.size() == 0) {
            showNothingView();
        }
        else {hideNothingView();}
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        isActive = active;

    }

    @Override
    public void showLoadingTasksError(String error) {

    }

    @Override
    public void showNothingView() {
        nothing_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNothingView() {
        if(nothing_view != null)
            this.nothing_view.setVisibility(View.GONE);
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
