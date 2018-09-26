package co.za.tinycinema.features.GetReviews;

import java.util.List;

import co.za.tinycinema.features.GetReviews.Domain.model.Result;
import co.za.tinycinema.features.common.mvcViews.ObservableViewMvc;

public interface GetReviewsContract extends ObservableViewMvc<GetReviewsContract.Listener> {

    interface Listener{
    }

    void renderInView(List<Result> reviewEntities);
}
