package com.baidu.dingding.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.entity.Shoping.GouwubeanToQueren;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class QuerenAdapter extends AbstractBaseAdapter {

    public QuerenAdapter(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GouwubeanToQueren gouwubean = (GouwubeanToQueren) beanList.get(position);
        final ShopingViewHolder shopingViewHolder ;
        if (convertView == null || ((ShopingViewHolder) convertView.getTag()).flag != position) {
            convertView = mInflater.inflate(R.layout.que_ren_list, null);
            shopingViewHolder = new ShopingViewHolder();
            shopingViewHolder.tv_name = (TextView) convertView.findViewById(R.id.que_ren_name);
            shopingViewHolder.tv_maijiadindinghao = (TextView) convertView.findViewById(R.id.que_ren_maijia);
            shopingViewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.que_ren_list_image_09);
            shopingViewHolder.biaoti = (TextView) convertView.findViewById(R.id.que_ren_title);
            shopingViewHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
            shopingViewHolder.tv_price = (TextView) convertView.findViewById(R.id.que_ren_price);
            shopingViewHolder.tv_count = (TextView) convertView.findViewById(R.id.que_ren_count);
            convertView.setTag(shopingViewHolder);
        } else {
            shopingViewHolder = (ShopingViewHolder) convertView.getTag();
        }

        String listStirng = gouwubean.getGoodsSpecificationContactStr();
        List<String> abcList = new ArrayList<String>();
        if(!TextUtils.isEmpty(listStirng)){
            String[] array = listStirng.split(",");
            for (String str : array)
            {
                abcList.add(str);
            }
        }
            shopingViewHolder.tagFlowLayout.setAdapter(new TagAdapter(abcList) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    LayoutInflater inflater = LayoutInflater.from(TApplication.getContext());
                    TextView tv = (TextView) inflater.inflate(R.layout.gouwutv,
                            shopingViewHolder.tagFlowLayout, false);
                    tv.setText(o.toString());
                    return tv;
                }
            });
            shopingViewHolder.tv_price.setText(context.getResources().getString(R.string.shoppingcart_renmingbin) + gouwubean.getPrice());
            shopingViewHolder.tv_count.setText(context.getResources().getString(R.string.shoppingcart_count)+ gouwubean.getGoodsCount());
            shopingViewHolder.tv_name.setText(gouwubean.getShopName());
            shopingViewHolder.tv_maijiadindinghao.setText(context.getResources().getString(R.string.shoppingcart_maijia) + gouwubean.getSellerNumber());
            shopingViewHolder.networkImageView.setImageUrl(gouwubean.getPicPath(), imageLoader);
            shopingViewHolder.biaoti.setText(gouwubean.getGoodsName());
        return convertView;
    }

    public class ShopingViewHolder {
        TextView tv_name, tv_maijiadindinghao, biaoti, tv_price, tv_count;
        NetworkImageView networkImageView;
        TagFlowLayout tagFlowLayout;
        int flag;
    }
}