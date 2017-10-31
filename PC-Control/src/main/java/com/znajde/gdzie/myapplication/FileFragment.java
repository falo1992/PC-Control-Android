package com.znajde.gdzie.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FileFragment extends android.app.Fragment {


    public FileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file, container, false);

        Button sendFileButton = (Button) view.findViewById(R.id.sendFileButton);
        Button downloadFileButton = (Button) view.findViewById(R.id.downloadFileButton);

        sendFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FileBrowser.class);
                startActivity(intent);

            }
        });

        downloadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        SocketSingleton.download("");
                    }
                }.start();
            }
        });
        return view;
    }

}
