package com.list.movie.hyuck.movielist.movielist.view;

public interface MovieListView {
    void refreshMovieList();
    void hideKeyboard();
    void moveToMovieWeb(String uri);
    void showApplicationError(String errorMessage);
    void showNetworkNotConnectingError();
    void showServerSystemError(String errorMessage);
    void showNoMoreData();
    void showNonExistentWordError();
    void showProgressDialog();
    void hideProgressDialog();

}
