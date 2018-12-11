package com.list.movie.hyuck.movielist.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.list.movie.hyuck.movielist.R;

public class CyanToast {
    public static void showToastMessage(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.cyan_toast, (ViewGroup)activity.findViewById(R.id.cyanToastTextView));

        TextView customToastTextView = toastLayout.findViewById(R.id.cyanToastTextView);
        customToastTextView.setText(message);

        Toast customToast = new Toast(activity);
        customToast.setDuration(Toast.LENGTH_LONG);
        customToast.setView(toastLayout);

        customToast.show();
    }
}
