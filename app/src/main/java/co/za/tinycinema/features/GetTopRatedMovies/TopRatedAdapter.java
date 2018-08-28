package co.za.tinycinema.features.GetTopRatedMovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder> {

    private List<Result> listOfTopeRated;
    Context mContext;
    boolean offlineStatus = false;

    private TopRatedAdapter.OnMovieClickedListener onMovieClickedListener;

    public interface OnMovieClickedListener {
        void onMovieClicked(Result result);
        void onSaveButtonClicked(Result result);
        void onDeleteButtonClicked(boolean type, Result result);
    }

    public TopRatedAdapter(Context mContext) {
        this.listOfTopeRated = Collections.emptyList();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TopRatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TopRatedViewHolder viewHolder = new TopRatedViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedViewHolder holder, int position) {
        final Result result = this.listOfTopeRated.get(position);
        if(offlineStatus){
            holder.btn_save.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }
        else {
            holder.btn_save.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
        }

        String movieCode = result.getPosterPath();
        if(movieCode == null || movieCode.isEmpty()){
            Glide.with(mContext)
                    .load(R.drawable.photo_camera)
                    .into(holder.imageView);
        }else{
        String url = mContext.getString(R.string.image_base_url) + movieCode;
        Glide.with(mContext)
                .load(url)
                .into(holder.imageView);}

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(onMovieClickedListener != null){
                 onMovieClickedListener.onMovieClicked(result);}
             }
         });

        holder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickedListener != null) {
                    onMovieClickedListener.onSaveButtonClicked(result);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickedListener != null) {
                    onMovieClickedListener.onDeleteButtonClicked(true, result);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.listOfTopeRated != null) ? this.listOfTopeRated.size() : 0;
    }

    public class TopRatedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_movie) ImageView imageView;
        @BindView(R.id.btn_save) Button btn_save;
        @BindView(R.id.btn_delete) Button delete;

        public TopRatedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<Result> INFO, boolean networkStatus) {
        this.validateCollection(INFO);
        this.listOfTopeRated = (List<Result>) INFO;
        this.notifyDataSetChanged();
        offlineStatus = networkStatus;
    }

    private void validateCollection(Collection<Result> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnItemClickListener(TopRatedAdapter.OnMovieClickedListener onItemClickListener) {
        this.onMovieClickedListener = onItemClickListener;
    }
}
