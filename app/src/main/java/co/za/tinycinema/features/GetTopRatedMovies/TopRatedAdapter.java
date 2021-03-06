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
import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder> {

    private List<MovieResultEntity> listOfTopeRated;
    Context mContext;
    boolean offlineStatus = false;

    private TopRatedAdapter.OnMovieClickedListener onMovieClickedListener;

    public interface OnMovieClickedListener {
        void onMovieClicked(MovieResultEntity result);
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
        final MovieResultEntity result = this.listOfTopeRated.get(position);

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

    }

    @Override
    public int getItemCount() {
        return (this.listOfTopeRated != null) ? this.listOfTopeRated.size() : 0;
    }

    public class TopRatedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_movie) ImageView imageView;

        public TopRatedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<MovieResultEntity> INFO, boolean networkStatus) {
        this.validateCollection(INFO);
        this.listOfTopeRated = (List<MovieResultEntity>) INFO;
        this.notifyDataSetChanged();
        offlineStatus = networkStatus;
    }

    private void validateCollection(Collection<MovieResultEntity> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnItemClickListener(TopRatedAdapter.OnMovieClickedListener onItemClickListener) {
        this.onMovieClickedListener = onItemClickListener;
    }
}
