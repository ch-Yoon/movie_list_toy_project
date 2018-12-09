package com.list.movie.hyuck.movielist.contracts;

import com.list.movie.hyuck.movielist.items.MovieData;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataItemClickListener;

import java.util.ArrayList;

public interface MovieListAdapterContract {
    interface View {
        void refresh();
        void setOnMovieDataItemClickListener(OnMovieDataItemClickListener onMovieDataItemClickListener);
    }

    interface Model {
        void setMovieDataList(ArrayList<MovieData> movieDataArrayList);
        MovieData getMovieData(int position);
    }
}
