package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.DingdanEntity;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.MyView.FlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/25. 数据适配
 */
public class MydingAllAdapter extends AbstractBaseAdapter<DingdanEntity> {

    public MydingAllAdapter(Context context, List<DingdanEntity> beanList) {
        super(context, beanList);
    }

    List list;
    LinearLayout goodsSpecificationContactStr;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LogUtil.i("position", "position=" + position);
        //DingdanEntity dingdanEntity = (DingdanEntity) beanList.get(position);
        list.clear();
        DingdanEntity dingdanEntity = beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null||((ViewHolder) convertView.getTag()).flag != position) {
            viewHolder = new ViewHolder();
            viewHolder.flag=position;
            convertView = mInflater.inflate(R.layout.geren_dingdanall_item, null);
            viewHolder.shopName = (TextView) convertView.findViewById(R.id.dingdanall_dizhi);
            viewHolder.sellerNumber = (TextView) convertView.findViewById(R.id.dingdanall_haomaduoshao);
            viewHolder.orderStatus = (TextView) convertView.findViewById(R.id.dingdanall_qianshou);
            viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.networkImageView);
            viewHolder.name = (TextView) convertView.findViewById(R.id.dingdanall_name);
            goodsSpecificationContactStr = (LinearLayout) convertView.findViewById(R.id.linear3);
            viewHolder.price = (TextView) convertView.findViewById(R.id.dingdanall_jiaqian);
            viewHolder.goodsCount = (TextView) convertView.findViewById(R.id.dingdanall_shuliang);
            viewHolder.goodsCoun = (TextView) convertView.findViewById(R.id.dingdanall_shopingcount);
            viewHolder.dingdanall_moneycunt = (TextView) convertView.findViewById(R.id.dingdanall_moneycunt);
            viewHolder.yunfei = (TextView) convertView.findViewById(R.id.dingdanall_yunfei);
            viewHolder.yancang = (RadioButton) convertView.findViewById(R.id.radio_yanchangshouhuo);
            viewHolder.chakanwuliu = (RadioButton) convertView.findViewById(R.id.radio_cahkanwuliu);
            viewHolder.querenshouhuo = (RadioButton) convertView.findViewById(R.id.radio_querenshouhuo);
            viewHolder.radio_tixingfahuo = (RadioButton) convertView.findViewById(R.id.radio_tixingfahuo);
            viewHolder.zhifu= (RadioButton) convertView.findViewById(R.id.radio_zhifu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.shopName.setText(dingdanEntity.getShopName());
        viewHolder.sellerNumber.setText(dingdanEntity.getSellerNumber());
        try {
            viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_182);
            viewHolder.networkImageView.setDefaultImageResId(R.drawable.ding_182);
            viewHolder.networkImageView.setImageUrl(dingdanEntity.getGoodsVOs().get(0).getPicPath(), imageLoader);
            viewHolder.name.setText(dingdanEntity.getGoodsVOs().get(0).getName());
            viewHolder.price.setText(dingdanEntity.getGoodsVOs().get(0).getPrice());
            viewHolder.goodsCount.setText("X" + dingdanEntity.getGoodsVOs().get(0).getGoodsCount());
            viewHolder.goodsCoun.setText("共" + dingdanEntity.getGoodsVOs().get(0).getGoodsCount() + "件商品");
            viewHolder.dingdanall_moneycunt.setText("合计:￥" + dingdanEntity.getGoodsVOs().get(0).getTotalPrice());
            viewHolder.yunfei.setText("(含运费￥" + dingdanEntity.getFreight() + ")");
            viewCount(dingdanEntity);
        } catch (Exception e) {
            LogUtil.i("出错了", "");
            e.printStackTrace();
            ExceptionUtil.handleException(e);
        }

        switch (Integer.parseInt(dingdanEntity.getOrderStatus())) {
            case 0:
                viewHolder.orderStatus.setText("已退单");
                viewHolder.yancang.setVisibility(View.GONE);
                viewHolder.chakanwuliu.setVisibility(View.GONE);
                viewHolder.querenshouhuo.setVisibility(View.GONE);
                viewHolder.radio_tixingfahuo.setVisibility(View.GONE);
                viewHolder.zhifu.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.orderStatus.setText("待付款");
                viewHolder.chakanwuliu.setVisibility(View.GONE);
                viewHolder.yancang.setVisibility(View.GONE);
                viewHolder.chakanwuliu.setVisibility(View.VISIBLE);
                viewHolder.querenshouhuo.setVisibility(View.VISIBLE);
                viewHolder.radio_tixingfahuo.setVisibility(View.GONE);
                viewHolder.zhifu.setVisibility(View.VISIBLE);
                break;
            case 2:
                viewHolder.orderStatus.setText("已付款");
                viewHolder.yancang.setVisibility(View.GONE);
                viewHolder.chakanwuliu.setVisibility(View.GONE);
                viewHolder.querenshouhuo.setVisibility(View.GONE);
                viewHolder.radio_tixingfahuo.setVisibility(View.VISIBLE);
                viewHolder.zhifu.setVisibility(View.GONE);
                break;
            case 3:
                viewHolder.orderStatus.setText("已发货");
                viewHolder.yancang.setVisibility(View.VISIBLE);
                viewHolder.chakanwuliu.setVisibility(View.VISIBLE);
                viewHolder.querenshouhuo.setVisibility(View.GONE);
                viewHolder.radio_tixingfahuo.setVisibility(View.GONE);
                viewHolder.zhifu.setVisibility(View.GONE);
                break;
            case 4:
                viewHolder.orderStatus.setText("已到货");
                viewHolder.yancang.setVisibility(View.GONE);
                viewHolder.chakanwuliu.setVisibility(View.VISIBLE);
                viewHolder.querenshouhuo.setVisibility(View.VISIBLE);
                viewHolder.radio_tixingfahuo.setVisibility(View.GONE);
                viewHolder.zhifu.setVisibility(View.GONE);
                break;
            case 5:
                viewHolder.orderStatus.setText("全部");
                viewHolder.yancang.setVisibility(View.GONE);
                viewHolder.chakanwuliu.setVisibility(View.GONE);
                viewHolder.querenshouhuo.setVisibility(View.GONE);
                viewHolder.radio_tixingfahuo.setVisibility(View.GONE);
                viewHolder.zhifu.setVisibility(View.GONE);
                break;
        }

        viewHolder.yancang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.chakanwuliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.querenshouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.radio_tixingfahuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.zhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LogUtil.i("MydingAllAdapter", "ok");
        return convertView;
    }

    private void viewCount(DingdanEntity dingdanEntity) {
        if (dingdanEntity.getGoodsVOs().get(0).getGoodsSpecificationContactStr().toString() != null) {
            list = new ArrayList();
            Map<String, String> fenlei = (Map<String, String>) dingdanEntity.getGoodsVOs().get(0).getGoodsSpecificationContactStr();
            LogUtil.i("goodsSpecificationContactStr", "fenlei=" + fenlei.toString());
            Iterator it = fenlei.entrySet().iterator();
            int i=0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                  String key = entry.getKey().toString();
                // a[i] = key;
                list.add(key);

                LogUtil.i("goodsSpecificationContactStr", "list="+list.get(i));
                ++i;
            }
            //
            LinearLayout.LayoutParams LP_MM = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout.LayoutParams LP_TV = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout layout = null;
            TextView textView;
            int  index = 0;
            for (int j = 0; j < list.size(); j++) {
                layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(LP_MM);
                LP_TV.setMargins(0, 5, 0, 0);
                textView = new TextView(context);
                textView.setText(list.get(j) + ":" + fenlei.get(list.get(j)));
                LogUtil.i("goodsSpecificationContactStr", "j=" + j + "a[" + j + "]=" + list.get(j));
                // list.clear();
                textView.setLayoutParams(LP_TV);
                layout.addView(textView);
                goodsSpecificationContactStr.addView(layout, index);
                index++;
            }
            list.clear();
        } else {
            goodsSpecificationContactStr.setVisibility(View.GONE);
            list.clear();
        }
    }

    class ViewHolder {
        TextView shopName, sellerNumber, orderStatus, name, price, goodsCount, goodsCoun, dingdanall_moneycunt, yunfei, tv;
        //LinearLayout goodsSpecificationContactStr;
        NetworkImageView networkImageView;
        // LinearLayout linearLayout;
        //LinearLayout.LayoutParams params ,tv_params;
        int flag;
        FlowLayout ll;
        RadioButton yancang, chakanwuliu, querenshouhuo, radio_tixingfahuo,zhifu;

    }
}
