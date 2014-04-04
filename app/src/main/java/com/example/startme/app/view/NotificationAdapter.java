package com.example.startme.app.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.startme.app.model.Notification;

import java.util.ArrayList;

/**
 * Created by Tian on 4/3/14.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {
    ArrayList<Notification> items;

    public NotificationAdapter(Context context, int textViewResourceId, ArrayList<Notification> items){
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
