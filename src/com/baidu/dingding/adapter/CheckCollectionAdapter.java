package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.entity.CollectionEntity;
import com.baidu.dingding.until.Toasttool;

import java.util.List;

/**
 * Created by Administrator on 2015/12/2. 查看个人收藏
 */
public class CheckCollectionAdapter extends AbstractBaseAdapter{
    private Imgclickitemid imgclickitemid;
    public CheckCollectionAdapter(Context context, List beanList) {
        super(context, beanList);
    }
    public void setIsShowDelete(boolean isShowDelete){
        this.isShowDelete=isShowDelete;
        notifyDataSetChanged();
    }
    private boolean isShowDelete;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示
    ViewHolder viewHolder=null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CollectionEntity collectionEntity= (CollectionEntity) beanList.get(position);
        if(null==convertView||((ViewHolder) convertView.getTag()).flag != position){
            viewHolder=new ViewHolder();
            viewHolder.flag=position;
            convertView=mInflater.inflate(R.layout.geren_shopingcollection_item,null);
            viewHolder.name= (TextView) convertView.findViewById(R.id.sou_girdView_list_view_text_01);
            viewHolder.price= (TextView) convertView.findViewById(R.id.sou_girdView_list_view_text_02);
            viewHolder.networkImageView= (NetworkImageView) convertView.findViewById(R.id.sou_girdView_list_view_image_01);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.delete_markView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setVisibility(isShowDelete?View.VISIBLE:View.GONE);//设置删除按钮是否显示
        viewHolder.name.setText(collectionEntity.getGoodsName());
        viewHolder.price.setText("¥"+collectionEntity.getPrice());
        viewHolder.networkImageView.setErrorImageResId(R.drawable.ding_182);
        viewHolder.networkImageView.setDefaultImageResId(R.drawable.ding_182);
        viewHolder.networkImageView.setImageUrl(collectionEntity.getPicPath(), imageLoader);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imgclickitemid!=null){
                    imgclickitemid.getPostion(viewHolder.flag);
                }
              //  Toasttool.MyToast(context,""+position);
            }
        });
        return convertView;
    }
    public interface  Imgclickitemid{
         void getPostion(int postion);
    }
    public void addListener(Imgclickitemid imgclickitemid){
        this.imgclickitemid = imgclickitemid;
    }
    class ViewHolder {
        Integer flag;
        TextView name,price;
        NetworkImageView networkImageView;
        ImageView imageView;
    }
}
