package co.za.tinycinema.features.Library;

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

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.GetMoviesFromLibraryViewHolder> {

    List<Result> getMoviesCollection;
    private Context mContext;
    private LibraryAdapter.OnMoviePosterClicked onItemClickListener;
    boolean offlineStatus = false;

    //setup a listener for your posts in the recyclerview
    public interface OnMoviePosterClicked {
        void onMoviePosterClicked(Result result);
        void onDeleteButtonClicked(Result result);
    }

    public LibraryAdapter(Context context) {
        this.getMoviesCollection = Collections.emptyList();
        mContext = context;
    }

    @NonNull
    @Override
    public GetMoviesFromLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GetMoviesFromLibraryViewHolder viewHolder = new GetMoviesFromLibraryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GetMoviesFromLibraryViewHolder holder, int position) {
        final Result movieResult = this.getMoviesCollection.get(position);

            holder.btn_save.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);

        String movieCode = movieResult.getPosterPath();
        if(movieCode == null || movieCode.isEmpty()){
            Glide.with(mContext)
                    .load(R.drawable.photo_camera)
                    .into(holder.imageView);
        }
        else{
            String url = mContext.getString(R.string.image_base_url) + movieCode;
            Glide.with(mContext)
                    .load(url)
                    .into(holder.imageView);}


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onMoviePosterClicked(movieResult);
                }
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDeleteButtonClicked(movieResult);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.getMoviesCollection != null) ? this.getMoviesCollection.size() : 0;
    }

    static class GetMoviesFromLibraryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_movie)
        ImageView imageView;
        @BindView(R.id.btn_save)
        Button btn_save;
        @BindView(R.id.btn_delete) Button delete;

        public GetMoviesFromLibraryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<Result> INFO, boolean newtorkStatus) {
        this.validateCollection(INFO);
        this.getMoviesCollection = (List<Result>) INFO;
        this.notifyDataSetChanged();
        offlineStatus = newtorkStatus;
    }

    private void validateCollection(Collection<Result> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnItemClickListener (LibraryAdapter.OnMoviePosterClicked onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
