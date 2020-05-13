package com.example.myEcom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myEcom.adapters.NotificationPojo;

import java.util.ArrayList;


public class NotificationAdapter extends ArrayAdapter<NotificationPojo>{

    private final Context context;
    private final ArrayList<NotificationPojo> itemsArrayList;

    public NotificationAdapter(Context context, ArrayList<NotificationPojo> itemsArrayList) {

        super(context, R.layout.notification_cell, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.notification_cell, parent, false);

        TextView labelView = rowView.findViewById(R.id.notiftitle);
        TextView valueView = rowView.findViewById(R.id.notifbody);

        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getBody());

        return rowView;
    }
}