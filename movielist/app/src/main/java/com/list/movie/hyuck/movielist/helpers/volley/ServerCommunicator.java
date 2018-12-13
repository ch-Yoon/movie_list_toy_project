package com.list.movie.hyuck.movielist.helpers.volley;

import java.util.Map;

public interface ServerCommunicator {

    void requestData(String url, Map<String, String> requestHeaderMap, OnServerRequestListener onServerRequestListener);

    void cancelAll();

}
