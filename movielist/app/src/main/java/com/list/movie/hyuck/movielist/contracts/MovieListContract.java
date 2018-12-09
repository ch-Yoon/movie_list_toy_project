package com.list.movie.hyuck.movielist.contracts;

public interface MovieListContract {
    interface View {
        void refreshMovieList();
        void hideKeyboard();
        void moveToMovieWeb(String uri);
        void showErrorMessage(String errorMessage);
    }

    interface Presenter {
        void setView(MovieListContract.View view);
        void setAdapterModel(MovieListAdapterContract.Model adapterModel);
        void requestInitialMovieData(String movieTitle);
        void requestHandlingOfItemClick(int position);
    }
}
