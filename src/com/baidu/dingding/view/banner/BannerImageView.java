package com.baidu.dingding.view.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.entity.TPic;
import com.baidu.dingding.R;
import java.util.ArrayList;
import java.util.List;
/**
 * 显示图片的Banner
 * */
public class BannerImageView extends AbstractBannerView {

    private List<TPic> picList;
    private BannerImageView.Listener listener;
    public BannerImageView(Context context) {
        super(context);
    }

    public BannerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getIndicatorUnfocusedIcon() {
        return R.drawable.dot_normal;
    }

    @Override
    protected int getIndicatorFocusedIcon() {
        return R.drawable.dot_focused;
    }

    public void createPicView(List<TPic> picList) {
        this.picList=picList;
        if (picList == null || picList.size() == 0) {
            return;
        }
        List<View> viewList=new ArrayList<View>();
        for (int i = 0; i < picList.size(); i++) {
            final TPic bean = picList.get(i);
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            if(bean.getImageRes()!=0){
                imageView.setImageResource(bean.getImageRes());
            }else{
                //加载图片
                DVolley.getImage(bean.getImageUrl(), imageView, R.drawable.default_image);
//                bean.getImageUrl();
            }
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.OnClickPicView(bean);
                    }
                }
            });
            viewList.add(imageView);
        }
        super.createView(viewList);
    }

    public void addListener(BannerImageView.Listener listener){
        this.listener=listener;
    }
    public static interface Listener{
        public void OnClickPicView(TPic pic);
    }
    public List<TPic> getPicList() {
        return picList;
    }
}
