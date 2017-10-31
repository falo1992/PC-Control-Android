package com.znajde.gdzie.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MultimediaFragment extends android.app.Fragment {


    public MultimediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multimedia, container, false);

        Button volumeButton = (Button) view.findViewById(R.id.volumeButton);
        Button youtubeButton = (Button) view.findViewById(R.id.youtubeButton);
        Button videoButton = (Button) view.findViewById(R.id.videoButton);
        Button wmpButton = (Button) view.findViewById(R.id.wmpButton);
        Button audioButton = (Button) view.findViewById(R.id.audioButton);

        volumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VolumeControl.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
