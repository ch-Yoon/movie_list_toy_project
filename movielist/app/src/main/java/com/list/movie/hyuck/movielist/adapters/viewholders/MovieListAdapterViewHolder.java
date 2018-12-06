package com.list.movie.hyuck.movielist.adapters.viewholders;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataItemClickListener;
import com.list.movie.hyuck.movielist.items.MovieData;
import com.list.movie.hyuck.movielist.loaders.GlideLoader;
import com.list.movie.hyuck.movielist.loaders.ImageLoader;

public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder {
    private OnMovieDataItemClickListener onMovieDataItemClickListener;
    private ImageLoader imageLoader;

    private Context context;
    private ImageView movieImageView;
    private TextView movieTitleTextView;
    private RatingBar userRatingBar;
    private TextView moviePubDateTextView;
    private TextView movieDirectorTextView;
    private TextView movieActorTextView;


    public MovieListAdapterViewHolder(View itemView, Context context) {
        super(itemView);

        init(itemView, context);
    }

    private void init(View itemView, Context context) {
        this.context = context;
        imageLoader = new GlideLoader();

        initViews(itemView);
    }

    private void initViews(View itemView) {
        ConstraintLayout movieDataItemRootView = itemView.findViewById(R.id.movieItemRootView);
        movieDataItemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processingMovieDataItemClick();
            }
        });

        movieImageView = itemView.findViewById(R.id.movieImageView);
        movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
        userRatingBar = itemView.findViewById(R.id.userRatingBar);
        moviePubDateTextView = itemView.findViewById(R.id.moviePubDateTextView);
        movieDirectorTextView = itemView.findViewById(R.id.movieDirectorTextView);
        movieActorTextView = itemView.findViewById(R.id.movieActorTextView);
    }


    public void setOnMovieDataItemClickListener(OnMovieDataItemClickListener onMovieDataItemClickListener) {
        this.onMovieDataItemClickListener = onMovieDataItemClickListener;
    }

    public void setMovieData(MovieData movieData) {
        processingMovieDataToView(movieData);
    }


    private void processingMovieDataItemClick() {
        if(onMovieDataItemClickListener != null) {
            int position = getAdapterPosition();

            onMovieDataItemClickListener.onMovieDataItemClick(position);
        }
    }

    private void processingMovieDataToView(MovieData movieData) {
        if(movieData != null) {
            movieTitleTextView.setText(movieData.getTitle());
            userRatingBar.setRating(movieData.getUserRating());
            moviePubDateTextView.setText(movieData.getPubDate());
            movieDirectorTextView.setText(movieData.getDirector());
            movieActorTextView.setText(movieData.getActor());

            imageLoader.imageLoad(context, movieData.getImage(), movieImageView);
        }
    }
}
