//package co.za.tinycinema.features.Library.domain.usecase;
//
//import java.util.List;
//
//import co.za.tinycinema.common.UseCase;
//import co.za.tinycinema.data.DataSource;
//import co.za.tinycinema.data.Repository;
//import co.za.tinycinema.features.GetMoviesInTheatres.domain.model.Result;
//
//public class GetMoviesFromLibrary extends UseCase<GetMoviesFromLibrary.RequestValues, GetMoviesFromLibrary.ResponseValues>  {
//
//    private Repository repository;
//
//    public GetMoviesFromLibrary(Repository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    protected void executeUseCase(RequestValues requestValues) {
//        this.repository.getMoviesFromLibrary(new DataSource.LoadInfoCallback() {
//            @Override
//            public void onDataLoaded(List<Result> results, boolean offline) {
//                getUseCaseCallback().onSuccess(new ResponseValues(results, offline));
//            }
//
//            @Override
//            public void onDataNotAvailable(String noDataAvailable) {
//                getUseCaseCallback().onError(noDataAvailable);
//            }
//        });
//    }
//
//    public static final class RequestValues implements UseCase.RequestValues{
//
//    }
//
//    public static final class ResponseValues implements UseCase.ResponseValue{
//        private List<Result> mResults;
//        private boolean offline;
//
//        public ResponseValues(List<Result> mResults, boolean offline) {
//            this.mResults = mResults;
//            this.offline = offline;
//        }
//
//        public List<Result> getInfo() {
//            return mResults;
//        }
//
//        public boolean getNetWorkStatus() {
//            return offline;}
//
//    }
//
//}
