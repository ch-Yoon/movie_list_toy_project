package com.list.movie.hyuck.movielist.movielist.presenter.manager;

public class RequestLog {
    private String movieTitle;
    private int dataSizeOfPreviousRequest;
    private int expectedDataSizeAfterRequest;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getDataSizeOfPreviousRequest() {
        return dataSizeOfPreviousRequest;
    }

    public void setDataSizeOfPreviousRequest(int dataSizeOfPreviousRequest) {
        this.dataSizeOfPreviousRequest = dataSizeOfPreviousRequest;
    }

    public int getExpectedDataSizeAfterRequest() {
        return expectedDataSizeAfterRequest;
    }

    public void setExpectedDataSizeAfterRequest(int expectedDataSizeAfterRequest) {
        this.expectedDataSizeAfterRequest = expectedDataSizeAfterRequest;
    }
}
