package com.znajde.gdzie.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolumeControl extends AppCompatActivity {

    boolean mute;
    ToggleButton muteButton;
    SeekBar seekBar;
    double volumeLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_control);
        muteButton = (ToggleButton) findViewById(R.id.muteButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mute = volumeGetMute();
        muteButton.setChecked(mute);
        seekBar.setProgress((int) volumeGet());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeLevel = progress/100.0;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volumeSet(volumeLevel);
            }
        });
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

        seekBar.setProgress((int) volumeGet());
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

        seekBar.setProgress((int) volumeGet());
    }

    public void volumeMute(View view) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                if(!mute) {
                    SocketSingleton.volumeMute();
                    mute = volumeGetMute();
                }else{
                    SocketSingleton.volumeUnmute();
                    mute = volumeGetMute();
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

    private void volumeSet(final double volumeLevel) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.volumeSet(volumeLevel);
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

    private double volumeGet() {
        final double[] volume = new double[1];
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                volume[0] = SocketSingleton.volumeGet();
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }

        return volume[0];
    }
}
