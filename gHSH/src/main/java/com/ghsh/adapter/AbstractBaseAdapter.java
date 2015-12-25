
package com.ghsh.adapter;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AbstractBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> list;

    public AbstractBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return this.list;
    }

    public void add(T object) {
        this.list.add(object);
    }

    public void addAllList(Collection<? extends T> collection) {
        this.list.addAll(collection);
        this.notifyDataSetChanged();
    }

    public void clearAllList() {
        this.list.clear();
        this.notifyDataSetChanged();
    }
}
