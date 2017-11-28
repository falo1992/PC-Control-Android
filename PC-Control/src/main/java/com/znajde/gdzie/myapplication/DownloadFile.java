package com.znajde.gdzie.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DownloadFile extends AppCompatActivity {
    private ListView lv;
    private RemoteFileList remoteFileList;
    private DownloadListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        FileUtils.verifyStoragePermissions(this);
        lv = (ListView) findViewById(R.id.remoteFiles);
        getRemoteFiles("desktop");
        adapter = new DownloadListAdapter(this, remoteFileList.getNameArray(), remoteFileList.getTypeArray());
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((int) adapter.getItem(position) == 0) {
                    getRemoteFiles(remoteFileList.getPathArray()[position]);
                    adapter.updateList(remoteFileList.getNameArray(), remoteFileList.getTypeArray());
                } else {
                    downloadFile(remoteFileList.getPathArray()[position]);
                }
            }
        });
    }

    public void getRemoteFiles(final String path) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                remoteFileList = new RemoteFileList(SocketSingleton.getRemoteFilesList(path));
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
        }
    }

    public void downloadFile(final String path) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.download(path);
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(10, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
        }
    }
}
