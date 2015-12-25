package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.FenLei;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class YcdAdapter extends AbstractBaseAdapter{


    public YcdAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FenLei dianpu = (FenLei) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.haiwai_ycd_itm,null);
            viewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.fenlei_grid_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.networkImageView.setDefaultImageResId(R.drawable.ding_102);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_102);
        viewHolder.networkImageView.setImageUrl(dianpu.getLogPath(),
                imageLoader);
        return convertView;
    }
    class ViewHolder{
        NetworkImageView networkImageView;
    }
}

