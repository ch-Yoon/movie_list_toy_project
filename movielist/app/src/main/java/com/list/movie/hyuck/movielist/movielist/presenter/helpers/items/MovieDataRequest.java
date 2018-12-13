package com.list.movie.hyuck.movielist.movielist.presenter.helpers.items;

public class MovieDataRequest {

    private String movieTitle;
    private int loadIndex;
    private int requestDataSize;


    public MovieDataRequest(String movieTitle, int loadIndex, int requestDataSize) {
        this.movieTitle = movieTitle;
        this.loadIndex = loadIndex;
        this.requestDataSize = requestDataSize;
    }


    public String getMovieTitle() {
        return movieTitle;
    }

    public int getLoadIndex() {
        return loadIndex;
    }

    public int getRequestDataSize() {
        return requestDataSize;
    }

    public boolean isFirstRequestOfNowTitle() {
        return loadIndex == 1;
    }
}
