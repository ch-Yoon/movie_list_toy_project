package com.list.movie.hyuck.movielist.listeners;

import com.list.movie.hyuck.movielist.items.MovieData;

import java.util.ArrayList;

public interface OnMovieDataLoadListener {
    void onSuccess(ArrayList<MovieData> movieDataList);
    void onApplicationError(String errorMessage);
    void onNetworkNotConnectingError();
    void onServerSystemError(String errorMessage);
    void onNoMoreData();
    void onNonExistentWordError();
}
