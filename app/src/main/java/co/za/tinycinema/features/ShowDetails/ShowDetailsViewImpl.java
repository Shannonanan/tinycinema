package co.za.tinycinema.features.ShowDetails;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.BaseViewMvc;

public class ShowDetailsViewImpl extends BaseViewMvc<ShowDetailsContract.Listener> implements ShowDetailsContract{

    @BindView(R.id.tv_description) TextView mPlot;
    @BindView(R.id.tv_rating_details)TextView mRating;
    @BindView(R.id.tv_releasedate_details) TextView mReleaseDate;
    @BindView(R.id.tv_title_detail) TextView mTitle;
    //@BindView(R.id.tv_orig_lang) TextView tv_original_language;
   // @BindView(R.id.tv_orig_title) TextView tv_orig_title;
    @BindView(R.id.iv_details_big) ImageView thumbnail;
    @BindView(R.id.iv_thumbnail) ImageView thumbnailSmall;
    @BindView(R.id.scrollview_details) ScrollView scrollView;
    @BindView(R.id.btn_save_detail) FloatingActionButton btn_save;
    @BindView(R.id.btn_delete_detail) FloatingActionButton btn_delete;
  //  @BindView(R.id.btn_review) ImageButton btn_review;
 //  @BindView(R.id.btn_trailer) ImageButton btn_trailer;



    MovieResultEntity entity;

    public ShowDetailsViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_show_details2, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);

    }

    @Override
    public void setupViews(MovieResultEntity result) {

//        if(offlineStatus){
////            btn_save.setVisibility(View.GONE);
////            delete.setVisibility(View.VISIBLE);
////        }
////        else {
////            btn_save.setVisibility(View.VISIBLE);
////            delete.setVisibility(View.GONE);
////        }
        entity = result;
        String url = getContext().getString(R.string.image_base_url) + result.getPosterPath();
        Glide.with(getContext())
                .load(url)
                .thumbnail(0.1f)
                .into(thumbnailSmall);


        Glide.with(getContext())
                .load(url)
                .into(thumbnail);




        mPlot.setText(result.getOverview());
        mTitle.setText(result.getTitle());
     //   tv_original_language.setText(result.getOriginalLanguage());
     //   tv_orig_title.setText(result.getOriginalTitle());
        if(result.getVoteAverage() != null){
        Double voteAv = result.getVoteAverage();
        String dbl = Double.toString(voteAv);
        mRating.setText(dbl);}
        mReleaseDate.setText(result.getReleaseDate());
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @OnClick(R.id.btn_save_detail)
    public void click(){
        if(btn_save != null){
        btn_save.setVisibility(View.GONE);
        btn_delete.setVisibility(View.VISIBLE);}
        for (Listener list:getListeners()) {
            list.onSaveMovieToLocalClicked(entity);
        }
    }
//
//    @OnClick(R.id.btn_trailer)
//    public void onClickTrailer(){
//        for (Listener list:getListeners()) {
//            list.onTrailerBtnClicked(entity.getId());
//        }
//    }

    @OnClick(R.id.btn_delete_detail)
    public void onClick(){
         if(btn_delete != null){
            btn_save.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.GONE);
        }
        for (Listener list:getListeners()) {
            list.onRemoveMovieFromLocalClicked(entity);
        }
    }

//    @OnClick(R.id.btn_review)
//    public void clickReviews(){
//        for (Listener list:getListeners()) {
//            list.onReviewClicked(entity.getId());
//        }
//    }

   @Override
    public void renderStatusOfSave(String status) {
        for (Listener listener : getListeners()) {
            listener.renderStatusOfSave(status);

        }
    }

    @Override
    public void renderDeleteInView(String success) {
        Toast.makeText(getContext(), getString(R.string.deleted),Toast.LENGTH_LONG).show();
    }

    @Override
    public void renderGetVideoId(String id) {
        for (Listener listener : getListeners()) {
            listener.watchVideo(id);
        }
    }

    @Override
    public void renderCheckMovieSavedInView(Boolean status) {
        if(status){
            if(btn_save != null){
            btn_save.setVisibility(View.GONE);
            btn_delete.setVisibility(View.VISIBLE);}
        }else{
            if(btn_delete != null){
            btn_save.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.GONE);}
        }
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
