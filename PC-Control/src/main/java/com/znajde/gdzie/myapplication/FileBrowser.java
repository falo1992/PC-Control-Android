package com.znajde.gdzie.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileBrowser extends AppCompatActivity {

    private static final int REQUEST_RUNTIME_PERMISSION = 1;
    private static final int FILE_SELECT_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtils.verifyStoragePermissions(this);
        setContentView(R.layout.activity_file_browser);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        try{
            startActivityForResult(Intent.createChooser(intent, "select file"), FILE_SELECT_CODE);
        }catch(android.content.ActivityNotFoundException e){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FILE_SELECT_CODE) {
            if(resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String path = FileUtils.getPath(this, uri);
                sendFile(path);
            }
        }
    }

    public void sendFile(final String path) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                SocketSingleton.send(path);
            }
        });
        es.shutdown();
        try {
            es.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
        }
    }

}