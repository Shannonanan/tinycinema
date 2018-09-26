package co.za.tinycinema.features.GetReviews;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import co.za.tinycinema.data.local.MovieResultEntity;
import co.za.tinycinema.features.GetReviews.Domain.model.Result;
import co.za.tinycinema.features.GetReviews.Domain.usecase.GetReviews;

public class GetReviewsViewModelPresenter extends ViewModel {

    private GetReviews getReviewsUsecase;

    public GetReviewsViewModelPresenter(GetReviews getReviewsUsecase) {
        this.getReviewsUsecase = getReviewsUsecase;
    }

    public LiveData<List<Result>> getReviews(int movieId) {
        return getReviewsUsecase.executeGetReviewsUseCase(movieId);
    }
}
