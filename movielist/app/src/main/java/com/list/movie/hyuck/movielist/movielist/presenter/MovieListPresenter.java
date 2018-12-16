package com.list.movie.hyuck.movielist.movielist.presenter;

import android.os.Bundle;

import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterModel;
import com.list.movie.hyuck.movielist.movielist.view.MovieListView;

public interface MovieListPresenter {

    void setAdapterModel(MovieListAdapterModel adapterModel);

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void loadFirstMovieDataOfNowTitle(String movieTitle);

    void loadPossibleMoreMovieData(int nowDisplayPosition);

    void handlingOfItemClick(int position);

}
