package com.step.androd.modules.system;

import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Create By: Meng
 * Create Date: 21/10/21
 * Desc:
 */
public class Recorder {
    private MediaRecorder mMediaRecorder;
    private String fileName;
    private String filePath;

    public Recorder() {
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/step/audio/";
    }

    public void startRecord() {
        // 开始录音
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            fileName = DateFormat.format("yyyyMMddHHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
            }
            filePath = filePath + fileName;
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        try {
            mMediaRecorder.stop();
        } catch (RuntimeException e) {
            e.printStackTrace();
            mMediaRecorder.reset();
            File file = new File(filePath, fileName);
            if (file.exists())
                file.delete();
        }
        mMediaRecorder.release();
        mMediaRecorder = null;
    }
}
