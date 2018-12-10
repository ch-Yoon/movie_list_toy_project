package com.list.movie.hyuck.movielist.movielist.model;

import android.content.Context;

import com.google.gson.Gson;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieDataList;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.MovieRequest;
import com.list.movie.hyuck.movielist.volley.OnServerRequestListener;
import com.list.movie.hyuck.movielist.volley.ServerCommunicator;
import com.list.movie.hyuck.movielist.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListModel {
    private ServerCommunicator serverCommunicator;

    public MovieListModel(Context applicationContext) {
        init(applicationContext);
    }

    private void init(Context applicationContext) {
        serverCommunicator = VolleySingleton.getInstance(applicationContext);
    }

    public void loadMovieData(MovieRequest movieRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        handlingLoadMovieData(movieRequest, onMovieDataLoadListener);
    }

    private void handlingLoadMovieData(MovieRequest movieRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        loadMoveDataFromServer(movieRequest, onMovieDataLoadListener);
    }

    private void loadMoveDataFromServer(final MovieRequest movieRequest, final OnMovieDataLoadListener onMovieDataLoadListener) {
        String movieTitle = movieRequest.getMovieTitle();
        int loadIndex = movieRequest.getLoadIndex();
        int requestDataSize = movieRequest.getDataSize();

        String url = "https://openapi.naver.com/v1/search/movie.json?query=" + movieTitle + "&start=" + loadIndex + "&display=" + requestDataSize;
        String clientId = "8s8wwGUG6AI8OjMV1SpN";
        String clientSecret = "lucyznUH91";

        serverCommunicator.requestData(url, clientId, clientSecret, new OnServerRequestListener() {
            @Override
            public void onResult(String response) {
                handlingRequestResultData(response, movieRequest, onMovieDataLoadListener);
            }

            @Override
            public void onNetworkNotConnecting() {
                handlingNetworkNotConnecting(onMovieDataLoadListener);
            }

            @Override
            public void onError(String errorMessage) {
                handlingRequestErrorData(errorMessage, onMovieDataLoadListener);
            }
        });
    }

    private void handlingRequestResultData(String response, MovieRequest movieRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        Gson gson = new Gson();

        MovieDataList movieDataList = gson.fromJson(response, MovieDataList.class);
        ArrayList<MovieData> items = movieDataList.getResult();
        if(items.size() == 0) {
            onMovieDataLoadListener.onNonExistentWordError();
        } else {
            onMovieDataLoadListener.onSuccess(items);

            if(movieRequest.getDataSize() != items.size()) {
                onMovieDataLoadListener.onNoMoreData();
            }
        }
    }

    private void handlingNetworkNotConnecting(OnMovieDataLoadListener onMovieDataLoadListener) {
        onMovieDataLoadListener.onNetworkNotConnectingError();
    }

    private void handlingRequestErrorData(String errorMessageJSONFormat, OnMovieDataLoadListener onMovieDataLoadListener) {
        String error[] = extractError(errorMessageJSONFormat);
        String errorMessage = error[0];
        String errorCode = error[1];

        switch (errorCode) {
            case "SE01":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case "SE02":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case "SE03":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case "SE04":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case "SE05":
                onMovieDataLoadListener.onNonExistentWordError();
                break;
            case "SE06":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case "SE99":
                onMovieDataLoadListener.onServerSystemError(errorMessage);
                break;
            case "000":
                onMovieDataLoadListener.onServerSystemError(errorMessage);
                break;
            case "UNKNOWN_ERROR":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
        }
    }

    private String[] extractError(String errorMessageJSONFormat) {
        try {
            JSONObject jsonObject = new JSONObject(errorMessageJSONFormat);
            String errorMessage = jsonObject.getString("errorMessage");
            String errorCode = jsonObject.getString("errorCode");

            return new String[]{errorMessage, errorCode};
        } catch (JSONException e) {
            return new String[]{"Uncheckable error", "UNKNOWN_ERROR"};
        }
    }
}
