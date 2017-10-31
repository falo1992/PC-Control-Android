package com.znajde.gdzie.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;

public class Connect extends AppCompatActivity {
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void connect (View view) {
        //new Thread(new ClientThread()).start();
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.init(Connect.this ,getIP(), getPort());
                setConnected(SocketSingleton.isConnected());
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
        if(connected) {
            Intent intent = new Intent(this, MainContent.class);
            startActivity(intent);
        }
    }
    public void setConnected(boolean connected){
        this.connected = connected;
    }

    public void search (View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    private int getPort() {
        return Integer.parseInt(((EditText) findViewById(R.id.connectPort)).getText().toString());
    }

    private String getIP() {
        return ((EditText) findViewById(R.id.connectIP)).getText().toString();
    }
}
