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
    public void requestInitialMovieData(String movieTitle) {
        handlingRequestInitialMovieData(movieTitle);
    }

    @Override
    public void requestHandlingOfItemClick(int position) {
        handlingMovieDataClick(position);
    }


    private void requestInitialMovieDataToModel(String movieTitle) {
        movieListModel.loadMovieData(movieTitle, 1, new OnMovieDataLoadListener() {
            @Override
            public void onSuccess(ArrayList<MovieData> movieDataArrayList) {
                handlingLoadMovieData(movieDataArrayList);
            }

            @Override
            public void onApplicationError(String errorMessage) {
                handlingApplicationError(errorMessage);
            }

            @Override
            public void onNetworkNotConnectingError() {
                handlingNetworkNotConnectingError();
            }

            @Override
            public void onServerSystemError(String errorMessage) {
                handlingServerSystemError(errorMessage);
            }

            @Override
            public void onNoMoreData() {
                handlingNoMoreData();
            }

            @Override
            public void onNonExistentWordError() {
                handlingNonExistentWordError();
            }
        });
    }

    private void handlingRequestInitialMovieData(String movieTitle) {
        movieListView.hideKeyboard();

        requestInitialMovieDataToModel(movieTitle);
    }

    private void handlingLoadMovieData(ArrayList<MovieData> movieDataList) {
        manufactureUserRatingOfMovieDataList(movieDataList);

        adapterModel.setMovieDataList(movieDataList);
        movieListView.refreshMovieList();
    }

    private void handlingMovieDataClick(int position) {
        MovieData movieData = adapterModel.getMovieData(position);
        String movieLink = movieData.getLink();

        movieListView.moveToMovieWeb(movieLink);
    }

    private void handlingApplicationError(String errorMessage) {
        String showMessage = String.format(resources.getString(R.string.movie_data_load_application_error), errorMessage);

        movieListView.showErrorMessage(showMessage);
    }

    private void handlingServerSystemError(String errorMessage) {
        String showMessage = String.format(resources.getString(R.string.movie_data_load_system_error), errorMessage);

        movieListView.showErrorMessage(showMessage);
    }

    private void handlingNetworkNotConnectingError() {
        String showMessage = resources.getString(R.string.movie_data_network_not_connecting_error);

        movieListView.showErrorMessage(showMessage);
    }

    private void handlingNoMoreData() {
        String showMessage = resources.getString(R.string.movie_data_no_more_data_error);

        movieListView.showErrorMessage(showMessage);
    }

    private void handlingNonExistentWordError() {
        String showMessage = resources.getString(R.string.movie_data_non_existent_word_error);

        movieListView.showErrorMessage(showMessage);
    }

    private void manufactureUserRatingOfMovieDataList(ArrayList<MovieData> movieDataList) {
        final float startCountOfRatingBar = 5f;
        for(int i=0; i<movieDataList.size(); i++) {
            movieDataList.get(i).manufactureUserRating(startCountOfRatingBar);
        }
    }
}
