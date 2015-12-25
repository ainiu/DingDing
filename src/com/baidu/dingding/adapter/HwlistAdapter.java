package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.HwlistBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/5.
 */
public class HwlistAdapter extends AbstractBaseAdapter {
    public HwlistAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       HwlistBean list= (HwlistBean) beanList.get(position);
        ViewHold viewHold=null;
        if(convertView==null){
            viewHold=new ViewHold();
            convertView=mInflater.inflate(R.layout.haiwai_fragment_list_view,null);
            viewHold.tvText= (TextView) convertView.findViewById(R.id.haiwai_fragment_list_text_01);
            viewHold.tvPirce= (TextView) convertView.findViewById(R.id.haiwai_fragment_list_text_04);
            viewHold.networkImageView= (NetworkImageView) convertView.findViewById(R.id.haiwai_fragment_list_image_01);
            convertView.setTag(viewHold);
        }
        else{
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.networkImageView.setImageUrl(list.getLogPath(),imageLoader);
        viewHold.tvPirce.setText("¥"+list.getPrimalPrice());
        viewHold.tvText.setText(list.getGoodsName());
        return convertView;
    }
    class ViewHold {
        NetworkImageView networkImageView;
        TextView tvPirce,tvText;
    }
}
