package co.za.tinycinema.features.GetReviews;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import co.za.tinycinema.features.GetReviews.Domain.usecase.GetReviews;

public class GetReviewsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private GetReviews getReviewsUsecase;

    public GetReviewsViewModelFactory(GetReviews getReviewsUsecase) {
        this.getReviewsUsecase = getReviewsUsecase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetReviewsViewModelPresenter(getReviewsUsecase);
    }
}
