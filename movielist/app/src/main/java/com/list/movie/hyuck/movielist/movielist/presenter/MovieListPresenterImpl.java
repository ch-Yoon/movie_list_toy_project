package com.list.movie.hyuck.movielist.movielist.presenter;

import android.content.Context;

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

    public MovieListPresenterImpl(Context applicationContext) {
        init(applicationContext);
    }

    private void init(Context applicationContext) {
        movieListModel = new MovieListModel(applicationContext);
        dataRequestManager = new DataRequestManager();
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
        hideKeyboardOfView();
        showProgressDialogOfView();
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
        manufactureMovieDataList(movieDataList);
        pushMovieDataListToAdapterModel(movieDataList, loadIndex);
        hideProgressDialogOfView();
        viewRefresh();
    }

    private void handlingMovieDataClick(int position) {
        MovieData movieData = adapterModel.getMovieData(position);
        String movieLink = movieData.getLink();

        movieListView.moveToMovieWeb(movieLink);
    }

    private void handlingApplicationError(String errorMessage) {
        movieListView.showApplicationError(errorMessage);
        hideProgressDialogOfView();
    }

    private void handlingServerSystemError(String errorMessage) {
        movieListView.showServerSystemError(errorMessage);
        hideProgressDialogOfView();
    }

    private void handlingNetworkNotConnectingError() {
        movieListView.showNetworkNotConnectingError();
        hideProgressDialogOfView();
    }

    private void handlingNoMoreData() {
        movieListView.showNoMoreData();
        hideProgressDialogOfView();
    }

    private void handlingNonExistentWordError() {
        movieListView.showNonExistentWordError();
        hideProgressDialogOfView();
    }

    private void manufactureMovieDataList(ArrayList<MovieData> movieDataList) {
        ratingMaximumConversion(movieDataList);
        applyBoldToTitleOfMovieDataList(movieDataList);
    }

    private void ratingMaximumConversion(ArrayList<MovieData> movieDataList) {
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

    private void pushMovieDataListToAdapterModel(ArrayList<MovieData> movieDataList, int loadIndex) {
        if (loadIndex == 1) {
            adapterModel.setMovieDataList(movieDataList);
        } else {
            adapterModel.addMovieDataList(movieDataList);
        }
    }

    private void viewRefresh() {
        movieListView.refreshMovieList();
    }

    private void hideKeyboardOfView() {
        movieListView.hideKeyboard();
    }

    private void hideProgressDialogOfView() {
        movieListView.hideProgressDialog();
    }

    private void showProgressDialogOfView() {
        movieListView.showProgressDialog();
    }
}
