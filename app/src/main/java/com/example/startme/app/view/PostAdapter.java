package com.example.startme.app.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.startme.app.model.Post;

import java.util.ArrayList;

/**
 * Created by Tian on 4/3/14.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    ArrayList<Post> items;

    public PostAdapter(Context context, int textViewResourceId, ArrayList<Post> items){
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

}
