package com.list.movie.hyuck.movielist.models;

import android.content.Context;

import com.list.movie.hyuck.movielist.listeners.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.loaders.MovieDataLoader;

public class MovieListModel {
    private MovieDataLoader movieDataLoader;


    public MovieListModel(Context context) {
        init(context);
    }

    private void init(Context context) {
        movieDataLoader = new MovieDataLoader(context);
    }


    public void loadMovieData(String movieTitle, int startIndex, OnMovieDataLoadListener onMovieDataLoadListener) {
        handlingLoadMovieData(movieTitle, startIndex, onMovieDataLoadListener);
    }


    private void handlingLoadMovieData(String movieTitle, int startIndex, OnMovieDataLoadListener onMovieDataLoadListener) {
        movieDataLoader.loadMovieData(movieTitle, startIndex, onMovieDataLoadListener);
    }
}
