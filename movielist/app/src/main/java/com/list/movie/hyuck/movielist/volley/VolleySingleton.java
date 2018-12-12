package com.list.movie.hyuck.movielist.volley;

import android.content.Context;
import android.nfc.Tag;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.list.movie.hyuck.movielist.utils.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class VolleySingleton implements ServerCommunicator {
    private static final String TAG = "Request";

    private static VolleySingleton ourInstance = null;
    private Context applicationContext;
    private RequestQueue requestQueue;

    private VolleySingleton(Context applicationContext) {
        initRequestQueue(applicationContext);
    }

    private void initRequestQueue(Context applicationContext) {
        this.applicationContext = applicationContext;
        requestQueue = Volley.newRequestQueue(applicationContext);
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new VolleySingleton(context);
        }

        return ourInstance;
    }

    @Override
    public void requestData(String uri, final String clientId, final String clientSecret, final OnServerRequestListener onServerRequestListener) {
        if(NetworkUtil.isNetworkConnecting(applicationContext)) {
            handlingRequestData(uri, clientId, clientSecret, onServerRequestListener);
        } else {
            onServerRequestListener.onNetworkNotConnecting();
        }
    }

    @Override
    public void cancelAll() {
        requestQueue.cancelAll(TAG);
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
