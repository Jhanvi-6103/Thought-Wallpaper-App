package com.jtthought.thoughtwallpaperapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class WallpaperAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> wallpapers;

    public WallpaperAdapter(Context context, ArrayList<Integer> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @Override
    public int getCount() {
        return wallpapers.size();
    }

    @Override
    public Object getItem(int position) {
        return wallpapers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate custom wallpaper layout for each item in GridView
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_wallpaper, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewWallpaper);
        imageView.setImageResource(wallpapers.get(position));

        return convertView;
    }
}
