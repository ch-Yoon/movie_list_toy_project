package com.list.movie.hyuck.movielist.presenters;

import android.content.Context;
import android.content.res.Resources;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.contracts.MovieListAdapterContract;
import com.list.movie.hyuck.movielist.contracts.MovieListContract;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.items.MovieData;
import com.list.movie.hyuck.movielist.models.MovieListModel;

import java.util.ArrayList;

public class MovieListPresenter implements MovieListContract.Presenter {
    private MovieListContract.View movieListView;
    private MovieListAdapterContract.Model adapterModel;
    private MovieListModel movieListModel;

    private Resources resources;

    public MovieListPresenter(Context context) {
        init(context);
    }

    private void init(Context context) {
        movieListModel = new MovieListModel(context);
        resources = context.getResources();
    }

    @Override
    public void setView(MovieListContract.View view) {
        this.movieListView = view;
    }

    @Override
    public void setAdapterModel(MovieListAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void loadMovieData(String movieTitle) {
        processingLoadMovieData(movieTitle);
    }

    @Override
    public void movieDataClick(int position) {
        processingMovieDataClick(position);
    }


    private void processingLoadMovieData(String movieTitle) {
        movieListModel.loadMovieData(movieTitle, 1, new OnMovieDataLoadListener() {
            @Override
            public void onSuccess(ArrayList<MovieData> movieDataArrayList) {
                processingLoadMovieData(movieDataArrayList);
            }

            @Override
            public void onApplicationError(String errorMessage) {
                processingOnApplicationError(errorMessage);
            }

            @Override
            public void onNetworkNotConnectingError() {
                processingOnNetworkNotConnectingError();
            }

            @Override
            public void onServerSystemError(String errorMessage) {
                processingOnServerSystemError(errorMessage);
            }

            @Override
            public void onNoMoreData() {
                processingOnNoMoreData();
            }

            @Override
            public void onNonExistentWordError() {
                processingOnNonExistentWordError();
            }
        });
    }


    private void processingLoadMovieData(ArrayList<MovieData> movieDataList) {
        manufactureUserRatingOfMovieDataList(movieDataList);

        adapterModel.setMovieDataList(movieDataList);
        movieListView.refreshMovieList();
    }

    private void processingMovieDataClick(int position) {
        MovieData movieData = adapterModel.getMovieData(position);
        String movieLink = movieData.getLink();

        movieListView.moveToMovieWeb(movieLink);
    }

    private void manufactureUserRatingOfMovieDataList(ArrayList<MovieData> movieDataList) {
        final float startCountOfRatingBar = 5f;
        for(int i=0; i<movieDataList.size(); i++) {
            movieDataList.get(i).manufactureUserRating(startCountOfRatingBar);
        }
    }

    private void processingOnApplicationError(String errorMessage) {
        String showMessage = String.format(resources.getString(R.string.movie_data_load_application_error), errorMessage);

        movieListView.showErrorMessage(showMessage);
    }

    private void processingOnServerSystemError(String errorMessage) {
        String showMessage = String.format(resources.getString(R.string.movie_data_load_system_error), errorMessage);

        movieListView.showErrorMessage(showMessage);
    }

    private void processingOnNetworkNotConnectingError() {
        String showMessage = resources.getString(R.string.movie_data_network_not_connecting_error);

        movieListView.showErrorMessage(showMessage);
    }

    private void processingOnNoMoreData() {
        String showMessage = resources.getString(R.string.movie_data_no_more_data_error);

        movieListView.showErrorMessage(showMessage);
    }

    private void processingOnNonExistentWordError() {
        String showMessage = resources.getString(R.string.movie_data_non_existent_word_error);

        movieListView.showErrorMessage(showMessage);
    }
}
