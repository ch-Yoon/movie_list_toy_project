package com.list.movie.hyuck.movielist.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static  boolean isNetworkConnecting(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            return networkInfo != null;
        }

        return false;
    }

}
