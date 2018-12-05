package com.list.movie.hyuck.movielist.presenters;

import android.support.annotation.NonNull;

import com.list.movie.hyuck.movielist.contracts.MovieListContract;
import com.list.movie.hyuck.movielist.models.MovieListModel;

public class MovieListPresenter implements MovieListContract.Presenter {
    private MovieListContract.View movieListView;
    private MovieListModel movieListModel;

    public MovieListPresenter(@NonNull MovieListContract.View movieListView) {
        this.movieListView = movieListView;
        movieListModel = new MovieListModel();
    }
}
