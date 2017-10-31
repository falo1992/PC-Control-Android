package com.znajde.gdzie.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Search extends ListActivity {
    boolean connected = false;
    static ArrayList<String> listItems = new ArrayList<>();
    static ArrayAdapter<String> adapter;


public static Handler UIHandler;
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

    }

    @Override
    protected  void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //TODO Popup window 'Do you want to connect to: ..... ?
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.init(Search.this, listItems.get(position), getPort());
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

    public static void addListElement(String row){
        Search.runOnUI(new Runnable() {
            @Override
            public void run() {
                listItems.add(row);
                adapter.notifyDataSetInvalidated();
            }
        });
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    public void search(View view) {
        new Thread() {
            public void run() {
                SocketSingleton.search(getPort());
            }
        }.start();
    }

    public int getPort() {
        return Integer.parseInt(((EditText) findViewById(R.id.searchPort)).getText().toString());
    }

    private void setConnected(boolean connected) {
        this.connected = connected;
    }
}
