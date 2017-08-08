package com.example.luyan.smartmenu_process.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luyan.smartmenu_process.MetaData.DESKITEM;
import com.example.luyan.smartmenu_process.MetaData.ORDERITEM;
import com.example.luyan.smartmenu_process.R;

import java.util.ArrayList;

/**
 * Created by luyan on 07/06/2017.
 */

public class DeskAdapter extends BaseAdapter {

    private ArrayList<ORDERITEM> orderitems;
    private LayoutInflater mInflater;

    @Override
    public int getCount() {
        return orderitems.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.desk_grid_item, null);
            holder = new DeskAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.mText = (TextView) convertView.findViewById(R.id.m_text);
            holder.mTopLine = (ImageView) convertView.findViewById(R.id.top_line);
            holder.mBadge = (ImageView) convertView.findViewById(R.id.badge);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        if (position > 3){
            holder.mTopLine.setVisibility(View.GONE);
        }
        if (orderitems.get(position).getIsRead()){
            holder.mBadge.setVisibility(View.GONE);
        } else {
            holder.mBadge.setVisibility(View.VISIBLE);
            holder.mBadge.setImageResource(R.drawable.badge_new_icon);
        }
        holder.mText.setText(orderitems.get(position).getDeskNum());
        return convertView;
    }

    public DeskAdapter(Context mContext, ArrayList<ORDERITEM> orderitems){
        this.mInflater = LayoutInflater.from(mContext);
        this.orderitems = orderitems;
    }
    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView mText;
        public ImageView mTopLine;
        public ImageView mBadge;
    }
}
