package com.list.movie.hyuck.movielist.listeners;

import com.list.movie.hyuck.movielist.items.MovieData;

import java.util.ArrayList;

public interface OnMovieDataLoadListener {
    void onSuccess(ArrayList<MovieData> movieDataList);
    void onNetworkNotConnectingError();
    void onServerSystemError();
    void onNonExistentWordError();

}
