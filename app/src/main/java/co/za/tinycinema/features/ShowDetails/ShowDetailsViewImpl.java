package co.za.tinycinema.features.ShowDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.BaseViewMvc;

public class ShowDetailsViewImpl extends BaseViewMvc<ShowDetailsContract.Listener> implements ShowDetailsContract{

    @BindView(R.id.tv_plot) TextView mPlot;
    @BindView(R.id.tv_rating)TextView mRating;
    @BindView(R.id.tv_release_date) TextView mReleaseDate;
    @BindView(R.id.tv_title) TextView mTitle;
    @BindView(R.id.iv_thumbnail) ImageView thumbnail;
    @BindView(R.id.scrollView) ScrollView scrollView;

    public ShowDetailsViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_show_details, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);

    }

    @Override
    public void setupViews(Result result) {

        String url = getContext().getString(R.string.image_base_url) + result.getPosterPath();
        Glide.with(getContext())
                .load(url)
                .thumbnail(0.1f)
                .into(thumbnail);


        mPlot.setText(result.getOverview());
        mTitle.setText(result.getTitle());
        if(result.getVoteAverage() != null){
        Double voteAv = result.getVoteAverage();
        String dbl = Double.toString(voteAv);
        mRating.setText(dbl);}
        mReleaseDate.setText(result.getReleaseDate());
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