package com.list.movie.hyuck.movielist.contracts;

public interface MovieListContract {
    interface View {
        void refreshMovieList();
        void moveToMovieWeb(String uri);
    }

    interface Presenter {
        void setView(MovieListContract.View view);
        void setAdapterModel(MovieListAdapterContract.Model adapterModel);
        void loadMovieData(String movieTitle);
        void movieDataClick(int position);
    }
}
