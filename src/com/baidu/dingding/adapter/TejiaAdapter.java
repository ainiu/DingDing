package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.XianShiEntity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class TejiaAdapter extends AbstractBaseAdapter {
    public TejiaAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        XianShiEntity tejia_list = (XianShiEntity) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.sou_girdview_list_01, null);
            viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.sou_girdview_network);
            viewHolder.name = (TextView) convertView.findViewById(R.id.sou_girdview_list_text_01);
            viewHolder.jiage = (TextView) convertView.findViewById(R.id.sou_girdview_list_text_02);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.wang_18);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_42);
        viewHolder.networkImageView.setImageUrl(tejia_list.getLogPath(), imageLoader);
        viewHolder.name.setText(tejia_list.getName());
        viewHolder.jiage.setText("¥"+tejia_list.getPrimalPrice());
        return convertView;
    }

    class ViewHolder {
        TextView name, jiage;
        NetworkImageView networkImageView;
    }
}
