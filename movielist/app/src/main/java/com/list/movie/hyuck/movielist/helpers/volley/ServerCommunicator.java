package com.list.movie.hyuck.movielist.helpers.volley;

public interface ServerCommunicator {

    void requestData(String url, String clientId, String clientSecret, OnServerRequestListener onServerRequestListener);

    void cancelAll();

}
