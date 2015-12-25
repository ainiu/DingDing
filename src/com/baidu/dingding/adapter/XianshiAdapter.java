package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.XianShiEntity;
import com.baidu.dingding.view.XianShiActivity;

import java.util.List;

/**
 *   限时抢购listview适配器
 */
public class XianshiAdapter extends AbstractBaseAdapter<XianShiEntity> {


    public XianshiAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    //给控件赋值
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        XianShiEntity xianshi_list = beanList.get(position);
        if(xianshi_list==null){
            return  convertView;
        }
        ViewHold hold = null;
        if (convertView == null) {
            hold = new ViewHold();
            if (XianShiActivity.TAB) {
                convertView = mInflater.inflate(R.layout.each_dingding_xianshi_list, null);
                hold.networkImageView = (NetworkImageView) convertView.findViewById(R.id.xianshi_list_image_01);
                hold.shopName = (TextView) convertView.findViewById(R.id.xianshi_list_text_01);
                hold.xsPrice = (TextView) convertView.findViewById(R.id.xianshi_list_text_04);
                hold.productCategory = (TextView) convertView.findViewById(R.id.xianshi_list_text_02);
                hold.server_timer = (TextView) convertView.findViewById(R.id.xianshi_list_text_05);
                convertView.setTag(hold);
            } else {
                convertView = mInflater.inflate(R.layout.each_dingding_xianshi_grid, null);
                hold.networkImageView = (NetworkImageView) convertView.findViewById(R.id.xianshi_list_image_01);
                hold.shopName = (TextView) convertView.findViewById(R.id.xianshi_list_text_01);
                hold.xsPrice = (TextView) convertView.findViewById(R.id.xianshi_list_text_04);
                hold.productCategory = (TextView) convertView.findViewById(R.id.xianshi_list_text_02);
                hold.server_timer = (TextView) convertView.findViewById(R.id.xianshi_list_text_05);
                convertView.setTag(hold);
            }

        } else {
            hold = (ViewHold) convertView.getTag();
        }

        hold.networkImageView.setImageUrl(xianshi_list.getLogPath(), imageLoader);
        hold.shopName.setText(xianshi_list.getName());
        hold.xsPrice.setText(xianshi_list.getXsPrice());
        hold.productCategory.setText(xianshi_list.getProductCategory());
        String state = xianshi_list.getFlag();
        if ("Y".equalsIgnoreCase(state)) {
            hold.server_timer.setText("00:00");
        } else {
            hold.server_timer.setText("活动未开始");
        }
        return convertView;


    }

    class ViewHold {
        NetworkImageView networkImageView;
        TextView shopName, xsPrice, productCategory, server_timer;

    }
}
