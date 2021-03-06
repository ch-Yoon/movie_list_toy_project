package com.list.movie.hyuck.movielist.movielist.presenter;

import android.content.Context;
import android.os.Bundle;

import com.list.movie.hyuck.movielist.movielist.adapter.MovieListAdapterModel;
import com.list.movie.hyuck.movielist.movielist.model.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.MovieListModel;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.DataLoadInspector;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.items.MovieDataRequest;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.OnMovieDataLoadApproveListener;
import com.list.movie.hyuck.movielist.movielist.view.MovieListView;

import java.util.ArrayList;

public class MovieListPresenterImpl implements MovieListPresenter {

    private static final String MOVIE_DATA_LIST_KEY = "MOVIE_DATA_LIST_KEY";

    private MovieListView movieListView;
    private MovieListAdapterModel adapterModel;
    private MovieListModel movieListModel;
    private DataLoadInspector dataLoadInspector;


    public MovieListPresenterImpl(Context context, MovieListView movieListView) {
        init(context, movieListView);
    }

    private void init(Context context, MovieListView movieListView) {
        movieListModel = new MovieListModel(context);
        dataLoadInspector = new DataLoadInspector();
        this.movieListView = movieListView;
    }


    @Override
    public void setAdapterModel(MovieListAdapterModel adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void onStop() {
        requestCancelAllToModel();
        hideProgressDialogOfView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        handlingSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        handlingRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void loadFirstMovieDataOfNowTitle(String movieTitle) {
        requestCancelAllToModel();
        hideKeyboardOfView();
        showProgressDialogOfView();
        clearDataListOfAdapterModel();
        checkFirstMovieDataLoad(movieTitle);
    }

    @Override
    public void loadPossibleMoreMovieData(int nowDisplayPosition) {
        checkMoreMovieDataLoad(nowDisplayPosition);
    }

    @Override
    public void handlingOfItemClick(int position) {
        MovieData movieData = adapterModel.getMovieData(position);
        String movieLink = movieData.getLink();

        movieListView.moveToMovieWeb(movieLink);
    }


    private void checkFirstMovieDataLoad(String movieTitle) {
        dataLoadInspector.checkFirstMovieDataLoad(movieTitle, new OnMovieDataLoadApproveListener() {
            @Override
            public void onMovieDataLoadApprove(MovieDataRequest movieDataRequest) {
                requestMovieDataLoadToModel(movieDataRequest);
            }
        });
    }

    private void checkMoreMovieDataLoad(int nowDisplayPosition) {
        if(adapterModel != null) {
            int nowDataTotalCount = adapterModel.getCount();
            dataLoadInspector.checkMoreMovieDataLoad(nowDisplayPosition, nowDataTotalCount, new OnMovieDataLoadApproveListener() {
                @Override
                public void onMovieDataLoadApprove(MovieDataRequest movieDataRequest) {
                    requestMovieDataLoadToModel(movieDataRequest);
                }
            });
        }
    }

    private void requestMovieDataLoadToModel(MovieDataRequest movieDataRequest) {
        final boolean isFirstRequest = movieDataRequest.isFirstRequestOfNowTitle();
        movieListModel.loadMovieData(movieDataRequest, new OnMovieDataLoadListener() {
            @Override
            public void onSuccess(ArrayList<MovieData> movieDataList) {
                saveLoadMovieDataList(movieDataList, isFirstRequest);
                hideProgressDialogOfView();
                refreshView();
            }

            @Override
            public void onApplicationError(String errorMessage) {
                sendApplicationErrorToView(errorMessage);
                hideProgressDialogOfView();
            }

            @Override
            public void onNetworkNotConnectingError() {
                sendNetworkNotConnectingErrorToView();
                hideProgressDialogOfView();
            }

            @Override
            public void onServerSystemError(String errorMessage) {
                sendServerSystemErrorToView(errorMessage);
                hideProgressDialogOfView();
            }

            @Override
            public void onNoMoreDataError() {
                sendNoMoreDataErrorToView();
                hideProgressDialogOfView();
            }

            @Override
            public void onNonExistentWordError() {
                sendNonExistentWordErrorToView();
                hideProgressDialogOfView();
            }
        });
    }

    private void handlingSaveInstanceState(Bundle outState) {
        saveInstanceStateOfAdapterModel(outState);
        dataLoadInspector.onSaveInstanceState(outState);
    }

    private void saveInstanceStateOfAdapterModel(Bundle outState) {
        ArrayList<MovieData> movieDataList = adapterModel.getMovieDataList();
        outState.putParcelableArrayList(MOVIE_DATA_LIST_KEY, movieDataList);
    }

    private void handlingRestoreInstanceState(Bundle savedInstanceState) {
        restoreInstanceStateOfAdapterModel(savedInstanceState);
        dataLoadInspector.onRestoreSavedInstanceState(savedInstanceState);
    }

    private void restoreInstanceStateOfAdapterModel(Bundle savedInstanceState) {
        ArrayList<MovieData> movieDataList = savedInstanceState.getParcelableArrayList(MOVIE_DATA_LIST_KEY);
        adapterModel.setMovieDataList(movieDataList);
    }

    private void saveLoadMovieDataList(ArrayList<MovieData> movieDataList, boolean isFirstRequest) {
        manufactureMovieDataList(movieDataList);
        saveMovieDataListToAdapterModel(movieDataList, isFirstRequest);
    }

    private void manufactureMovieDataList(ArrayList<MovieData> movieDataList) {
        convertMaximumValueOfUserRating(movieDataList);
        applyBoldToTitleOfMovieDataList(movieDataList);
    }

    private void convertMaximumValueOfUserRating(ArrayList<MovieData> movieDataList) {
        final float ratingMaximum = 5f;

        for (int i = 0; i < movieDataList.size(); i++) {
            movieDataList.get(i).ratingMaximumConversion(ratingMaximum);
        }
    }

    private void applyBoldToTitleOfMovieDataList(ArrayList<MovieData> movieDataList) {
        for (int i = 0; i < movieDataList.size(); i++) {
            movieDataList.get(i).movieTitleApplyToBold();
        }
    }

    private void sendApplicationErrorToView(String errorMessage) {
        movieListView.showApplicationError(errorMessage);
    }

    private void sendServerSystemErrorToView(String errorMessage) {
        movieListView.showServerSystemError(errorMessage);
    }

    private void sendNetworkNotConnectingErrorToView() {
        movieListView.showNetworkNotConnectingError();
    }

    private void sendNoMoreDataErrorToView() {
        movieListView.showNoMoreData();
    }

    private void sendNonExistentWordErrorToView() {
        movieListView.showNonExistentWordError();
    }

    private void saveMovieDataListToAdapterModel(ArrayList<MovieData> movieDataList, boolean isFirstRequest) {
        if (isFirstRequest) {
            adapterModel.setMovieDataList(movieDataList);
        } else {
            adapterModel.addMovieDataList(movieDataList);
        }
    }

    private void refreshView() {
        movieListView.refreshMovieList();
    }

    private void hideKeyboardOfView() {
        movieListView.hideKeyboard();
    }

    private void showProgressDialogOfView() {
        movieListView.showProgressDialog();
    }

    private void hideProgressDialogOfView() {
        movieListView.hideProgressDialog();
    }

    private void clearDataListOfAdapterModel() {
        adapterModel.clear();
    }

    private void requestCancelAllToModel() {
        movieListModel.requestCancelAll();
    }

}
