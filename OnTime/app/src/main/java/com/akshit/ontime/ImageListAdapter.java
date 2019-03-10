package com.akshit.ontime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageListAdapter extends BaseAdapter {

    Context context;
    private List<String> listImage;
    View view;
    ImageListAdapter(Context context, List<String> objects) {
        this.context=context;
        listImage=objects;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            view = new View(context);
            view = inflater.inflate(R.layout.image_item,null);
            ImageView imageView = view.findViewById(R.id.image);
            Glide.with(context).load(listImage.get(position)).into(imageView);
        }
        return view;
    }
}

