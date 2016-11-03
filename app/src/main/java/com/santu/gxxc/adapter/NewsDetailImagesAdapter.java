package com.santu.gxxc.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.santu.gxxc.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ChuyaoShi on 16/11/1.
 */

public class NewsDetailImagesAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private DisplayMetrics displayMetrics;

    public NewsDetailImagesAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.displayMetrics = context.getResources().getDisplayMetrics();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_detail_image, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        int width = list.size() > 3 ? displayMetrics.widthPixels / 3 : displayMetrics.widthPixels / list.size();
        int height = list.size() > 3 ? width : (width * 2) / 3;
        holder.image.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        Picasso.with(context).load(list.get(position)).resize(width, height).centerCrop().into(holder.image);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
    }
}
