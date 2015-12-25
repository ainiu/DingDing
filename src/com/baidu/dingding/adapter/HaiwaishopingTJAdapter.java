package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.HaiwaituijianBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11. 海外店铺推荐适配器
 */
public class HaiwaishopingTJAdapter extends AbstractBaseAdapter{
    public HaiwaishopingTJAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HaiwaituijianBean haiwaituijianBean_list= (HaiwaituijianBean) beanList.get(position);
        ViewHold viewHold=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.haiwai_shoping_girdview_item,null);
            viewHold=new ViewHold();
            viewHold.tag=position;
            viewHold.networkImageView= (NetworkImageView) convertView.findViewById(R.id.haiwai_girdView_list_view_image_01);
            viewHold.tvName= (TextView) convertView.findViewById(R.id.sou_girdView_list_view_text_01);
            viewHold.tvPirce= (TextView) convertView.findViewById(R.id.haiwai_dianputuijian_shoujia);
            viewHold.tvcategoryName= (TextView) convertView.findViewById(R.id.haiwai_dianputuijian_liebie);
            convertView.setTag(viewHold);
        }else{
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.networkImageView
                .setDefaultImageResId(R.drawable.wang_18);
        viewHold.networkImageView.setErrorImageResId(R.drawable.ding_42);
        viewHold.networkImageView.setImageUrl(haiwaituijianBean_list.getLogPath(),
                imageLoader);
        viewHold.tvPirce.setText("¥" + haiwaituijianBean_list.getPrimalPrice());
        viewHold.tvName.setText(haiwaituijianBean_list.getName());
        viewHold.tvcategoryName.setText(haiwaituijianBean_list.getCategoryName());
        return convertView;
    }

    class ViewHold {
        NetworkImageView networkImageView;
        TextView tvPirce,tvName,tvcategoryName;
        int tag;
    }
}
