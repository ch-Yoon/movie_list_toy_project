package com.list.movie.hyuck.movielist.helpers.volley;

public interface OnServerRequestListener {

    void onResult(String response);

    void onError(String errorMessage);

}
