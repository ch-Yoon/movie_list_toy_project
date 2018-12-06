package com.list.movie.hyuck.movielist.listeners;

public interface OnServerRequestListener {
    void onResult(String response);
    void onError(String errorCode);
}
