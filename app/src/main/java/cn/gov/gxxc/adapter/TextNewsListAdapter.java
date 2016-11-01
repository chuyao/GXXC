package cn.gov.gxxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.gov.gxxc.R;
import cn.gov.gxxc.model.TextNewsModel;

/**
 * Created by ChuyaoShi on 16/10/31.
 */

public class TextNewsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<TextNewsModel> mList;

    public TextNewsListAdapter(Context context, List<TextNewsModel> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_text_news, parent, false);
            holder = new ViewHolder();
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final TextNewsModel model = mList.get(position);
        holder.tvDate.setText(model.getDate());
        holder.tvTitle.setText(model.getTitle());
        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle, tvDate;
    }
}
