package com.list.movie.hyuck.movielist.movielist.model;

import android.content.Context;

import com.google.gson.Gson;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieDataList;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.MovieDataRequest;
import com.list.movie.hyuck.movielist.utils.BinaryUtil;
import com.list.movie.hyuck.movielist.utils.JSONUtil;
import com.list.movie.hyuck.movielist.helpers.volley.OnServerRequestListener;
import com.list.movie.hyuck.movielist.helpers.volley.ServerCommunicator;
import com.list.movie.hyuck.movielist.helpers.volley.VolleySingleton;

import java.util.ArrayList;

public class MovieListModel {

    private static final String API_ADDRESS = "https://openapi.naver.com/v1/search/movie.json?";
    private static final String QUERY = "query=";
    private static final String START = "&start=";
    private static final String DISPLAY = "&display=";

    private ServerCommunicator serverCommunicator;

    private String clientId;
    private String clientSecret;


    public MovieListModel(Context applicationContext) {
        init(applicationContext);
        initAPIKey(applicationContext);
    }

    private void init(Context applicationContext) {
        serverCommunicator = VolleySingleton.getInstance(applicationContext);
    }

    private void initAPIKey(Context applicationContext) {
        String JSONFileName = "apiKey.json";
        String JSONFormatString = JSONUtil.readJSONFile(applicationContext, JSONFileName);

        String clientIdJSONKey = "clientId";
        String clientSecretJSONKey = "clientSecret";
        String keys[] = new String[]{clientIdJSONKey, clientSecretJSONKey};

        String dataList[] = JSONUtil.extractJSONDataList(JSONFormatString, keys);
        String clientIdBinaryArray[] = dataList[0].split(" ");
        String clientSecretBinaryArray[] = dataList[1].split(" ");

        clientId = BinaryUtil.binaryArrayToString(clientIdBinaryArray);
        clientSecret = BinaryUtil.binaryArrayToString(clientSecretBinaryArray);
    }


    public void loadMovieData(MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        handlingLoadMovieData(movieDataRequest, onMovieDataLoadListener);
    }

    public void requestCancelAll() {
        serverCommunicator.cancelAll();
    }


    private void handlingLoadMovieData(MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        loadMoveDataFromServer(movieDataRequest, onMovieDataLoadListener);
    }

    private void loadMoveDataFromServer(final MovieDataRequest movieDataRequest, final OnMovieDataLoadListener onMovieDataLoadListener) {
        String movieTitle = movieDataRequest.getMovieTitle();
        int loadIndex = movieDataRequest.getLoadIndex();
        int requestDataSize = movieDataRequest.getRequestDataSize();

        String url = API_ADDRESS + QUERY + movieTitle + START + loadIndex + DISPLAY + requestDataSize;
        serverCommunicator.requestData(url, clientId, clientSecret, new OnServerRequestListener() {
            @Override
            public void onResult(String response) {
                handlingRequestResultData(response, movieDataRequest, onMovieDataLoadListener);
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

    private void handlingRequestResultData(String response, MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        Gson gson = new Gson();

        MovieDataList movieDataList = gson.fromJson(response, MovieDataList.class);
        ArrayList<MovieData> items = movieDataList.getResult();
        if(items.size() == 0) {
            onMovieDataLoadListener.onNonExistentWordError();
        } else {
            onMovieDataLoadListener.onSuccess(items);

            int requestDataSize = movieDataRequest.getRequestDataSize();
            if(items.size() < requestDataSize) {
                onMovieDataLoadListener.onNoMoreDataError();
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
            case "1000":
                onMovieDataLoadListener.onServerSystemError(errorMessage);
                break;
            case "UNKNOWN_ERROR":
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
                default:
                    onMovieDataLoadListener.onApplicationError(errorMessage);
                    break;
        }
    }

    private String[] extractError(String errorMessageJSONFormat) {
        String errorMessageKey = "errorMessage";
        String errorCodeKey = "errorCode";
        String keys[] = new String[]{errorMessageKey, errorCodeKey};

        String errors[] = JSONUtil.extractJSONDataList(errorMessageJSONFormat, keys);
        if(errors != null) {
            return errors;
        } else {
            return new String[]{"Uncheckable error", "UNKNOWN_ERROR"};
        }
    }
}
