package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.EachBaen;
import com.baidu.dingding.until.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class FenleiAdapterThree extends  AbstractBaseAdapter{


    public FenleiAdapterThree(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EachBaen fenlei_eachBaen = (EachBaen) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.skin_list, null);
            viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.skin_list_image_01);
            viewHolder.name = (TextView) convertView.findViewById(R.id.skin_list_text_01);
            viewHolder.jiaqian= (TextView) convertView.findViewById(R.id.skin_list_text_04);
                    viewHolder.dizhi= (TextView) convertView.findViewById(R.id.skin_list_text_02);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setImageUrl(fenlei_eachBaen.getLogPath(),
                imageLoader);

        viewHolder.name.setText(fenlei_eachBaen.getName());
        LogUtil.i("分类适配器", "name=" + fenlei_eachBaen.getName());
        viewHolder.jiaqian.setText("¥" + fenlei_eachBaen.getPrimalPrice());
        viewHolder.dizhi.setText(fenlei_eachBaen.getOriginPlace());
        return convertView;
    }

    class ViewHolder {
        TextView name ,jiaqian,dizhi;
        NetworkImageView networkImageView;
    }
}
