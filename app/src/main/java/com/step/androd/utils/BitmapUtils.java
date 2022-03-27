package com.step.androd.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    // 图片压缩
    public static Bitmap decodeSampledBitmapFromRes(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void createScaledBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap compress = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);

        // 或者直接使用 matrix 进行缩放，查看Bitmap.createScaledBitmap源码其实就是使用 matrix 缩放
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    /**
     * 质量压缩
     * 设置bitmap options属性，降低图片的质量，像素不会减少
     * 第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
     * 设置options 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     */
    public static void qualityBitmap(String path, String outPath) {
        int quality = 20;
        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(outPath, "test.jpg");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap compress = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compress.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            close(baos);
            close(fos);
            bitmap.recycle();
            compress.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void regionDecoder(Context context) {
        try {
            InputStream inputStream = context.getResources().getAssets().open("test.jpg");
            BitmapRegionDecoder mRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options sOptions = new BitmapFactory.Options();
            sOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
            sOptions.inSampleSize = 2;
            Rect mRect = new Rect();
            mRect.top = 0;
            mRect.left = 0;
            mRect.right = 100;
            mRect.bottom = 100;
            Bitmap bitmap = mRegionDecoder.decodeRegion(mRect, sOptions);
            //bitmap.getByteCount()=40000
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Closeable stream) {
        try {
            stream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
