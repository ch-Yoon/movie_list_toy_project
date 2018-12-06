package com.list.movie.hyuck.movielist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.list.movie.hyuck.movielist.adapters.viewholders.MovieListAdapterViewHolder;
import com.list.movie.hyuck.movielist.contracts.MovieListAdapterContract;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapterViewHolder> implements MovieListAdapterContract.View {
    private Context context;

    public MovieListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapterViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    @Override
    public void refresh() {

    }
}
