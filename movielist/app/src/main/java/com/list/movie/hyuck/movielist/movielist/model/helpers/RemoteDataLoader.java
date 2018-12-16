package com.list.movie.hyuck.movielist.movielist.model.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.list.movie.hyuck.movielist.helpers.volley.OnServerRequestListener;
import com.list.movie.hyuck.movielist.helpers.volley.ServerCommunicator;
import com.list.movie.hyuck.movielist.helpers.volley.VolleySingleton;
import com.list.movie.hyuck.movielist.movielist.model.OnMovieDataLoadListener;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieDataList;
import com.list.movie.hyuck.movielist.movielist.presenter.helpers.items.MovieDataRequest;
import com.list.movie.hyuck.movielist.utils.BinaryUtil;
import com.list.movie.hyuck.movielist.utils.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemoteDataLoader {

    private static final String API_ADDRESS = "https://openapi.naver.com/v1/search/movie.json?";
    private static final String QUERY = "query=";
    private static final String START = "&start=";
    private static final String DISPLAY = "&display=";
    private static final int MAX_START_INDEX = 1000;

    private static final String INCORRECT_QUERY_ERROR = "SE01";
    private static final String INVALID_DISPLAY_ERROR = "SE02";
    private static final String INVALID_START_ERROR = "SE03";
    private static final String INVALID_SORT_VALUE_ERROR = "SE04";
    private static final String INVALID_SEARCH_API = "SE05";
    private static final String MALFORMED_ENCODING_ERROR = "SE06";
    private static final String SYSTEM_ERROR = "SE99";
    private static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

    private ServerCommunicator serverCommunicator;

    private Map<String, String> requestHeaderMap;


    public RemoteDataLoader(Context applicationContext) {
        init(applicationContext);
        initRequestHeaderMap(applicationContext);
    }

    private void init(Context applicationContext) {
        serverCommunicator = VolleySingleton.getInstance(applicationContext);
    }

    private void initRequestHeaderMap(Context applicationContext) {
        String JSONFileName = "apiKey.json";
        String JSONFormatString = JSONUtil.readJSONFile(applicationContext, JSONFileName);

        String clientIdJSONKey = "clientId";
        String clientSecretJSONKey = "clientSecret";
        String keys[] = new String[]{clientIdJSONKey, clientSecretJSONKey};

        String dataList[] = JSONUtil.extractJSONDataList(JSONFormatString, keys);
        String clientIdBinaryArray[] = dataList[0].split(" ");
        String clientSecretBinaryArray[] = dataList[1].split(" ");

        String clientId = BinaryUtil.binaryArrayToString(clientIdBinaryArray);
        String clientSecret = BinaryUtil.binaryArrayToString(clientSecretBinaryArray);

        requestHeaderMap = new HashMap<>();
        requestHeaderMap.put("X-Naver-Client-Id", clientId);
        requestHeaderMap.put("X-Naver-Client-Secret", clientSecret);
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

        if(loadIndex <= MAX_START_INDEX) {
            String url = API_ADDRESS + QUERY + movieTitle + START + loadIndex + DISPLAY + requestDataSize;
            serverCommunicator.requestData(url, requestHeaderMap, new OnServerRequestListener() {
                @Override
                public void onResult(String response) {
                    handlingRequestResult(response, movieDataRequest, onMovieDataLoadListener);
                }

                @Override
                public void onError(String errorMessage) {
                    handlingRequestError(errorMessage, onMovieDataLoadListener);
                }
            });
        }
    }

    private void handlingRequestResult(String response, MovieDataRequest movieDataRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
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

    private void handlingRequestError(String errorMessageJSONFormat, OnMovieDataLoadListener onMovieDataLoadListener) {
        String error[] = extractError(errorMessageJSONFormat);

        String errorMessage = error[0];
        String errorCode = error[1];
        switch (errorCode) {
            case INCORRECT_QUERY_ERROR :
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case INVALID_DISPLAY_ERROR:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case INVALID_START_ERROR:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case INVALID_SORT_VALUE_ERROR:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case INVALID_SEARCH_API:
                onMovieDataLoadListener.onNonExistentWordError();
                break;
            case MALFORMED_ENCODING_ERROR:
                onMovieDataLoadListener.onApplicationError(errorMessage);
                break;
            case SYSTEM_ERROR:
                onMovieDataLoadListener.onServerSystemError(errorMessage);
                break;
            case UNKNOWN_ERROR:
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
