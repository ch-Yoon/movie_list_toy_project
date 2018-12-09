package com.list.movie.hyuck.movielist.modules;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.list.movie.hyuck.movielist.constants.LoadError;
import com.list.movie.hyuck.movielist.listeners.OnServerRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleySingleton implements ServerCommunicator {
    private static VolleySingleton ourInstance = null;
    private RequestQueue requestQueue;


    private VolleySingleton(Context context) {
        initRequestQueue(context);
    }

    private void initRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized  VolleySingleton getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new VolleySingleton(context);
        }

        return ourInstance;
    }


    @Override
    public void requestData(String uri, final String clientId, final String clientSecret, final OnServerRequestListener onServerRequestListener) {
        handlingRequestData(uri, clientId, clientSecret, onServerRequestListener);
    }


    private void handlingRequestData(String uri, final String clientId, final String clientSecret, final OnServerRequestListener onServerRequestListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handlingResponseData(onServerRequestListener, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handlingError(error, onServerRequestListener);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();

                params.put("X-Naver-Client-Id", clientId);
                params.put("X-Naver-Client-Secret", clientSecret);

                return params;
            }
        };

        requestQueue.add(request);
    }

    private void handlingResponseData(OnServerRequestListener onServerRequestListener, String response) {
        if(onServerRequestListener != null) {
            onServerRequestListener.onResult(response);
        }
    }

    private void handlingError(VolleyError volleyError, OnServerRequestListener onServerRequestListener) {
        if(onServerRequestListener != null) {
            LoadError error = extractLoadError(volleyError);

            onServerRequestListener.onError(error);
        }
    }

    private LoadError extractLoadError(VolleyError volleyError) {
        String errorCode = convertToErrorCode(volleyError);
        LoadError error = convertToLoadError(errorCode);

        return error;
    }

    private String convertToErrorCode(VolleyError volleyError) {
        String errorMessageJSONFormat = new String(volleyError.networkResponse.data);
        try {
            JSONObject jsonObject = new JSONObject(errorMessageJSONFormat);
            String errorCode = jsonObject.getString("errorCode");

            return errorCode;
        } catch (JSONException e) {
            return "";
        }
    }

    private LoadError convertToLoadError(String errorCode) {
        LoadError error;
        try {
            error = LoadError.valueOf(errorCode);
        } catch (IllegalArgumentException illegalArgumentException) {
            error = LoadError.UNKNOWN_ERROR;
        }

        return error;
    }
}
