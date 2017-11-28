package com.znajde.gdzie.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class DownloadListAdapter extends BaseAdapter {
    private Context context;
    private String[] listElements;
    private int[] listElementsTypes;

    public DownloadListAdapter(Context context, String[] listElements, int[] listElementsTypes) {
        this.context = context;
        this.listElements = listElements;
        this.listElementsTypes = listElementsTypes;
    }

    @Override
    public int getCount() {
        return listElements.length;
    }

    @Override
    public Object getItem(int position) {
        return listElementsTypes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        View row;
        TextView title;
        ImageView imageView;

        row = inflater.inflate(R.layout.download_list_row, parent, false);
        title = (TextView) row.findViewById(R.id.txtTitle);
        title.setText(listElements[position]);
        imageView = (ImageView) row.findViewById(R.id.imgIcon);
        if(listElementsTypes[position] == 0) {
            imageView.setImageResource(R.drawable.folder);
        }else{
            imageView.setImageResource(R.drawable.file);
        }

        return (row);
    }

    public void updateList(String[] listElements, int[] listElementsTypes) {
        this.listElements = listElements;
        this.listElementsTypes = listElementsTypes;
        this.notifyDataSetChanged();
    }
}
