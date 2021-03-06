package com.example.luyan.smartmenu_process.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.luyan.smartmenu_process.MetaData.CASEITEM;
import com.example.luyan.smartmenu_process.R;

import java.util.ArrayList;

/**
 * Created by luyan on 03/08/2017.
 */

public class OrderAdapter extends BaseAdapter {

    private ArrayList<CASEITEM> caseItems;
    private LayoutInflater mInflater;
    private Context context;
    private ButtonDelegate delegate;

    @Override
    public int getCount() {
        return caseItems.size();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final OrderAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_list_item, null);
            holder = new OrderAdapter.ViewHolder();
            /**得到各个控件的对象*/
            holder.caseName = (TextView) convertView.findViewById(R.id.case_name);
            holder.caseNum = (TextView) convertView.findViewById(R.id.case_num);
            holder.standardProperty = (TextView) convertView.findViewById(R.id.standard_property);
            holder.completeLine = (ImageView) convertView.findViewById(R.id.complete_line);
            holder.operateBtn = (TextView) convertView.findViewById(R.id.operate_btn);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (OrderAdapter.ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        holder.caseName.setText(caseItems.get(position).getCaseName());
        holder.caseNum.setText("×" + caseItems.get(position).getOrderNum());
        if (caseItems.get(position).getStandardDesc() != null) {
            holder.standardProperty.setVisibility(View.VISIBLE);
            holder.standardProperty.setText(caseItems.get(position).getStandardDesc());
        } else {
            holder.standardProperty.setVisibility(View.GONE);
        }

        if (caseItems.get(position).getCaseProgress() == 0) {
            holder.completeLine.setVisibility(View.GONE);
            holder.operateBtn.setVisibility(View.VISIBLE);
            holder.operateBtn.setText(context.getResources().getString(R.string.wine));
            holder.operateBtn.setBackground(context.getResources().getDrawable(R.drawable.button_radius_bg));
        }

        if (caseItems.get(position).getCaseProgress() == 1) {
            holder.completeLine.setVisibility(View.GONE);
            holder.operateBtn.setVisibility(View.VISIBLE);
            holder.operateBtn.setText(context.getResources().getString(R.string.complete));
            holder.operateBtn.setBackground(context.getResources().getDrawable(R.drawable.button_complete_radius_bg));
        }

        if (caseItems.get(position).getCaseProgress() == 2) {
            holder.completeLine.setVisibility(View.VISIBLE);
            holder.operateBtn.setVisibility(View.GONE);
        }

        holder.operateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delegate != null) {
                    if (holder.operateBtn.getText().toString().equals(context.getResources().getString(R.string.wine))){
                        delegate.tapOperateButton(position,1);
                    } else {
                        delegate.tapOperateButton(position,2);
                    }

                }
            }
        });

        return convertView;
    }

    public OrderAdapter(Context mContext, ArrayList<CASEITEM> caseItems) {
        this.mInflater = LayoutInflater.from(mContext);
        this.caseItems = caseItems;
        this.context = mContext;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView caseName;
        public TextView caseNum;
        public TextView standardProperty;
        public ImageView completeLine;
        public TextView operateBtn;
    }

    public ButtonDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ButtonDelegate delegate) {
        this.delegate = delegate;
    }

    public interface ButtonDelegate {
        public void tapOperateButton(int index, int progress);
    }
}
