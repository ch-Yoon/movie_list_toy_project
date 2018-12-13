package com.list.movie.hyuck.movielist.movielist.view;

public interface MovieListView {

    void refreshMovieList();

    void moveToMovieWeb(String uri);

    void showApplicationError(String errorMessage);

    void showNetworkNotConnectingError();

    void showServerSystemError(String errorMessage);

    void showNoMoreData();

    void showNonExistentWordError();

    void hideKeyboard();

    void showProgressDialog();

    void hideProgressDialog();

    void moveScrollToTop();

}
