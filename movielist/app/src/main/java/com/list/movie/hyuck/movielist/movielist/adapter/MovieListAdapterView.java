package com.list.movie.hyuck.movielist.movielist.adapter;

import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataDisplayPositionListener;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataItemClickListener;

public interface MovieListAdapterView {
    void refresh();
    void setOnMovieDataItemClickListener(OnMovieDataItemClickListener onMovieDataItemClickListener);
    void setOnMovieDataDisplayPositionListener(OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener);
}
