package com.list.movie.hyuck.movielist.movielist.presenter.manager;

public class MovieRequest {
    private String movieTitle;
    private int loadIndex;
    private int dataSize;

    MovieRequest(String movieTitle, int loadIndex, int dataSize) {
        this.movieTitle = movieTitle;
        this.loadIndex = loadIndex;
        this.dataSize = dataSize;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getLoadIndex() {
        return loadIndex;
    }

    public int getDataSize() {
        return dataSize;
    }
}
