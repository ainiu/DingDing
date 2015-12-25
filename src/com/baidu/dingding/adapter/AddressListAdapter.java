package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.entity.AddressEntity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3.收货地址适配器
 */
public class AddressListAdapter extends AbstractBaseAdapter{

    private Imgclickitemid imgclickitemid;
    public AddressListAdapter(Context context, List beanList) {
        super(context, beanList);
    }
    ViewHolder viewHolder=null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         AddressEntity addressEntity = (AddressEntity) beanList.get(position);
        if(null==convertView||((ViewHolder) convertView.getTag()).flag != position){
            viewHolder=new ViewHolder();
            viewHolder.flag=position;
            convertView=mInflater.inflate(R.layout.shouhuo_list_item,null);
            viewHolder.name= (TextView) convertView.findViewById(R.id.shouhuo_name);
            viewHolder.phone= (TextView) convertView.findViewById(R.id.shouhuo_phone);
            viewHolder.address= (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageView1);
            viewHolder.default_imgView= (ImageView) convertView.findViewById(R.id.shouhuo_list_image_02);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(addressEntity.getReceiveName());
        viewHolder.phone.setText(addressEntity.getReceiveMobile());
        viewHolder.address.setText(addressEntity.getProvince_city_area() + addressEntity.getReceiveAddress());

        if(addressEntity.getIsDefault().equals("1")){
            viewHolder.default_imgView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.default_imgView.setVisibility(View.INVISIBLE);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgclickitemid != null) {
                    imgclickitemid.getPostion(position);
                }
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
        TextView name,phone,address;
        ImageView imageView,default_imgView;
    }

}
