package com.step.androd.utils.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public class SystemUtils {

    public static String getVersion(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            int versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return "";
    }

    public static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n主板： "+ Build.BOARD+"\n");
        sb.append("系统启动程序版本号： " + Build.BOOTLOADER+"\n");
        sb.append("系统定制商：" + Build.BRAND+"\n");
        sb.append("cpu指令集： " + Build.SUPPORTED_ABIS+"\n");
//        sb.append("cpu指令集2 "+ Build.CPU_ABI+"\n");
        sb.append("设置参数： "+ Build.DEVICE+"\n");
        sb.append("显示屏参数：" + Build.DISPLAY+"\n");
        sb.append("无线电固件版本：" + Build.getRadioVersion()+"\n");
        sb.append("硬件识别码：" + Build.FINGERPRINT+"\n");
        sb.append("硬件名称：" + Build.HARDWARE+"\n");
        sb.append("HOST: " + Build.HOST+"\n");
        sb.append("修订版本列表：" + Build.ID+"\n");
        sb.append("硬件制造商：" + Build.MANUFACTURER+"\n");
        sb.append("版本：" + Build.MODEL+"\n");
        sb.append("硬件序列号：" + Build.SERIAL+"\n");
        sb.append("手机制造商：" + Build.PRODUCT+"\n");
        sb.append("描述Build的标签：" + Build.TAGS+"\n");
        sb.append("TIME: " + Build.TIME+"\n");
        sb.append("builder类型：" + Build.TYPE+"\n");
        sb.append("USER: " + Build.USER+"\n");
        sb.append("当前开发代号: " + Build.VERSION.CODENAME+"\n");
        sb.append(" 源码控制版本号: " + Build.VERSION.INCREMENTAL+"\n");
        sb.append("版本字符串: " + Build.VERSION.RELEASE+"\n");
        sb.append("版本号: " + Build.VERSION.SDK_INT+"\n");
        return sb.toString();
    }
}
