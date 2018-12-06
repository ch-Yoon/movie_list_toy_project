package com.list.movie.hyuck.movielist.modules;

import com.list.movie.hyuck.movielist.listeners.OnServerRequestListener;

public interface ServerCommunicator {
    void requestData(String url, String clientId, String clientSecret, OnServerRequestListener onServerRequestListener);
}
