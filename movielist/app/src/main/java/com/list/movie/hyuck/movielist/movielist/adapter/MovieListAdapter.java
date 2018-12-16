package com.list.movie.hyuck.movielist.movielist.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.MovieListAdapterViewHolder;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataDisplayPositionListener;
import com.list.movie.hyuck.movielist.movielist.adapter.viewholder.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapterViewHolder> implements MovieListAdapterView, MovieListAdapterModel {

    private OnMovieDataItemClickListener onMovieDataItemClickListener;
    private OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener;

    private Activity activity;
    private ArrayList<MovieData> movieDataList;


    public MovieListAdapter(Activity activity) {
        init(activity);
    }

    private void init(Activity activity) {
        this.activity = activity;
        movieDataList = new ArrayList<>();
    }


    @NonNull
    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View itemView = layoutInflater.inflate(R.layout.movie_list_recycler_view_item, viewGroup, false);

        MovieListAdapterViewHolder movieListAdapterViewHolder = new MovieListAdapterViewHolder(itemView, activity);
        movieListAdapterViewHolder.setOnMovieDataItemClickListener(onMovieDataItemClickListener);
        movieListAdapterViewHolder.setOnMovieDataDisplayPositionListener(onMovieDataDisplayPositionListener);

        return movieListAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapterViewHolder holder, int position) {
        MovieData movieData = movieDataList.get(position);
        holder.setMovieData(movieData);
    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }


    @Override
    public void refresh() {
        notifyDataSetChanged();
    }


    @Override
    public void setOnMovieDataItemClickListener(OnMovieDataItemClickListener onMovieDataItemClickListener) {
        this.onMovieDataItemClickListener = onMovieDataItemClickListener;
    }

    @Override
    public void setOnMovieDataDisplayPositionListener(OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener) {
        this.onMovieDataDisplayPositionListener = onMovieDataDisplayPositionListener;
    }

    @Override
    public void setMovieDataList(ArrayList<MovieData> movieDataArrayList) {
        this.movieDataList = movieDataArrayList;
    }

    @Override
    public void addMovieDataList(ArrayList<MovieData> movieDataArrayList) {
        this.movieDataList.addAll(movieDataArrayList);
    }

    @Override
    public ArrayList<MovieData> getMovieDataList() {
        return movieDataList;
    }

    @Override
    public MovieData getMovieData(int position) {
        return movieDataList.get(position);
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public void clear() {
        movieDataList.clear();
        refresh();
    }

}
