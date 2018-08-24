package co.za.tinycinema.features.GetMoviesInTheatres;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.tinycinema.R;
import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;

public class MoviesInTheatresAdapter extends RecyclerView.Adapter<MoviesInTheatresAdapter.GetMoviesViewHolder> {

    List<Result> getMoviesCollection;
    private Context mContext;
    private MoviesInTheatresAdapter.OnMoviePosterClicked onItemClickListener;

    //setup a listener for your posts in the recyclerview
    public interface OnMoviePosterClicked {
        void onMoviePosterClicked(Result result);
    }

    public MoviesInTheatresAdapter(Context context) {
        this.getMoviesCollection = Collections.emptyList();
        mContext = context;
    }

    @NonNull
    @Override
    public GetMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GetMoviesViewHolder viewHolder = new GetMoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetMoviesViewHolder holder, int position) {
        final Result movieResult = this.getMoviesCollection.get(position);

        String movieCode = movieResult.getPosterPath();
        String url = mContext.getString(R.string.image_base_url) + movieCode;
        Glide.with(mContext)
                .load(url)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMoviePosterClicked(movieResult);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.getMoviesCollection != null) ? this.getMoviesCollection.size() : 0;
    }

    static class GetMoviesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_movie) ImageView imageView;

        public GetMoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<Result> INFO) {
        this.validateCollection(INFO);
        this.getMoviesCollection = (List<Result>) INFO;
        this.notifyDataSetChanged();
    }

    private void validateCollection(Collection<Result> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnItemClickListener (MoviesInTheatresAdapter.OnMoviePosterClicked onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
