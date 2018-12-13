package com.list.movie.hyuck.movielist.movielist.adapter.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.helpers.glide.GlideLoader;
import com.list.movie.hyuck.movielist.helpers.glide.ImageLoader;

public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder {

    private OnMovieDataItemClickListener onMovieDataItemClickListener;
    private OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener;
    private ImageLoader imageLoader;

    private ImageView movieImageView;
    private TextView movieTitleTextView;
    private TextView moviePubDateTextView;
    private TextView movieDirectorTextView;
    private TextView movieActorTextView;
    private RatingBar userRatingBar;


    public MovieListAdapterViewHolder(View itemView, Activity activity) {
        super(itemView);

        init(itemView, activity);
    }

    private void init(View itemView, Activity activity) {
        imageLoader = new GlideLoader(activity);

        movieImageView = itemView.findViewById(R.id.movieImageView);
        movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
        moviePubDateTextView = itemView.findViewById(R.id.moviePubDateTextView);
        movieDirectorTextView = itemView.findViewById(R.id.movieDirectorTextView);
        movieActorTextView = itemView.findViewById(R.id.movieActorTextView);
        userRatingBar = itemView.findViewById(R.id.userRatingBar);

        ConstraintLayout movieDataItemRootView = itemView.findViewById(R.id.movieItemRootView);
        movieDataItemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlingMovieDataItemClick();
            }
        });
    }


    public void setOnMovieDataItemClickListener(OnMovieDataItemClickListener onMovieDataItemClickListener) {
        this.onMovieDataItemClickListener = onMovieDataItemClickListener;
    }

    public void setOnMovieDataDisplayPositionListener(OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener) {
        this.onMovieDataDisplayPositionListener = onMovieDataDisplayPositionListener;
    }

    public void setMovieData(MovieData movieData) {
        setMovieDataOfItemView(movieData);
        sendPositionToListener();
    }


    private void handlingMovieDataItemClick() {
        if(onMovieDataItemClickListener != null) {
            int position = getAdapterPosition();
            onMovieDataItemClickListener.onMovieDataItemClick(position);
        }
    }

    private void setMovieDataOfItemView(MovieData movieData) {
        if(movieData != null) {
            movieTitleTextView.setText(movieData.getApplyBoldBuilder());
            userRatingBar.setRating(movieData.getUserRating());
            moviePubDateTextView.setText(movieData.getPubDate());
            movieDirectorTextView.setText(movieData.getDirector());
            movieActorTextView.setText(movieData.getActor());
            imageLoader.imageLoad(movieData.getImage(), movieImageView);
        }
    }

    private void sendPositionToListener() {
        if(onMovieDataDisplayPositionListener != null) {
            onMovieDataDisplayPositionListener.onMovieDataDisplayPosition(getAdapterPosition());
        }
    }

}
