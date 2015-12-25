package com.baidu.dingding.adapter.adapterutil;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.until.LogUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/14.
 */
public class GouwucheSelectAdapter extends MyCommonAdapter {
    public GouwucheSelectAdapter(List datas, int layoutId) {
        super(datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Object object) {
        GouwucheBean gouwucheBean = (GouwucheBean) mDatas.get(holder.getmPosition());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout linear = new LinearLayout(mContext);
        linear.setLayoutParams(lp);//设置布局参数
        linear.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为垂直方向布局
        try {
        Map<String, String> goodsSpecificationContactStr = (Map<String, String>) gouwucheBean.getGoodsSpecificationContactStr();
        LogUtil.i("有多少个子控件","goodsSpecificationContactStr="+goodsSpecificationContactStr.getClass().getName());
            LogUtil.i("有多少个子控件", "object=" + object.getClass().getName());
                int i = 0;
                LinearLayout.LayoutParams vlp=null;
                TextView textView=null;
                for(String key:goodsSpecificationContactStr.keySet()){
                    LogUtil.i("解析mapok","key="+key);
                    i++;
                    String value = goodsSpecificationContactStr.get(key);
                    LogUtil.i("解析mapok","value="+value);
                    vlp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView = new TextView(mContext);
                    textView.setText(key + ":" + value);
                    textView.setLayoutParams(vlp);
                    if (i > 0) textView.setPadding(10, 0, 0, 0);
                    linear.addView(textView);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        //holder.setLinearLayoutgetChild(R.id.Lenar,linear);

        //holder.setImagView(R.id.dianpulide);
        holder.setTextView(R.id.name, gouwucheBean.getShopName());
      //  holder.setImagView(R.id.yige);
        holder.setNetworkImageView(R.id.networkImageView, gouwucheBean.getPicPath(), imageLoader);
        holder.setdefaNetworkImageView(R.id.networkImageView, R.drawable.ding_184);
        holder.setTextView(R.id.textname, gouwucheBean.getGoodsName());
        holder.setTextView(R.id.jiage, "¥" + gouwucheBean.getPrice());
        holder.setTextView(R.id.count,"X"+gouwucheBean.getGoodsCount());
       // holder.set
    }
}
