package com.list.movie.hyuck.movielist.volley;

public interface OnServerRequestListener {
    void onResult(String response);
    void onError(String errorMessage);
}
