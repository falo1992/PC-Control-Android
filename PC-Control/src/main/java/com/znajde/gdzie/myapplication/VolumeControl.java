package com.znajde.gdzie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolumeControl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_control);
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
                SocketSingleton.volumeMute();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

    public void volumeUnmute(View view) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.volumeUnmute();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }
}
