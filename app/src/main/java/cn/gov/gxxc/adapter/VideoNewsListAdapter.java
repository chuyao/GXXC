package cn.gov.gxxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.gov.gxxc.R;
import cn.gov.gxxc.model.VideoNewsModel;

/**
 * Created by ChuyaoShi on 16/11/1.
 */

public class VideoNewsListAdapter extends BaseAdapter {

    private Context context;
    private List<VideoNewsModel> list;

    public VideoNewsListAdapter(Context context, List<VideoNewsModel> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_video_news, parent, false);
            holder = new ViewHolder();
            holder.iv_pre = (ImageView) convertView.findViewById(R.id.iv_pre);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final VideoNewsModel model = list.get(position);
        holder.tvDate.setText(model.getDate());
        holder.tvTitle.setText(model.getTitle());
        holder.iv_pre.setImageResource(R.mipmap.ic_play_circle_outline_black_36dp);
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_pre;
        TextView tvTitle, tvDate;
    }
}
