package com.list.movie.hyuck.movielist.modules;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.list.movie.hyuck.movielist.listeners.OnServerRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleySingleton implements ServerCommunicator {
    private static VolleySingleton ourInstance = null;
    private RequestQueue requestQueue;

    public static synchronized  VolleySingleton getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new VolleySingleton(context);
        }

        return ourInstance;
    }

    private VolleySingleton(Context context) {
        createRequestQueue(context);
    }

    private void createRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void requestData(String url, final String clientId, final String clientSecret, final OnServerRequestListener onServerRequestListener) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processingResponseData(onServerRequestListener, response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        processingError(error, onServerRequestListener);
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

    private void processingResponseData(OnServerRequestListener onServerRequestListener, String response) {
        if(onServerRequestListener != null) {
            onServerRequestListener.onResult(response);
        }
    }

    private void processingError(VolleyError volleyError, OnServerRequestListener onServerRequestListener) {
        if(onServerRequestListener != null) {
            String errorCode = extractErrorCode(volleyError);

            onServerRequestListener.onError(errorCode);
        }
    }

    private String extractErrorCode(VolleyError volleyError) {
        String errorMessage = new String(volleyError.networkResponse.data);
        try {
            JSONObject jsonObject = new JSONObject(errorMessage);
            String errorCode = jsonObject.getString("errorCode");

            return errorCode;
        } catch (JSONException e) {
            return "unknown error";
        }
    }
}
