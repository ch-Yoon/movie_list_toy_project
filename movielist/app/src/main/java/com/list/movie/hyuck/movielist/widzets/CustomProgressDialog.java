package com.list.movie.hyuck.movielist.widzets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;

import com.list.movie.hyuck.movielist.R;

public class CustomProgressDialog {

    private Context context;
    private AppCompatDialog progressDialog;


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
        return progressDialog != null && progressDialog.isShowing();
    }

    private void dialogOpen() {
        progressDialog.show();
    }

    private void dialogHide() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    private void createDialog() {
        progressDialog = new AppCompatDialog(context);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        Window progressWindow = progressDialog.getWindow();
        if(progressWindow != null) {
            progressWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
