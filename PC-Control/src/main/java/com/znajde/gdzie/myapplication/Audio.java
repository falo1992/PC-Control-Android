package com.znajde.gdzie.myapplication;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Audio extends AppCompatActivity {

    private static Handler UIHandler;
    private static AudioTrack audioTrack;
    private static int bufferSize;
    private static int frequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        frequency = 44100;
        UIHandler = new Handler(Looper.getMainLooper());
//        bufferSize = android.media.AudioTrack.getMinBufferSize(frequency, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        bufferSize = 512;
        audioTrack = new  AudioTrack(AudioManager.STREAM_MUSIC  , frequency, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        audioTrack.play();
        getAudio();
    }

    private void getAudio() {
        new Thread() {
            @Override
            public void run() {
                SocketSingleton.getAudio();
            }
        }.start();
    }

    public static void setAudio(final byte[] audioInput) {
        audioTrack.write(audioInput, 0, audioInput.length);

    }

    private static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
