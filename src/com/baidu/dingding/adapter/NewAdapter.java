package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.NewGoods;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class NewAdapter extends AbstractBaseAdapter<NewGoods> {


    public NewAdapter(Context context, List<NewGoods> beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        NewGoods newGoods = beanList.get(position);
        ViewHold hold = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sou_girdview_list_02, null);
            hold = new ViewHold();
            hold.tvText = (TextView) convertView.findViewById(R.id.sou_girdView_list_view_text_01);
            hold.tvPirce = (TextView) convertView.findViewById(R.id.sou_girdView_list_view_text_02);
            hold.networkImageView = (NetworkImageView) convertView.findViewById(R.id.sou_girdView_list_view_image_01);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        hold.networkImageView
                .setDefaultImageResId(R.drawable.wang_18);
        hold.networkImageView.setErrorImageResId(R.drawable.ding_42);
        hold.networkImageView.setImageUrl(newGoods.getLogPath(),
                imageLoader);
        hold.tvPirce.setText("¥" + newGoods.getPrimalPrice());
        hold.tvText.setText(newGoods.getName());
        return convertView;
    }

    class ViewHold {
        NetworkImageView networkImageView;
        TextView tvPirce,tvText;
    }
}
