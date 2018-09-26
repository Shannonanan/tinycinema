package co.za.tinycinema.features.GetReviews;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.features.GetReviews.Domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.BaseViewMvc;

public class GetReviewsViewImpl  extends BaseViewMvc<GetReviewsContract.Listener>
        implements GetReviewsContract {

    @BindView(R.id.recyclerViewReviews)
    RecyclerView recyclerView;
    @BindView(R.id.rl_progress_lottie)
    RelativeLayout rl_progress;
    @BindView(R.id.animation_view_load)
    LottieAnimationView pb_progress;
    private boolean isActive;
    @BindView(R.id.nothing_layout)
    RelativeLayout nothing_view;

    private GetReviewsAdapter getReviewsAdapter;

    public GetReviewsViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_reviews, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);
        setupAdapter();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(getReviewsAdapter);
    }

    private void setupAdapter() {
        getReviewsAdapter = new GetReviewsAdapter(getContext());
    }

    @Override
    public void renderInView(List<Result> reviewEntities) {
        if (reviewEntities != null) {
            this.getReviewsAdapter.setInfoCollection(reviewEntities);
        }
        if (reviewEntities.size() == 0) {
            showNothingView();
        }
        else {hideNothingView();}
    }


    private void showNothingView() {
        nothing_view.setVisibility(View.VISIBLE);
    }


    private void hideNothingView() {
        if(nothing_view != null)
            this.nothing_view.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context applicationContext() {
        return null;
    }

    @Override
    public Context activityContext() {
        return null;
    }
}
