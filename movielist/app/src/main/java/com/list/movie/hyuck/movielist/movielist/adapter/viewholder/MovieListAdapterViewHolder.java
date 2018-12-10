package com.list.movie.hyuck.movielist.movielist.adapter.viewholder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.glide.GlideLoader;
import com.list.movie.hyuck.movielist.glide.ImageLoader;

public class MovieListAdapterViewHolder extends RecyclerView.ViewHolder {
    private OnMovieDataItemClickListener onMovieDataItemClickListener;
    private OnMovieDataDisplayPositionListener onMovieDataDisplayPositionListener;
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

        movieImageView = itemView.findViewById(R.id.movieImageView);
        movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
        userRatingBar = itemView.findViewById(R.id.userRatingBar);
        moviePubDateTextView = itemView.findViewById(R.id.moviePubDateTextView);
        movieDirectorTextView = itemView.findViewById(R.id.movieDirectorTextView);
        movieActorTextView = itemView.findViewById(R.id.movieActorTextView);

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
        handlingMovieDataToView(movieData);
        notifyPositionToListener();
    }

    private void handlingMovieDataItemClick() {
        if(onMovieDataItemClickListener != null) {
            int position = getAdapterPosition();

            onMovieDataItemClickListener.onMovieDataItemClick(position);
        }
    }

    private void handlingMovieDataToView(MovieData movieData) {
        if(movieData != null) {
            applyBoldToMovieTitle(movieData.getTitle());
            userRatingBar.setRating(movieData.getUserRating());
            moviePubDateTextView.setText(movieData.getPubDate());
            movieDirectorTextView.setText(movieData.getDirector());
            movieActorTextView.setText(movieData.getActor());
            imageLoader.imageLoad(context, movieData.getImage(), movieImageView);
        }
    }

    private void notifyPositionToListener() {
        if(onMovieDataDisplayPositionListener != null) {
            onMovieDataDisplayPositionListener.onMovieDataDisplayPosition(getAdapterPosition());
        }
    }

    private void applyBoldToMovieTitle(String movieTitle) {
        final String boldHeadText = "<b>";
        final String boldTailText = "</b>";

        if(movieTitle.contains(boldHeadText) && movieTitle.contains(boldTailText)) {
            int boldHeadStartIndex = movieTitle.indexOf(boldHeadText);
            int boldHeadEndIndex = boldHeadStartIndex + boldHeadText.length();
            int boldTailStartIndex = movieTitle.indexOf(boldTailText);
            int boldTailEndIndex = boldTailStartIndex + boldTailText.length();

            String boldTextFront = movieTitle.substring(0, boldHeadStartIndex);
            String boldText = movieTitle.substring(boldHeadEndIndex, boldTailStartIndex);
            String boldTextEnd = movieTitle.substring(boldTailEndIndex);

            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            stringBuilder.append(boldTextFront);
            stringBuilder.append(boldText);
            stringBuilder.append(boldTextEnd);
            stringBuilder.setSpan(new StyleSpan(Typeface.BOLD), boldTextFront.length(), boldTextFront.length() + boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            movieTitleTextView.setText(stringBuilder);
        } else {
            movieTitleTextView.setText(movieTitle);
        }
    }
}
