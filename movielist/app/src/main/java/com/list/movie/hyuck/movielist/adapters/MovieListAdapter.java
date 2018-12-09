package com.list.movie.hyuck.movielist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.adapters.viewholders.MovieListAdapterViewHolder;
import com.list.movie.hyuck.movielist.contracts.MovieListAdapterContract;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.items.MovieData;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapterViewHolder> implements MovieListAdapterContract.View, MovieListAdapterContract.Model {
    private OnMovieDataItemClickListener onMovieDataItemClickListener;
    private Context context;
    private ArrayList<MovieData> movieDataList;


    public MovieListAdapter(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        movieDataList = new ArrayList<>();
    }


    @NonNull
    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.movie_list_recycler_view_item, viewGroup, false);

        MovieListAdapterViewHolder movieListAdapterViewHolder = new MovieListAdapterViewHolder(itemView, context);
        movieListAdapterViewHolder.setOnMovieDataItemClickListener(onMovieDataItemClickListener);

        return movieListAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapterViewHolder holder, int position) {
        long start = System.currentTimeMillis();
        MovieData movieData = movieDataList.get(position);

        holder.setMovieData(movieData);
        long end = System.currentTimeMillis();
        Log.d("testtest",end-start+"");
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
    public void setMovieDataList(ArrayList<MovieData> movieDataArrayList) {
        this.movieDataList = movieDataArrayList;
    }

    @Override
    public MovieData getMovieData(int position) {
        return movieDataList.get(position);
    }
}
