package com.list.movie.hyuck.movielist.movielist.adapter;


import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;

import java.util.ArrayList;

public interface MovieListAdapterModel {

    void setMovieDataList(ArrayList<MovieData> movieDataArrayList);

    void addMovieDataList(ArrayList<MovieData> movieDataArrayList);

    ArrayList<MovieData> getMovieDataList();

    MovieData getMovieData(int position);

    int getCount();

    void clear();
}
