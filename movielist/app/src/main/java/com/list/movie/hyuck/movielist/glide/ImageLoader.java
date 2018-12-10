package com.list.movie.hyuck.movielist.glide;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {
    void imageLoad(Context context, String uri, ImageView targetView);
}
