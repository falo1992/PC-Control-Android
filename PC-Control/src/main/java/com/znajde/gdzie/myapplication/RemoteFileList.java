package com.znajde.gdzie.myapplication;

import android.util.Log;

public class RemoteFileList {
    private String[] name;
    private int[] type;
    private String[] path;

    public RemoteFileList(String[][] list) {
        type = new int[list.length];
        name = new String[list.length];
        path = new String[list.length];

        for(int i = 0; i < list.length; i++) {
            type[i] = Integer.parseInt(list[i][0]);
            name[i] = list[i][1];
            path[i] = list[i][2];
            Log.d("Path ==========", list[i][2]);
        }
    }

    public String[] getNameArray() {
        return name;
    }

    public int[] getTypeArray() {
        return type;
    }

    public String[] getPathArray() {
        return path;
    }
}
