package com.list.movie.hyuck.movielist.volley;

public interface ServerCommunicator {
    void requestData(String url, String clientId, String clientSecret, OnServerRequestListener onServerRequestListener);
}