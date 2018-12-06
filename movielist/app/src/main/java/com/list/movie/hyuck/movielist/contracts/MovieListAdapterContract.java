package com.list.movie.hyuck.movielist.contracts;

import com.list.movie.hyuck.movielist.items.MovieData;

import java.util.ArrayList;

public interface MovieListAdapterContract {
    interface View {
        void refresh();
    }

    interface Model {
        void setMovieDataList(ArrayList<MovieData> movieDataArrayList);
        MovieData getMovieData(int position);
    }
}
