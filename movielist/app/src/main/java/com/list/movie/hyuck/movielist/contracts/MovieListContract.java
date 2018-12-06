package com.list.movie.hyuck.movielist.contracts;

public interface MovieListContract {
    interface View {
        void refreshView();
        void moveToMovieWeb(String uri);
    }

    interface Presenter {
    }
}
