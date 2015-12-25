package com.baidu.dingding.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.baidu.dingding.R;

/**
 * Created by Administrator on 2015/11/28.
 */
public abstract class BaseTypeAdapter extends ABaseAdapter{

    protected BaseTypeAdapter(AbsListView listView) {
        super(listView);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTypeRender typeRender;
        if (null == convertView) {
            typeRender = getAdapterTypeRender(position);
            convertView = typeRender.getConvertView();
            convertView.setTag(R.id.ab__id_adapter_item_type_render, typeRender);
            typeRender.fitEvents();
        } else {
            typeRender = (AdapterTypeRender) convertView.getTag(R.id.ab__id_adapter_item_type_render);
        }
        convertView.setTag(R.id.ab__id_adapter_item_position, position);

        if (null != typeRender) {
            typeRender.fitDatas(position);
        }

        return convertView;
    }

    /**
     * 根据指定position的item获取对应的type，然后通过type实例化一个AdapterTypeRender的实现
     *
     * @param position
     * @return
     */
    public abstract AdapterTypeRender getAdapterTypeRender(int position);
}
