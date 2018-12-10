package com.list.movie.hyuck.movielist.movielist.view;

public interface MovieListView {
    void refreshMovieList();
    void hideKeyboard();
    void moveToMovieWeb(String uri);
    void showErrorMessage(String errorMessage);
}
