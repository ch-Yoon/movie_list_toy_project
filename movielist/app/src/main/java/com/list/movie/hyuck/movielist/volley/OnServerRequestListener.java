package com.list.movie.hyuck.movielist.volley;

public interface OnServerRequestListener {
    void onResult(String response);
    void onNetworkNotConnecting();
    void onError(String errorMessage);
}
