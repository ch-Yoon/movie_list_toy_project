package com.list.movie.hyuck.movielist.movielist.model;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.list.movie.hyuck.movielist.R;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieData;
import com.list.movie.hyuck.movielist.movielist.model.items.MovieDataList;
import com.list.movie.hyuck.movielist.movielist.presenter.manager.MovieRequest;
import com.list.movie.hyuck.movielist.utils.BinaryUtil;
import com.list.movie.hyuck.movielist.utils.JSONUtil;
import com.list.movie.hyuck.movielist.volley.OnServerRequestListener;
import com.list.movie.hyuck.movielist.volley.ServerCommunicator;
import com.list.movie.hyuck.movielist.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MovieListModel {
    private ServerCommunicator serverCommunicator;

    private Context applicationContext;
    private HashMap<String, String[]> apiKeyMap;

    public MovieListModel(Context applicationContext) {
        init(applicationContext);
    }

    private void init(Context applicationContext) {
        this.applicationContext = applicationContext;
        serverCommunicator = VolleySingleton.getInstance(applicationContext);
        apiKeyMap = new HashMap<>();
    }

    public void loadMovieData(MovieRequest movieRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        handlingLoadMovieData(movieRequest, onMovieDataLoadListener);
    }

    public void requestCancelAll() {
        serverCommunicator.cancelAll();
    }

    private void handlingLoadMovieData(MovieRequest movieRequest, OnMovieDataLoadListener onMovieDataLoadListener) {
        loadMoveDataFromServer(movieRequest, onMovieDataLoadListener);
    }

    private void loadMoveDataFromServer(final MovieRequest movieRequest, final OnMovieDataLoadListener onMovieDataLoadListener) {
        String movieTitle = movieRequest.getMovieTitle();
        int loadIndex = movieRequest.getLoadIndex();
        int requestDataSize = movieRequest.getDataSize();

        String url = "https://openapi.naver.com/v1/search/movie.json?query=" + movieTitle + "&start=" + loadIndex + "&display=" + requestDataSize;
        String APIKeys[] = getAPIKeys();

        String clientId = APIKeys[0];
        String clientSecret = APIKeys[1];

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
        String errorMessageKey = applicationContext.getString(R.string.API_server_error_message_JSON_key);
        String errorCodeKey = applicationContext.getString(R.string.API_server_error_code_JSON_key);
        String keys[] = new String[]{errorMessageKey, errorCodeKey};

        String errors[] = JSONUtil.extractJSONDataList(errorMessageJSONFormat, keys);
        if(errors != null) {
            return errors;
        } else {
            return new String[]{"Uncheckable error", "UNKNOWN_ERROR"};
        }
    }

    private String[] getAPIKeys() {
        String clientIdJSONKey = applicationContext.getString(R.string.API_client_id_JSON_key);
        String clientSecretJSONKey = applicationContext.getString(R.string.API_client_secret_JSON_key);
        String mapKey = clientIdJSONKey + "&" + clientIdJSONKey;
        if(apiKeyMap.containsKey(mapKey)) {
            return apiKeyMap.get(mapKey);
        } else {
            String APIKeys[] = new String[]{"", ""};

            String JSONFileName = applicationContext.getString(R.string.API_Key_JSON_file_name);
            String JSONFormatString = JSONUtil.readJSONFile(applicationContext, JSONFileName);

            String keys[] = new String[]{clientIdJSONKey, clientSecretJSONKey};
            String dataList[] = JSONUtil.extractJSONDataList(JSONFormatString, keys);
            if(dataList != null) {
                String clientIdBinaryArray[] = dataList[0].split(" ");
                String clientSecretBinaryArray[] = dataList[1].split(" ");

                APIKeys[0] = BinaryUtil.binaryArrayToString(clientIdBinaryArray);
                APIKeys[1] = BinaryUtil.binaryArrayToString(clientSecretBinaryArray);

                apiKeyMap.put(mapKey, APIKeys);
            }

            return APIKeys;
        }
    }
}
