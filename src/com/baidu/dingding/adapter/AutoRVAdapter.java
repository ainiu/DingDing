/*
package com.baidu.dingding.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.adapter.adapterutil.ViewHolder;
import com.baidu.dingding.biz.BitmapCache;
import com.baidu.dingding.until.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by Administrator on 2015/11/16. RecyclerView适配器抽象类
 *//*

public abstract class AutoRVAdapter extends RecyclerView.Adapter<RVHolder>{

    public List<?> list;
    private Context context;

    RequestQueue mQueue = TApplication.getInstance().getRequestQueue();

    ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

    public AutoRVAdapter(Context context, List<?> list) {
        this.list = list;
        this.context = context;
    }
    public List<?> getBeanList() {
        return list;
    }*/
/*//*


    public void setBeanList(List<?> List) {
        this.list = List;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(onCreateViewLayoutID(viewType), parent,false);
        RVHolder rvh = new RVHolder(view);
        return rvh;
    }

    public abstract int onCreateViewLayoutID(int viewType);


    @Override
    public void onViewRecycled(final RVHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, final int position) {

        onBindViewHolder(holder.getViewHolder(), position);
        //设置点击事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(null, v, holder.getPosition(), holder.getPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    onItemLongClickListener.onItemLongClick(null,v,holder.getPosition(),holder.getPosition());
                    return false;
                }
            });
        }



    }

    public abstract void onBindViewHolder(ViewHolder holder, int position);
    @Override
    public int getItemCount() {
        HashMap<String, Object> map = (HashMap<String, Object>) list.get(0);
        if(map instanceof Map){
            LogUtil.i("RecyclerView的数据是map类型","map="+map.toString());
            return map.size();
        }
        return list.size();
    }


    private AdapterView.OnItemClickListener onItemClickListener;                                    //设置点击事件
    private AdapterView.OnItemLongClickListener onItemLongClickListener;                            //设置长按点击事件

    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
*/
