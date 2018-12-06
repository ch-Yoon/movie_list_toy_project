package com.list.movie.hyuck.movielist.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.list.movie.hyuck.movielist.R;


public class GlideLoader implements ImageLoader {

    @Override
    public void imageLoad(Context context, String url, ImageView targetView) {
        RequestManager requestManager = Glide.with(context);
        imageLoad(requestManager, url, targetView);
    }

    private void imageLoad(RequestManager requestManager, String url, ImageView targetView) {
        requestManager.asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .error(R.drawable.no_image)
                        .centerCrop())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(targetView);
    }
}
