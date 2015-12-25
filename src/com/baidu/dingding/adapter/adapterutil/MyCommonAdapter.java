package com.baidu.dingding.adapter.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.biz.BitmapCache;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/12/13. 超级适配器
 */
public abstract class MyCommonAdapter<T> extends BaseAdapter {
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int mlayoutId;

    Context mContext=TApplication.getContext();
    RequestQueue mQueue = TApplication.getInstance().getRequestQueue();

    ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

    public MyCommonAdapter(List<T> datas, int layoutId) {
        super();
        this.mDatas = datas;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
//	@Override
//	public abstract View getView(int position, View convertView, ViewGroup parent);
    public List<T> getBeanList() {
        return mDatas;
    }

    public void setBeanList(List<T> beanList) {
        this.mDatas = beanList;
        this.notifyDataSetChanged();
    }


    public void addAllList(Collection<? extends T> collection) {
        this.mDatas.addAll(collection);
        this.notifyDataSetChanged();
    }

    public void clearAllList() {
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mlayoutId, position);

        convert(holder, getItem(position));

        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);
}
