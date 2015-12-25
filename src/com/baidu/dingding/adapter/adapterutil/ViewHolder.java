package com.baidu.dingding.adapter.adapterutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.until.BitmapUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by Administrator on 2015/11/16. ViewHolder类
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public View getConvertView() {
        return mConvertView;
    }

    public int getmPosition() {
        return mPosition;
    }


    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
    }

    /**
     * 暴露给外面的方法
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (null == convertView) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 获取viewid
     */
    public <T extends View> T getView(int id) {
        View childView = mViews.get(id);
        if (childView == null) {
            childView = mConvertView.findViewById(id);
            mViews.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * TextView的text
     */
    public ViewHolder setTextView(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    /**
     * button的text
     */
    public ViewHolder setButtonText(int viewid, String text) {
        Button btn = getView(viewid);
        btn.setText(text);
        return this;
    }
    public ViewHolder setTagFlowLayout(int viewid,List<?> list){
        TagFlowLayout tagFlowLayout=getView(viewid);
        tagFlowLayout.setAdapter(new TagAdapter(list) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {

                return null;
            }
        });
        return this;
    }
    /**
     * EditText的text
     */
    public ViewHolder setEditText(int viewid, String text) {
        EditText edtext = getView(viewid);
        edtext.setText(text);
        return this;
    }

    /**
     * NetworkImageView错误图片
     */
    public ViewHolder seterrorNetworkImageView(int viewid, int id) {
        NetworkImageView networkImageViewerror = getView(viewid);
        networkImageViewerror.setErrorImageResId(id);
        return this ;
    }
    /**
     * NetworkImageView默认图片
     */
    public ViewHolder setdefaNetworkImageView(int viewid, int id) {
        NetworkImageView networkImageView = getView(viewid);
        networkImageView.setDefaultImageResId(id);
        return this;
    }
    /**
     * NetworkImageView
     */
    public ViewHolder setNetworkImageView(int viewid, String path,ImageLoader imageLoader) {
        NetworkImageView networkImageView = getView(viewid);
        networkImageView.setImageUrl(path, imageLoader);
        return this;
    }

    /**
     * imageview根据地址获取图片
     */
    public ViewHolder setImagViewURL(int viewid, String path) {
        ImageView imgview = getView(viewid);
        Bitmap bitmap = BitmapUtil.getBitmapFromUrl(path);
        imgview.setImageBitmap(BitmapUtil.compressImage(bitmap));
        return this;
    }
    /**
     * imageview静态图片
     */
    public ViewHolder setImagView(int viewid ) {
        ImageView imgview = getView(viewid);
        return this;
    }
    /**linearLayout*/
    public ViewHolder setLinearLayout(int viewid ){
        LinearLayout linearLayout=getView(viewid);
        return this;
    }
    /**给linearLayout动态添加childview*/
    public ViewHolder setLinearLayoutgetChild(int textname, LinearLayout linear) {
        final LinearLayout linearLayout=getView(textname);
        linearLayout.addView(linear);
        return this;
    }
}
