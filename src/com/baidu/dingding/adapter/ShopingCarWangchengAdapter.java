package com.baidu.dingding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.MyView.AddAndSubView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/21.
 */
public class ShopingCarWangchengAdapter extends  AbstractBaseAdapter<GouwucheBean> {

    public ShopingCarWangchengAdapter(Context context, List<GouwucheBean> beanList) {
        super(context, beanList);
    }
    private Listener shopingCarWangchengListener;

    public interface Listener{
        //修改购物车数量
        void modifyCartNumber(GouwucheBean shoppingCart,String number);
        void changeCart(GouwucheBean shoppingCart);
        /**根据position去删除对应的item**/
        void deleteCartNumber(int positon);
    }
    public void addListener(Listener listener){
        this.shopingCarWangchengListener=listener;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GouwucheBean gouwucheBean = beanList.get(position);
        final MyViewHolder myViewHolder;
        if (convertView == null || ((MyViewHolder) convertView.getTag()).flag != position) {
            myViewHolder = new MyViewHolder();
            myViewHolder.flag = position;
            convertView = mInflater.inflate(R.layout.gouwu_list_item, null);
           // myViewHolder.imageViewtitle = (CheckBox) convertView.findViewById(R.id.shopping_checkbox1);
            myViewHolder.tv_name = (TextView) convertView.findViewById(R.id.name);
            myViewHolder.imageViewcontent = (CheckBox) convertView.findViewById(R.id.shopping_checkbox2);
            myViewHolder.tv_tiltle = (TextView) convertView.findViewById(R.id.textname);
            myViewHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
            myViewHolder.bianji = (Button) convertView.findViewById(R.id.btn_bianji);
            myViewHolder.bianji.setVisibility(View.GONE);
            myViewHolder.tv_jiage = (TextView) convertView.findViewById(R.id.jiage);
            myViewHolder.tv_jiage.setVisibility(View.GONE);
            myViewHolder.tv_count = (TextView) convertView.findViewById(R.id.count);
            myViewHolder.tv_count.setVisibility(View.GONE);
            myViewHolder.view = convertView.findViewById(R.id.view);
            myViewHolder.view.setVisibility(View.GONE);
            myViewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.networkImageView);
            myViewHolder.addAndSubView = (AddAndSubView) convertView.findViewById(R.id.addandsubview);
            myViewHolder.addAndSubView.setVisibility(View.VISIBLE);
            myViewHolder.addAndSubView.setButtonLayoutParm(40, 40);
            myViewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            myViewHolder.imageView.setVisibility(View.VISIBLE);
            myViewHolder.addAndSubView.setButtonBgResource(R.drawable.gouwujia, R.drawable.gouwujian);
            myViewHolder.addAndSubView.setEditTextResource(R.drawable.gouwu_zhong);
            myViewHolder.addAndSubView.setNum(Integer.parseInt(gouwucheBean.getGoodsCount()));
            myViewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutdelete);
            myViewHolder.linearLayout.setVisibility(View.VISIBLE);
            myViewHolder.delete = (Button) convertView.findViewById(R.id.button_delete);
            myViewHolder.delete.setVisibility(View.GONE);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        List<String> stringList = new ArrayList<String>();

        try {
            Map<String, String> goodsSpecificationContactStr = (Map<String, String>) gouwucheBean.getGoodsSpecificationContactStr();
            LogUtil.i("有多少个子控件", "goodsSpecificationContactStr=" + goodsSpecificationContactStr.getClass().getName());
            for (String key : goodsSpecificationContactStr.keySet()) {
                LogUtil.i("解析mapok", "key=" + key);
                String value = goodsSpecificationContactStr.get(key);
                LogUtil.i("解析mapok", "value=" + value);
                String string = key + ":" + value;
                stringList.add(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


       // myViewHolder.imageViewtitle.setChecked(gouwucheBean.getPid());
        myViewHolder.imageViewcontent.setChecked(gouwucheBean.getId());
        myViewHolder.tagFlowLayout.setAdapter(new TagAdapter(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater inflater = LayoutInflater.from(TApplication.getContext());
                TextView tv = (TextView) inflater.inflate(R.layout.gouwutv,
                        myViewHolder.tagFlowLayout, false);
                tv.setText(o.toString());
                return tv;
            }
        });
        myViewHolder.networkImageView.setImageUrl(gouwucheBean.getPicPath(), imageLoader);
        myViewHolder.tv_tiltle.setText(gouwucheBean.getGoodsName());
        // myViewHolder.tv_jiage.setText("¥" + gouwucheBean.getPrice());
        // myViewHolder.tv_count.setText("X" + gouwucheBean.getGoodsCount());
        myViewHolder.tv_name.setText(gouwucheBean.getShopName());
        //选中店铺
        /*myViewHolder.imageViewtitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtil.i("imageViewtitle", "isChecked=" + isChecked);
                gouwucheBean.setPid(isChecked);
                gouwucheBean.setId(isChecked);
            }
        });*/
        /*myViewHolder.imageViewcontent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(shopingCarWangchengListener!=null){
                        shopingCarWangchengListener.deleteCartNumber(position);
                    }
                }
            }
        });*/
        return convertView;
    }

    public  class MyViewHolder {
        CheckBox imageViewtitle, imageViewcontent;
        TextView tv_name, tv_jiage, tv_tiltle, tv_count;
        TagFlowLayout tagFlowLayout;
        NetworkImageView networkImageView;
        int flag;
        AddAndSubView addAndSubView;
        LinearLayout linearLayout;
        ImageView imageView;
        Button delete, bianji;
        View view;
    }

    /**根据购物车ID删除*/
    public void removeCartByID(String shoppingID){
        if(shoppingID==null){
            return;
        }
        for(GouwucheBean cart:beanList){
            if(cart.getShoppingCarId().equals(shoppingID)){
                beanList.remove(cart);
                this.notifyDataSetChanged();
                break;
            }
        }
    }
    /**根据购物车ID删除*/
    public void removeCartByIDs(List<String> cards){
        if(cards==null||cards.size()==0){
            return;
        }
        for(String id:cards){
            for(GouwucheBean cart:beanList){
                if(cart.getShoppingCarId().equals(id)){
                    beanList.remove(cart);
                    break;
                }
            }
        }
        this.notifyDataSetChanged();
    }
    /**计算价格*/
    public String totalPrice(){
        BigDecimal total=new BigDecimal("0.00").setScale(2);
        for(GouwucheBean shoppingCart:beanList){
            if(shoppingCart.getId()){
                total=total.add(new BigDecimal(shoppingCart.getGoodsCount()).multiply(new BigDecimal(shoppingCart.getPrice())));
            }
        }
        return total+"";
    }
    /**全选*/
    public void selectAll(boolean isSelected){
        for(GouwucheBean shoppingCart:beanList){
            shoppingCart.setId(isSelected);
        }
        this.notifyDataSetChanged();
    }
    /**判断是否全选*/
    public boolean isAllSelect(){
        for(GouwucheBean  shoppingCart:beanList){
            if(!shoppingCart.getId()){
                return false;
            }
        }
        return true;
    }
    /**获取已选商品数量*/
    public int getSelectShoopingNumber(){
        int number=0;
        for(GouwucheBean shoppingCart:beanList){
            if(shoppingCart.getId()){
                number=number+1;
            }
        }
        return number;
    }
    /**获取所有商品数量*/
    public int getAllGoodsNumber(){
        int number=0;
        for(GouwucheBean shoppingCart:beanList){
            number=number+Integer.parseInt(shoppingCart.getGoodsCount());
        }
        return number;
    }

    /**根据cartid修改数量*/
    public void modifyNumber(String shoppingID,String number){
        if(shoppingID==null){
            return;
        }
        for(GouwucheBean cart:beanList){
            if(cart.getShoppingCarId().equals(shoppingID)){
                cart.setGoodsCount(number);
                this.notifyDataSetChanged();
                break;
            }
        }
    }

    /**获取已选择的购物车ID*/
    public List<String> getSelectCartIDs(){
        List<String> l=new ArrayList<String>();
        for(GouwucheBean shoppingCart:beanList){
            if(shoppingCart.getId()){
                l.add(shoppingCart.getShoppingCarId());
            }
        }
        return l;
    }
    /**获取所有的购物车ID*/
    public List<String> getAllCartIDs(){
        List<String> l=new ArrayList<String>();
        for(GouwucheBean shoppingCart:beanList){
            l.add(shoppingCart.getShoppingCarId());
        }
        return l;
    }
}
