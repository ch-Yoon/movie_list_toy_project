package com.list.movie.hyuck.movielist.helpers.glide;

import android.app.Activity;
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


public class GlideLoader implements ImageLoader {

    private RequestManager requestManager;


    public GlideLoader(Activity activity) {
        requestManager = Glide.with(activity);
    }


    @Override
    public void loadImage(String uri, ImageView targetView) {
        handlingImageLoad(uri, targetView);
    }


    private void handlingImageLoad(String uri, ImageView targetView) {
        requestManager.asBitmap()
                .load(uri)
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
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
