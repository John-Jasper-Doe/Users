/**
 * @file  CustomListAdapter.java
 * @brief Custom List Adapter.
 * @date  01.08.2018
 * @autor M.Gusev
 */

package com.example.john.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
//import com.javatechig.feedreader.asynctaask.ImageDownloaderTask;


public class CustomListAdapter extends BaseAdapter {
    private ArrayList<UserItem> listData;
    private LayoutInflater 		layoutInflater;
    private Context 			mContext;

    public CustomListAdapter(Context context, ArrayList<UserItem> listData) {
        this.listData  = listData;
        layoutInflater = LayoutInflater.from(context);
        mContext       = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserItem userItem = (UserItem) listData.get(position);
        holder.headlineView.setText(userItem.getNameLast() + ", " + userItem.getNameFirst());

        if (holder.imageView != null) {
            new ImageDownloader(holder.imageView).execute(userItem.getPicThumbnailUrl());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView  headlineView;
        ImageView imageView;
    }
}


