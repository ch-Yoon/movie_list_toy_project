package com.list.movie.hyuck.movielist.widzets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;

import com.list.movie.hyuck.movielist.R;

public class CustomProgressDialog {

    private Context context;
    private AppCompatDialog progressBar;


    public CustomProgressDialog(Context context) {
        this.context = context;
    }


    synchronized public void show(boolean show) {
        if(show) {
            if(isShowing()) {
                dialogHide();
            }

            createDialog();
            dialogOpen();
        } else {
            if(isShowing()) {
                dialogHide();
            }
        }
    }


    private boolean isShowing() {
        return progressBar != null && progressBar.isShowing();
    }

    private void dialogOpen() {
        progressBar.show();
    }

    private void dialogHide() {
        progressBar.dismiss();
        progressBar = null;
    }

    private void createDialog() {
        progressBar = new AppCompatDialog(context);
        progressBar.setContentView(R.layout.custom_progress_dialog);
        Window progressWindow = progressBar.getWindow();
        if(progressWindow != null) {
            progressWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
