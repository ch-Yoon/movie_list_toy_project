package com.list.movie.hyuck.movielist.helpers.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleySingleton implements ServerCommunicator {

    private static final String TAG = "Request";

    private static VolleySingleton ourInstance = null;

    private RequestQueue requestQueue;


    private VolleySingleton(Context applicationContext) {
        initRequestQueue(applicationContext);
    }

    private void initRequestQueue(Context applicationContext) {
        requestQueue = Volley.newRequestQueue(applicationContext);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new VolleySingleton(context);
        }

        return ourInstance;
    }


    @Override
    public void requestData(String uri, final Map<String, String> requestHeaderMap, final OnServerRequestListener onServerRequestListener) {
        handlingRequestData(uri, requestHeaderMap, onServerRequestListener);
    }

    @Override
    public void cancelAll() {
        requestQueue.cancelAll(TAG);
    }


    private void handlingRequestData(String uri, final Map<String, String> requestHeaderMap, final OnServerRequestListener onServerRequestListener) {
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
                return requestHeaderMap;
            }
        };

        request.setTag(TAG);

        requestQueue.add(request);
    }

    private void handlingResponseData(OnServerRequestListener onServerRequestListener, String response) {
        if(onServerRequestListener != null) {
            onServerRequestListener.onResult(response);
        }
    }

    private void handlingError(VolleyError volleyError, OnServerRequestListener onServerRequestListener) {
        if(onServerRequestListener != null) {
            String errorMessage = new String(volleyError.networkResponse.data);

            onServerRequestListener.onError(errorMessage);
        }
    }

}
