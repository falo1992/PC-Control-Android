package com.znajde.gdzie.myapplication;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Video extends AppCompatActivity {

    private static ImageView imageView;
    public static Handler UIHandler;
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        imageView = (ImageView)findViewById(R.id.imageView);
        getVideo();
    }

    private void getVideo() {
        new Thread() {
            @Override
            public void run() {
                SocketSingleton.getVideo();
            }
        }.start();
    }

    public static void setImage(final Bitmap map) {
        Video.runOnUI(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(map);
            }
        });
    }

    private static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
