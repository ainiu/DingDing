/*
package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.List;

*/
/**
Created by Administrator on 2015/12/12. *//*



public class ShopingAddAdapter extends AbstractBaseAdapter {
    public ShopingAddAdapter(Context context, List<HashMap<String, Object>> beanList) {
        super(context, beanList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> map = (HashMap<String, Object>) beanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null||((ViewHolder) convertView.getTag()).flag != position) {
            viewHolder = new ViewHolder();
            convertView=mInflater.inflate(R.layout.dialog_list_item,null);
            viewHolder.flag=position;
            viewHolder.name= (TextView) convertView.findViewById(R.id.addtitle);
           // viewHolder.tagFlowLayout= (TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        for (String  key : map.keySet()) {
            viewHolder.name.setText(key);
            List value = (List) map.get(key);

        }

        return convertView;
    }

      class ViewHolder {
        TextView name;

          TagFlowLayout tagFlowLayout;
        int flag;
    }
}
*/
