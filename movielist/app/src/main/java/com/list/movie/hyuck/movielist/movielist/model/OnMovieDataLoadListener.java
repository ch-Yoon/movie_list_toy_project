package com.list.movie.hyuck.movielist.movielist.model;

import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;

import java.util.ArrayList;

public interface OnMovieDataLoadListener {
    void onSuccess(ArrayList<MovieData> movieDataList);
    void onApplicationError(String errorMessage);
    void onNetworkNotConnectingError();
    void onServerSystemError(String errorMessage);
    void onNoMoreDataError();
    void onNonExistentWordError();
}
