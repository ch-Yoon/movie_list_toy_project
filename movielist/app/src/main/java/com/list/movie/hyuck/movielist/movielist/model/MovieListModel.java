package com.list.movie.hyuck.movielist.movielist.model;

import android.content.Context;

import com.list.movie.hyuck.movielist.movielist.model.helpers.RemoteDataLoader;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.items.MovieDataRequest;
import com.list.movie.hyuck.movielist.utils.NetworkUtil;

public class MovieListModel {

    private RemoteDataLoader remoteDataLoader;

    private Context context;


    public MovieListModel(Context context) {
        init(context);
    }

    private void init(Context context) {
        remoteDataLoader = new RemoteDataLoader(context);
        this.context = context;
    }


    public void loadMovieData(MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        if(NetworkUtil.isNetworkConnecting(context)) {
            loadMovieDataFromRemote(movieDataRequest, onMovieDataLoadListener);
        } else {
            onMovieDataLoadListener.onNetworkNotConnectingError();
        }
    }

    public void requestCancelAll() {
        remoteDataLoader.requestCancelAll();
    }


    private void loadMovieDataFromRemote(MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        remoteDataLoader.loadMovieData(movieDataRequest, onMovieDataLoadListener);
    }

}
