package com.list.movie.hyuck.movielist.movielist.presenter;

import android.os.Bundle;

import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterModel;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.view.MovieListView;

import java.util.ArrayList;

public interface MovieListPresenter {

    void attachView(MovieListView movieListView);

    void detachView();

    void setAdapterModel(MovieListAdapterModel adapterModel);

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void loadFirstMovieDataOfNowTitle(String movieTitle);

    void loadPossibleMoreMovieData(int nowDisplayPosition);

    void handlingOfItemClick(int position);

}
