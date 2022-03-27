package com.step.androd.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Create By: Meng
 * Create Date: 2021/12/31
 * Desc:
 */
public class ToastUtils {
    private static Toast toast;

    public static void show(Activity context, String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
//        LayoutInflater inflater = context.getLayoutInflater();
//        LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast, null);
//        TextView txtToast = toastLayout.findViewById(R.id.app_cum_toast);
//        txtToast.setText(msg);
//        toast.setView(toastLayout);
        toast.show();
    }

}
