package com.ghsh.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DVolley;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

public class ShoppingCartListViewtAdapter extends AbstractBaseAdapter<TShoppingCart> {
	private boolean isEdit=false;
	private Listener listener;
	public ShoppingCartListViewtAdapter(Context context, List<TShoppingCart> list) {
		super(context, list);
	}

	@SuppressLint("NewApi")
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ShoppingCartHolder holder;
		final TShoppingCart shoppingCart = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_shoppingcart_listview_item, null);
			holder = new ShoppingCartHolder();
			holder.goodsNameView = (TextView) convertView.findViewById(R.id.goods_item_name);
			holder.goodsPriceView = (TextView) convertView.findViewById(R.id.goods_item_price);
			holder.goodsImageView=(ImageView) convertView.findViewById(R.id.goods_item_image);
			holder.goodsNumberLayout= (LinearLayout) convertView.findViewById(R.id.goods_item_number_layout);
			holder.goodsNumberView= (TextView) convertView.findViewById(R.id.goods_item_number);
			holder.numberLayout = (RelativeLayout) convertView.findViewById(R.id.number_layout);
			holder.addNumberButton = (ImageButton) convertView.findViewById(R.id.number_add_button);
			holder.minusNumberButton = (ImageButton) convertView.findViewById(R.id.number_minus_button);
			holder.numberInputView = (TextView) convertView.findViewById(R.id.number_input);
			holder.checkBox= (CheckBox) convertView.findViewById(R.id.goods_item_checkBox);
			holder.goodsAttrView= (TextView) convertView.findViewById(R.id.goods_item_attr);
			convertView.setTag(holder);
		} else {
			holder = (ShoppingCartHolder) convertView.getTag();
		}
		holder.goodsNameView.setText(shoppingCart.getGoodsName());
		holder.numberInputView.setText(shoppingCart.getGoodsNumber());
		holder.goodsNumberView.setText(shoppingCart.getGoodsNumber());
		holder.goodsPriceView.setText("￥"+shoppingCart.getGoodsPrice());
		holder.goodsAttrView.setText(shoppingCart.getGoodsAttr());
		DVolley.getImage(shoppingCart.getGoodsImage(),holder.goodsImageView,R.drawable.default_image);
		
		holder.checkBox.setChecked(shoppingCart.isSelected());
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shoppingCart.setSelected(holder.checkBox.isChecked());
				if(listener!=null){
					listener.changeCart(shoppingCart);
				}
			}
		});
		
		holder.addNumberButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int number=Integer.parseInt(holder.numberInputView.getText()+"")+1;
				if(number<=99){
					if(listener!=null){
						listener.modifyCartNumber(shoppingCart, "1");
					}
				}
			}
		});
		holder.minusNumberButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int number=Integer.parseInt(holder.numberInputView.getText()+"")-1;
				System.out.println(number);
				if(number>=0){
					if(listener!=null){
						if (number==0) {
							DAlertUtil.alertOKAndCel(context, R.string.shoppingcart_alter_delete_more, new android.content.DialogInterface.OnClickListener(){
								@Override
								public void onClick(DialogInterface dialog, int which) {
									listener.deleteCartNumber(position);
								}
							}).show();
						}else{
							listener.modifyCartNumber(shoppingCart, "-1");
						}
					}
				}
			}
		});
		if(isEdit){
			holder.goodsNumberLayout.setVisibility(View.GONE);
			holder.goodsAttrView.setVisibility(View.GONE);
			holder.numberLayout.setVisibility(View.VISIBLE);
		}else{
			holder.numberLayout.setVisibility(View.GONE);
			holder.goodsNumberLayout.setVisibility(View.VISIBLE);
			holder.goodsAttrView.setVisibility(View.VISIBLE);
		}
		return convertView;
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
		for(TShoppingCart cart:list){
			if(cart.getShoppingID().equals(shoppingID)){
				list.remove(cart);
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
			for(TShoppingCart cart:list){
				if(cart.getShoppingID().equals(id)){
					list.remove(cart);
					break;
				}
			}
		}
		this.notifyDataSetChanged();
	}
	/**计算价格*/
	public String totalPrice(){
		BigDecimal total=new BigDecimal("0.00").setScale(2);
		for(TShoppingCart shoppingCart:list){
			if(shoppingCart.isSelected()){
				total=total.add(new BigDecimal(shoppingCart.getGoodsNumber()).multiply(new BigDecimal(shoppingCart.getGoodsPrice())));
			}
		}
		return total+"";
	}
	/**全选*/
	public void selectAll(boolean isSelected){
		for(TShoppingCart shoppingCart:list){
			shoppingCart.setSelected(isSelected);
		}
		this.notifyDataSetChanged();
	}
	/**判断是否全选*/
	public boolean isAllSelect(){
		for(TShoppingCart shoppingCart:list){
			if(!shoppingCart.isSelected()){
				return false;
			}
		}
		return true;
	}
	/**获取已选商品数量*/
	public int getSelectShoopingNumber(){
		int number=0;
		for(TShoppingCart shoppingCart:list){
			if(shoppingCart.isSelected()){
				number=number+1;
			}
		}
		return number;
	}
	/**获取所有商品数量*/
	public int getAllGoodsNumber(){
		int number=0;
		for(TShoppingCart shoppingCart:list){
			number=number+Integer.parseInt(shoppingCart.getGoodsNumber());
		}
		return number;
	}
	
	/**根据cartid修改数量*/
	public void modifyNumber(String shoppingID,String number){
		if(shoppingID==null){
			return;
		}
		for(TShoppingCart cart:list){
			if(cart.getShoppingID().equals(shoppingID)){
				cart.setGoodsNumber(number);
				this.notifyDataSetChanged();
				break;
			}
		}
	}
	
	/**获取已选择的购物车ID*/
	public List<String> getSelectCartIDs(){
		List<String> l=new ArrayList<String>();
		for(TShoppingCart shoppingCart:list){
			if(shoppingCart.isSelected()){
				l.add(shoppingCart.getShoppingID());
			}
		}
		return l;
	}
	/**获取所有的购物车ID*/
	public List<String> getAllCartIDs(){
		List<String> l=new ArrayList<String>();
		for(TShoppingCart shoppingCart:list){
			l.add(shoppingCart.getShoppingID());
		}
		return l;
	}
	
	public void addListener(Listener listener){
		this.listener=listener;
	}
	public interface Listener{
		//修改购物车数量
		public void modifyCartNumber(TShoppingCart shoppingCart,String number);
		public void changeCart(TShoppingCart shoppingCart);
		/**根据position去删除对应的item**/
		public void deleteCartNumber(int positon);
	}
	
	class ShoppingCartHolder {
		CheckBox checkBox;
		TextView goodsNameView;//商品名称
		TextView goodsPriceView;//商品价格
		ImageView goodsImageView;//商品图片
		TextView goodsNumberView;//商品数量
		LinearLayout goodsNumberLayout;//数量
		
		TextView goodsAttrView;
		
		RelativeLayout numberLayout;//数量layout
		ImageButton addNumberButton,minusNumberButton;//加减按钮
		TextView numberInputView;//数量输入框
	}
}
