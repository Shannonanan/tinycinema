package co.za.tinycinema.features.GetReviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetReviews.Domain.model.Result;

public class GetReviewsAdapter extends RecyclerView.Adapter<GetReviewsAdapter.GetReviewsViewHolder> {

    private List<Result> getReview;
    private Context mContext;

    public GetReviewsAdapter(Context context) {
        this.getReview = Collections.emptyList();
        mContext = context;
    }

    @NonNull
    @Override
    public GetReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GetReviewsViewHolder viewHolder = new GetReviewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetReviewsViewHolder holder, int position) {
            Result getReview =  this.getReview.get(position);

            holder.tv_ReviewTitle.setText(getReview.getAuthor());
            holder.tv_review.setText(getReview.getContent());
            holder.tv_url.setText(getReview.getUrl());
    }

    @Override
    public int getItemCount() {
        return (this.getReview != null) ? this.getReview.size() : 0;
    }

    static class GetReviewsViewHolder extends RecyclerView.ViewHolder{

       @BindView(R.id.tv_review) TextView tv_review;
       @BindView(R.id.tv_review_title) TextView tv_ReviewTitle;
       @BindView(R.id.tv_url) TextView tv_url;


       public GetReviewsViewHolder(View itemView) {
           super(itemView);

           ButterKnife.bind(this, itemView);
       }
   }
    public void setInfoCollection(List<Result> INFO) {
        this.getReview = (List<Result>) INFO;
        this.notifyDataSetChanged();
    }
}
