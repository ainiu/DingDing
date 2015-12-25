package com.baidu.dingding.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.baidu.dingding.R;
import com.baidu.dingding.TApplication;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.until.DAlertUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.view.MyView.AddAndSubView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShopingSelectAdapter extends AbstractBaseAdapter<GouwucheBean> {
    private boolean isEdit=false;
    private Listener listener;

    public ShopingSelectAdapter(Context context, List<GouwucheBean> beanList) {
        super(context, beanList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GouwucheBean gouwucheBean = beanList.get(position);
        final MyViewHolder myViewHolder ;
        if (convertView == null || ((MyViewHolder) convertView.getTag()).flag != position) {
            myViewHolder = new MyViewHolder();
            myViewHolder.flag = position;
            convertView = mInflater.inflate(R.layout.gouwu_list_item, null);
            //myViewHolder.imageViewtitle = (CheckBox) convertView.findViewById(R.id.shopping_checkbox1);
            myViewHolder.tv_name = (TextView) convertView.findViewById(R.id.name);
            myViewHolder.imageViewcontent = (CheckBox) convertView.findViewById(R.id.shopping_checkbox2);
            myViewHolder.tv_tiltle = (TextView) convertView.findViewById(R.id.textname);
            myViewHolder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.TagFlowLayout);
            myViewHolder.tv_jiage = (TextView) convertView.findViewById(R.id.jiage);
            myViewHolder.tv_count = (TextView) convertView.findViewById(R.id.count);
            myViewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.networkImageView);
            myViewHolder.addAndSubView= (AddAndSubView) convertView.findViewById(R.id.addandsubview);
            myViewHolder.addAndSubView.setButtonLayoutParm(40, 40);
            myViewHolder.addAndSubView.setButtonBgResource(R.drawable.gouwujia, R.drawable.gouwujian);
            myViewHolder.addAndSubView.setEditTextResource(R.drawable.gouwu_zhong);
            myViewHolder.addAndSubView.setNum(Integer.parseInt(gouwucheBean.getGoodsCount()));
            myViewHolder.linearLayout= (LinearLayout) convertView.findViewById(R.id.linearLayoutdelete);
            myViewHolder.button = (Button) convertView.findViewById(R.id.btn_bianji);
            myViewHolder.delete= (Button) convertView.findViewById(R.id.button_delete);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        try {
        Map<String, String> goodsSpecificationContactStr = (Map<String, String>) gouwucheBean.getGoodsSpecificationContactStr();
            ArrayList<String> stringmaptolist = GsonTools.getMapList(goodsSpecificationContactStr);

        myViewHolder.tagFlowLayout.setAdapter(new TagAdapter(stringmaptolist) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater inflater = LayoutInflater.from(TApplication.getContext());

                TextView tv = (TextView) inflater.inflate(R.layout.gouwutv,
                        myViewHolder.tagFlowLayout, false);
                tv.setText(o.toString());
                return tv;
            }
        });
        }catch (Exception e){
            e.printStackTrace();
        }
       // myViewHolder.imageViewtitle.setChecked(gouwucheBean.getPid());
        myViewHolder.imageViewcontent.setChecked(gouwucheBean.getId());
        myViewHolder.networkImageView.setImageUrl(gouwucheBean.getPicPath(), imageLoader);
        myViewHolder.tv_tiltle.setText(gouwucheBean.getGoodsName());
        myViewHolder.tv_jiage.setText(context.getResources().getString(R.string.shoppingcart_renmingbin) + gouwucheBean.getPrice());
        myViewHolder.tv_count.setText(context.getResources().getString(R.string.shoppingcart_count) + gouwucheBean.getGoodsCount());
        myViewHolder.tv_name.setText(gouwucheBean.getShopName());
        //删除
        myViewHolder.delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        if(listener!=null){
                                DAlertUtil.alertOKAndCel(context, R.string.shoppingcart_alter_delete_more, new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listener.deleteCartNumber(position);
                                    }
                                }).show();
                            }else{
                                listener.modifyCartNumber(gouwucheBean, "-1");
                            }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //编辑
        myViewHolder.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index = !index;
                if (index) {
                    myViewHolder.button.setText(R.string.shoping_listview_item_wanchegn);
                    myViewHolder.addAndSubView.setVisibility(View.VISIBLE);
                    myViewHolder.linearLayout.setVisibility(View.VISIBLE);
                } else {
                    myViewHolder.button.setText(R.string.shoping_listview_item_bianji);
                    myViewHolder.addAndSubView.setVisibility(View.GONE);
                    myViewHolder.linearLayout.setVisibility(View.GONE);
                    int number=Integer.parseInt(gouwucheBean.getGoodsCount());
                    int num=myViewHolder.addAndSubView.getNum();
                    if(num!=number&&num>0){
                        if(listener!=null){
                            listener.modifyCartNumber(gouwucheBean,num+"");
                        }
                    }else{
                        if(listener!=null){
                            listener.modifyCartNumber(gouwucheBean,-1+"");
                        }
                    }
                }
            }
        });



        return convertView;
    }
    private boolean index=false;


    class MyViewHolder {
        CheckBox  imageViewcontent;  //是否选中
        TextView tv_name, tv_jiage, tv_tiltle, tv_count;
        TagFlowLayout tagFlowLayout;
        NetworkImageView networkImageView;
        Button button;
        int flag;
        AddAndSubView addAndSubView;
        LinearLayout linearLayout;
        Button delete;
    }


    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        this.notifyDataSetChanged();
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

    public void addListener(Listener listener){
        this.listener=listener;
    }
    public interface Listener{
        //修改购物车数量
         void modifyCartNumber(GouwucheBean shoppingCart,String number);
         void changeCart(GouwucheBean shoppingCart);
        /**根据position去删除对应的item**/
         void deleteCartNumber(int positon);
        //void  parseMaptoStirng(ArrayList<String> stringArrayList);
    }


}
