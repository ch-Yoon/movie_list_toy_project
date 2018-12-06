package com.list.movie.hyuck.movielist.loaders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.list.movie.hyuck.movielist.constants.LoadError;
import com.list.movie.hyuck.movielist.items.MovieData;
import com.list.movie.hyuck.movielist.items.MovieDataList;
import com.list.movie.hyuck.movielist.listeners.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.listeners.OnServerRequestListener;
import com.list.movie.hyuck.movielist.modules.ServerCommunicator;
import com.list.movie.hyuck.movielist.modules.VolleySingleton;

import java.util.ArrayList;

public class MovieDataLoader {
    private Context context;
    private ServerCommunicator serverCommunicator;

    public MovieDataLoader(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        serverCommunicator = VolleySingleton.getInstance(context.getApplicationContext());
    }


    public void loadMovieData(String movieTitle, int startIndex, OnMovieDataLoadListener onMovieDataLoadListener) {
        if(onMovieDataLoadListener != null) {
            processingLoadMovieData(movieTitle, startIndex, onMovieDataLoadListener);
        }
    }


    private void processingLoadMovieData(String movieTitle, int startIndex, OnMovieDataLoadListener onMovieDataLoadListener) {
        if(isNetworkConnecting()) {
            loadMoveDataFromServer(movieTitle, startIndex, onMovieDataLoadListener);
        } else {
            onMovieDataLoadListener.onNetworkNotConnectingError();
        }
    }

    private boolean isNetworkConnecting() {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return networkInfo != null;
    }

    private void loadMoveDataFromServer(String movieTitle, int startIndex, final OnMovieDataLoadListener onMovieDataLoadListener) {
        String url = "https://openapi.naver.com/v1/search/movie.json?query=" + movieTitle + "&start=" + startIndex;
        String clientId = "8s8wwGUG6AI8OjMV1SpN";
        String clientSecret = "lucyznUH91";

        serverCommunicator.requestData(url, clientId, clientSecret, new OnServerRequestListener() {
            @Override
            public void onResult(String response) {
                processingRequestResultData(response, onMovieDataLoadListener);
            }

            @Override
            public void onError(LoadError loadError) {
                processingRequestErrorData(loadError, onMovieDataLoadListener);
            }
        });
    }

    private void processingRequestResultData(String response, OnMovieDataLoadListener onMovieDataLoadListener) {
        Gson gson = new Gson();

        MovieDataList movieDataList = gson.fromJson(response, MovieDataList.class);
        ArrayList<MovieData> items = movieDataList.getResult();
        if(items.size() == 0) {
            onMovieDataLoadListener.onNonExistentWordError();
        } else {
            onMovieDataLoadListener.onSuccess(items);
        }
    }

    private void processingRequestErrorData(LoadError error, OnMovieDataLoadListener onMovieDataLoadListener) {
        String errorMessage = error.getErrorMessage();
        switch (error) {
            case SE01:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SE02:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SE03:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SE04:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SE05:
                onMovieDataLoadListener.onNonExistentWordError();
                break;
            case SE06:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SE99:
                onMovieDataLoadListener.onServerSystemError(errorMessage);
                break;
            case UNKNOWN_ERROR:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
        }
    }
}
