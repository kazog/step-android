package com.step.androd;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Create By: Meng
 * Create Date: 2022/03/27
 * Desc:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void getChannel() {
        PackageManager pm = getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("CHANNEL_NAME");
            Log.i("App", "===============> "+channel);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("App", "===============> "+e.getMessage());
        }

    }
}
