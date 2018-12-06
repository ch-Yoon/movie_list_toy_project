package com.list.movie.hyuck.movielist.loaders;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {
    void imageLoad(Context context, String url, ImageView targetView);
}
