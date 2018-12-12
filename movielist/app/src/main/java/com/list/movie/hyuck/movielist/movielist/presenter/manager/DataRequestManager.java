package com.list.movie.hyuck.movielist.movielist.presenter.manager;

import android.os.Bundle;

public class DataRequestManager {
    private static final String REQUEST_LOG_KEY = "REQUEST_LOG_KEY";

    private static final int REQUEST_DATA_SIZE = 20;
    private static final int ALLOW_PRELOAD_LENGTH = 5;
    private static final int MINIMUM_POSITION = REQUEST_DATA_SIZE - ALLOW_PRELOAD_LENGTH;

    private RequestLog requestLog;

    public DataRequestManager() {
        init();
    }

    private void init() {
        requestLog = new RequestLog();
    }

    public void confirmInitialDataLoad(String movieTitle, OnDataLoadConfirmListener onDataLoadConfirmListener) {
        recordInitialRequest(movieTitle);
        requestConfirm(onDataLoadConfirmListener);
    }

    public void confirmAdditionalDataLoad(int displayPosition, int nowDataSize, OnDataLoadConfirmListener onDataLoadConfirmListener) {
        if(isPreloadRange(displayPosition, nowDataSize)) {
            synchronized (this) {
                if (isPossibleAdditionalDataLoad(nowDataSize)) {
                    recordAdditionalRequest(nowDataSize);
                    requestConfirm(onDataLoadConfirmListener);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(REQUEST_LOG_KEY, requestLog);
    }

    public void onRestoreSavedInstanceState(Bundle savedInstanceState) {
        RequestLog requestLog = savedInstanceState.getParcelable(REQUEST_LOG_KEY);
        this.requestLog = requestLog;
    }

    private boolean isPreloadRange(int displayPosition, int nowDataSize) {
        int preloadLimitPosition = nowDataSize - ALLOW_PRELOAD_LENGTH;

        return (preloadLimitPosition >= MINIMUM_POSITION) && (displayPosition >= preloadLimitPosition);
    }

    private boolean isPossibleAdditionalDataLoad(int nowDataSize) {
        int expectedDataSize = requestLog.getExpectedDataSizeAfterRequest();

        return nowDataSize == expectedDataSize;
    }

    private void recordInitialRequest(String movieTitle) {
        requestLog.setMovieTitle(movieTitle);
        requestLog.setDataSizeOfPreviousRequest(0);
        requestLog.setExpectedDataSizeAfterRequest(REQUEST_DATA_SIZE);
    }

    private void recordAdditionalRequest(int nowDataSize) {
        requestLog.setDataSizeOfPreviousRequest(nowDataSize);
        requestLog.setExpectedDataSizeAfterRequest(nowDataSize + REQUEST_DATA_SIZE);
    }

    private void requestConfirm(OnDataLoadConfirmListener onDataLoadConfirmListener) {
        if(onDataLoadConfirmListener != null) {
            onDataLoadConfirmListener.onConfirm(createMovieRequest());
        }
    }

    private MovieRequest createMovieRequest() {
        String movieTitle = requestLog.getMovieTitle();
        int loadIndex = requestLog.getDataSizeOfPreviousRequest() + 1;

        return new MovieRequest(movieTitle, loadIndex, REQUEST_DATA_SIZE);
    }
}
