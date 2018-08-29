package co.za.tinycinema.features.common.mvcViews;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresContract;
import co.za.tinycinema.features.GetMoviesInTheatres.MoviesInTheatresImpl;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedContract;
import co.za.tinycinema.features.GetTopRatedMovies.TopRatedMoviesViewImpl;
import co.za.tinycinema.features.Library.LibraryContract;
import co.za.tinycinema.features.Library.LibraryViewImpl;
import co.za.tinycinema.features.ShowDetails.ShowDetailsContract;
import co.za.tinycinema.features.ShowDetails.ShowDetailsViewImpl;
import co.za.tinycinema.features.common.ImageLoader;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader;

    @Inject
    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container) {

        ViewMvc viewMvc;

        if (mvcViewClass == MoviesInTheatresContract.class) {
            viewMvc = new MoviesInTheatresImpl(mLayoutInflater, container);
        }
        else if(mvcViewClass == ShowDetailsContract.class) {
            viewMvc = new ShowDetailsViewImpl(mLayoutInflater, container);
        }
        else if(mvcViewClass == TopRatedContract.class){
            viewMvc = new TopRatedMoviesViewImpl(mLayoutInflater, container);
        }
        else if(mvcViewClass == LibraryContract.class){
            viewMvc = new LibraryViewImpl(mLayoutInflater, container);
        }
        else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcViewClass);
        }

        //noinspection unchecked
        return (T) viewMvc;
    }

}
