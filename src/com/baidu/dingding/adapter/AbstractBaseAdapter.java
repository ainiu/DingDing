package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.biz.BitmapCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context context;
    protected List<T> beanList = new ArrayList<T>();

    protected RequestQueue mQueue = TApplication.getInstance().getRequestQueue();

   protected ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

    public AbstractBaseAdapter(Context context, List<T> beanList) {
        super();
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public T getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    public void addAllList(Collection<? extends T> collection) {
        this.beanList.addAll(collection);
        this.notifyDataSetChanged();
    }

    public void clearAllList() {
        this.beanList.clear();
        this.notifyDataSetChanged();
    }

}
