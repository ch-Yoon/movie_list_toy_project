package com.list.movie.hyuck.movielist.movielist.presenter;

import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterModel;
import com.list.movie.hyuck.movielist.movielist.view.MovieListView;

public interface MovieListPresenter {
    void setView(MovieListView view);
    void setAdapterModel(MovieListAdapterModel adapterModel);
    void requestInitialMovieData(String movieTitle);
    void requestPossibleAdditionalMovieData(int nowDisplayPosition);
    void requestHandlingOfItemClick(int position);
}
