package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.Dianpu;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class DianAdapter extends AbstractBaseAdapter  {
    public DianAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dianpu dianpu = (Dianpu) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.fenlei_dianfu,null);
            viewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.fenlei_dianfu_image_01);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.fenlei_dianfu_text_02);
            viewHolder.tv_dianzhi= (TextView) convertView.findViewById(R.id.fenlei_dianfu_text_06);
            viewHolder.tv_maijia= (TextView) convertView.findViewById(R.id.fenlei_dianfu_text_04);
            viewHolder.tv_authenticatio= (TextView) convertView.findViewById(R.id.fenlei_dianfu_text_08);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setImageUrl(dianpu.getLogPath(),
                imageLoader);
        viewHolder.tv_name.setText(dianpu.getShopName());
        viewHolder.tv_maijia.setText(dianpu.getUsrNumber());
        viewHolder.tv_dianzhi.setText(dianpu.getCountry());
        viewHolder.tv_authenticatio.setText(dianpu.getAuthentication());
        return convertView;
    }
    class ViewHolder{
        TextView tv_name,tv_maijia,tv_dianzhi,tv_authenticatio;
        NetworkImageView  networkImageView;
    }
}
