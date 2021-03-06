package com.list.movie.hyuck.movielist.movielist.presenter.helpers;

import android.os.Bundle;

import com.list.movie.hyuck.movielist.movielist.presenter.helpers.items.MovieDataRequest;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.items.RequestLog;

public class DataLoadInspector {

    private static final String REQUEST_LOG_KEY = "REQUEST_LOG_KEY";

    private static final int REQUEST_DATA_SIZE = 20;
    private static final int ALLOW_PRELOAD_LENGTH = 7;
    private static final int MINIMUM_POSITION = REQUEST_DATA_SIZE - ALLOW_PRELOAD_LENGTH;

    private RequestLog movieDataRequestLog;


    public DataLoadInspector() {
        init();
    }

    private void init() {
        movieDataRequestLog = new RequestLog();
    }


    public void checkFirstMovieDataLoad(String movieTitle, OnMovieDataLoadApproveListener onMovieDataLoadApproveListener) {
        recordFirstMovieDataLoad(movieTitle);
        approveMovieDataLoad(onMovieDataLoadApproveListener);
    }

    public void checkMoreMovieDataLoad(int displayPosition, int nowDataCount, OnMovieDataLoadApproveListener onMovieDataLoadApproveListener) {
        if(isPossibleAdditionalDataLoad(displayPosition, nowDataCount)) {
            recordMoveMovieDataLoad(nowDataCount);
            approveMovieDataLoad(onMovieDataLoadApproveListener);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(REQUEST_LOG_KEY, movieDataRequestLog);
    }

    public void onRestoreSavedInstanceState(Bundle savedInstanceState) {
        RequestLog savedRequestLog = savedInstanceState.getParcelable(REQUEST_LOG_KEY);
        if(savedRequestLog != null) {
            movieDataRequestLog = savedRequestLog;
        }
    }


    private boolean isPossibleAdditionalDataLoad(int displayPosition, int nowDataSize) {
        if(isPreloadRange(displayPosition, nowDataSize)) {
            return isSameExpectedRequestDataSize(nowDataSize);
        }

        return false;
    }

    private boolean isPreloadRange(int displayPosition, int nowDataSize) {
        int preloadLimitPosition = nowDataSize - ALLOW_PRELOAD_LENGTH;

        return (preloadLimitPosition >= MINIMUM_POSITION) && (displayPosition >= preloadLimitPosition);
    }

    private boolean isSameExpectedRequestDataSize(int nowDataSize) {
        int expectedDataSize = movieDataRequestLog.getExpectedDataSizeAfterRequest();

        return nowDataSize == expectedDataSize;
    }

    private void recordFirstMovieDataLoad(String movieTitle) {
        movieDataRequestLog.setMovieTitle(movieTitle);
        movieDataRequestLog.setDataSizeOfPreviousRequest(0);
        movieDataRequestLog.setExpectedDataSizeAfterRequest(REQUEST_DATA_SIZE);
    }

    private void recordMoveMovieDataLoad(int nowDataSize) {
        movieDataRequestLog.setDataSizeOfPreviousRequest(nowDataSize);
        movieDataRequestLog.setExpectedDataSizeAfterRequest(nowDataSize + REQUEST_DATA_SIZE);
    }

    private void approveMovieDataLoad(OnMovieDataLoadApproveListener onMovieDataLoadApproveListener) {
        if(onMovieDataLoadApproveListener != null) {
            onMovieDataLoadApproveListener.onMovieDataLoadApprove(createMovieDataRequest());
        }
    }

    private MovieDataRequest createMovieDataRequest() {
        String movieTitle = movieDataRequestLog.getMovieTitle();
        int loadIndex = movieDataRequestLog.getDataSizeOfPreviousRequest() + 1;

        return new MovieDataRequest(movieTitle, loadIndex, REQUEST_DATA_SIZE);
    }

}
