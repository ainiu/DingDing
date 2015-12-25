package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.LogUtil;

import java.util.List;

public class DianpuAdapter extends AbstractBaseAdapter {

    public DianpuAdapter(Context context, List<FenLei> beanList) {

        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FenLei fenlei = (FenLei) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if(fenlei.getParentId().equals("0")) {
                convertView = mInflater.inflate(R.layout.fenlei_list, null);

            }else{
                convertView = mInflater.inflate(R.layout.fenlei__item_gridview_two, null);
            }
            viewHolder.tag=position;
            viewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.fenlei_grid_image);
            viewHolder.name= (TextView) convertView.findViewById(R.id.fenlei_grid_text_name);
            viewHolder.textbiaoti= (TextView) convertView.findViewById(R.id.fenlei_list_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setImageUrl(fenlei.getLogPath(),
                imageLoader);
        viewHolder.name.setText(fenlei.getName());
        LogUtil.i("分类适配器","name="+fenlei.getName());
        viewHolder.textbiaoti.setText("冰点降价");

        return convertView;
    }

    class ViewHolder {
        TextView name, textbiaoti;
        NetworkImageView networkImageView;
        int tag;
    }
}

