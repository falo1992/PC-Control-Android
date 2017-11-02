package com.znajde.gdzie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolumeControl extends AppCompatActivity {

    boolean mute;
    ImageButton muteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_control);
        muteButton = (ImageButton) findViewById(R.id.muteButton);
        mute = volumeGetMute();
        if(mute) {
            muteButton.setBackgroundResource(R.drawable.volume_mute);
        }else{
            muteButton.setBackgroundResource(R.drawable.volume_unmute);
        }
    }

    public void volumeUp(View view) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.volumeUp();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

    public void volumeDown(View view) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.volumeDown();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

    public void volumeMute(View view) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                if(!mute) {
                    SocketSingleton.volumeMute();
                    mute = volumeGetMute();
                    view.setBackgroundResource(R.drawable.volume_mute);
                }else{
                    SocketSingleton.volumeUnmute();
                    mute = volumeGetMute();
                    view.setBackgroundResource(R.drawable.volume_unmute);
                }
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

    public boolean volumeGetMute() {
        final boolean[] mute = new boolean[1];
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                mute[0] = SocketSingleton.volumeGetMute();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
        return mute[0];
    }
}
