package com.list.movie.hyuck.movielist.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;

import com.list.movie.hyuck.movielist.R;

public class CustomProgressDialog {
    private Context context;
    private AppCompatDialog progress;

    public CustomProgressDialog(Context context) {
        this.context = context;
    }

    public void show() {
        hide();
        createDialog();

        if(progress != null && !progress.isShowing()) {
            progress.show();
        }
    }

    public void hide() {
        if(progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    private void createDialog() {
        progress = new AppCompatDialog(context);
        progress.setContentView(R.layout.custom_progress_dialog);
        Window progressWindow = progress.getWindow();
        if(progressWindow != null) {
            progressWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
