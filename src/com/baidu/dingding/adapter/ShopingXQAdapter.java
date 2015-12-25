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
 * Created by Administrator on 2015/12/10.
 */
public class ShopingXQAdapter extends AbstractBaseAdapter  {
    boolean isShowDelete=true;
    public ShopingXQAdapter(Context context, List beanList) {
        super(context, beanList);
    }
    public void setIsShowDelete(boolean isShowDelete){
        this.isShowDelete=isShowDelete;
        //notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EachBaen eachBaen = (EachBaen) beanList.get(position);
        LogUtil.i("店铺适配器设置值", "isShowDelete="+isShowDelete);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            viewHolder.flag=position;
            if(isShowDelete){
                convertView = mInflater
                        .inflate(R.layout.dianpu__listorgrid_item, null);
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.name);
                viewHolder.networkImageView = (NetworkImageView) convertView
                        .findViewById(R.id.soushuo_img);
                viewHolder.primalPrice = (TextView) convertView
                        .findViewById(R.id.soushuo_primalPrice);
                viewHolder.originPlace = (TextView) convertView
                        .findViewById(R.id.soushuo_originPlace);
                convertView.setTag(viewHolder);
            }else{
                convertView=mInflater.inflate(R.layout.dianpu__grid_item,null);
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.grid_name);
                viewHolder.networkImageView = (NetworkImageView) convertView
                        .findViewById(R.id.grid_img);
                viewHolder.primalPrice = (TextView) convertView
                        .findViewById(R.id.grid_primalPrice);
                viewHolder.originPlace = (TextView) convertView
                        .findViewById(R.id.grid_originPlace);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.wang_18);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_42);
        viewHolder.networkImageView.setImageUrl(eachBaen.getLogPath(),
                imageLoader);
        /*viewHolder.webView.loadDataWithBaseURL(null, eachBaen.getName(),
                "text/html", "UTF-8", null);*/
        viewHolder.name.setText(eachBaen.getName());
        viewHolder.primalPrice.setText("￥"+eachBaen.getPrimalPrice());
        viewHolder.originPlace.setText(eachBaen.getOriginPlace());
        return convertView;
    }

    class ViewHolder {
        TextView primalPrice, originPlace,name;
        NetworkImageView networkImageView;
        int flag;
    }
}
