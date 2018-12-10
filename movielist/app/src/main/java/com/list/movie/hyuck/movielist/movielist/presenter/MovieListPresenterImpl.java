package com.list.movie.hyuck.movielist.movielist.presenter;

import android.content.Context;
import android.content.res.Resources;

import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterModel;
import com.list.movie.hyuck.movielist.movielist.model.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.MovieListModel;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.DataRequestManager;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.MovieRequest;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.OnDataLoadConfirmListener;
import com.list.movie.hyuck.movielist.movielist.view.MovieListView;

import java.util.ArrayList;

public class MovieListPresenterImpl implements MovieListPresenter {
    private MovieListView movieListView;
    private MovieListAdapterModel adapterModel;
    private MovieListModel movieListModel;
    private DataRequestManager dataRequestManager;
    private Resources resources;

    public MovieListPresenterImpl(Context context) {
        init(context);
    }

    private void init(Context context) {
        movieListModel = new MovieListModel(context);
        dataRequestManager = new DataRequestManager();
        resources = context.getResources();
    }

    @Override
    public void setView(MovieListView view) {
        this.movieListView = view;
    }

    @Override
    public void setAdapterModel(MovieListAdapterModel adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void requestInitialMovieData(String movieTitle) {
        confirmRequestToRequestManager(movieTitle);
    }

    @Override
    public void requestPossibleAdditionalMovieData(int nowDisplayPosition) {
        confirmRequestToRequestManager(nowDisplayPosition);
    }

    @Override
    public void requestHandlingOfItemClick(int position) {
        handlingMovieDataClick(position);
    }

    private void confirmRequestToRequestManager(String movieTitle) {
        dataRequestManager.confirmInitialDataLoad(movieTitle, new OnDataLoadConfirmListener() {
            @Override
            public void onConfirm(MovieRequest movieRequest) {
                requestMovieDataToModel(movieRequest);
            }
        });

        movieListView.hideKeyboard();
        adapterModel.clear();
    }

    private void confirmRequestToRequestManager(int nowDisplayPosition) {
        dataRequestManager.confirmAdditionalDataLoad(nowDisplayPosition, adapterModel.getCount(), new OnDataLoadConfirmListener() {
            @Override
            public void onConfirm(MovieRequest movieRequest) {
                requestMovieDataToModel(movieRequest);
            }
        });
    }

    private void requestMovieDataToModel(final MovieRequest movieRequest) {
        movieListModel.loadMovieData(movieRequest, new OnMovieDataLoadListener() {
            @Override
            public void onSuccess(ArrayList<MovieData> movieDataList) {
                handlingLoadMovieData(movieDataList, movieRequest.getLoadIndex());
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

    private void handlingLoadMovieData(ArrayList<MovieData> movieDataList, int loadIndex) {
        manufactureUserRatingOfMovieDataList(movieDataList);

        if(loadIndex == 1) {
            adapterModel.setMovieDataList(movieDataList);
        } else {
            adapterModel.addMovieDataList(movieDataList);
        }

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
