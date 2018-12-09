package com.list.movie.hyuck.movielist.listeners;

import com.list.movie.hyuck.movielist.constants.LoadError;

public interface OnServerRequestListener {
    void onResult(String response);
    void onError(LoadError loadError);
}
