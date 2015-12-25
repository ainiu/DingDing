package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/10/31.
 */
public class FenleiAdapterTwo extends AbstractBaseAdapter {
    public FenleiAdapterTwo(Context context, List beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FenLei fenlei = (FenLei) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fenlei__item_gridview_two, null);
            viewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.fenlei_grid_image);
            viewHolder.name= (TextView) convertView.findViewById(R.id.fenlei_grid_text_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.networkImageView
                .setDefaultImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_106);
        viewHolder.networkImageView.setImageUrl(fenlei.getLogPath(),
                imageLoader);

        viewHolder.name.setText(fenlei.getName());
        LogUtil.i("分类适配器", "name=" + fenlei.getName());
        return convertView;
    }

    class ViewHolder {
        TextView name;
        NetworkImageView networkImageView;
    }
}
