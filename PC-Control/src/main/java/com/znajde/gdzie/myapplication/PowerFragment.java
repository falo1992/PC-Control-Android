package com.znajde.gdzie.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PowerFragment extends android.app.Fragment {

    public PowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_power, container, false);

        Button shutdownButton = (Button) view.findViewById(R.id.shutdownButton);
        Button restartButton = (Button) view.findViewById(R.id.restartButton);

        shutdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        SocketSingleton.shutdown();
                    }
                }.start();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        SocketSingleton.restart();
                    }
                }.start();
            }
        });

        return view;
    }

}
