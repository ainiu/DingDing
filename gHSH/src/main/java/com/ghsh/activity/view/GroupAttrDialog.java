package com.ghsh.activity.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ghsh.R;
import com.ghsh.code.bean.TGoodsAttr;
import com.ghsh.code.bean.TGoodsAttr.TGoodsAttrItem;
import com.ghsh.view.DToastView;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.view.util.ViewUtils.NumberListener;

/**
 * 团购属性选择框
 * */
public class GroupAttrDialog extends Dialog implements android.view.View.OnClickListener{
	private Activity activity;
	private ImageButton addNumberButton,minusNumberButton;
	private Button orderButton;//加入购物车，立即购买
//	private TGoods goods;
	private String goodsName,shopPrice,marketPrice;
	private List<TGoodsAttr> goodsAttrList;
	private TextView  goodsNameView,goodsShopPriceView,goodsMarketPriceView,numberInputView;
	private ImageView closeButton;
	private LinearLayout goodsAttrLayout;
	private Listener listener;
	private List<GoodsAttrDialogAdapter> adapterList=new ArrayList<GoodsAttrDialogAdapter>();
	
	public GroupAttrDialog(Activity activity) {
		super(activity, R.style.goods_attr_dialog_style);
		this.activity = activity;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_group_details_attr);
		Window window = this.getWindow();
		// window.setWindowAnimations(R.style.anim_up_down); // 添加动画
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		WindowManager m = activity.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
		window.setAttributes(p);
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		this.initView();
		this.initData();
		this.initListener();
	}

	private void initView() {
		goodsNameView=(TextView)this.findViewById(R.id.goods_name);
		goodsShopPriceView=(TextView)this.findViewById(R.id.goods_shop_price);
		goodsMarketPriceView=(TextView)this.findViewById(R.id.goods_market_price);
		goodsMarketPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		
		goodsAttrLayout=(LinearLayout)this.findViewById(R.id.goods_attr_layout);
		
		orderButton=(Button)this.findViewById(R.id.orderButton);
		closeButton=(ImageView)this.findViewById(R.id.goods_attr_close);
		
		addNumberButton=(ImageButton) this.findViewById(R.id.number_add_button);
		minusNumberButton=(ImageButton) this.findViewById(R.id.number_minus_button);
		numberInputView=(TextView) this.findViewById(R.id.number_input);
		
	}
	private void initListener() {
		orderButton.setOnClickListener(this);
		closeButton.setOnClickListener(this);
		ViewUtils.addNumberListener(addNumberButton, minusNumberButton, numberInputView,new NumberListener() {
			@Override
			public void modifyNumber(int number) {
				numberInputView.setText(number+"");
				listener.modifyNumber(number);
			}
		});
	}
	private void initData(){
		goodsNameView.setText(goodsName);
		goodsShopPriceView.setText("￥"+shopPrice);
		goodsMarketPriceView.setText("￥"+marketPrice);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		for(TGoodsAttr goodsAttr:goodsAttrList){
			String attrName=goodsAttr.getAttrName();
			if(goodsAttr.getAttrType()==1){
				attrName=attrName+"(单选)";
			}
			if(goodsAttr.getAttrType()==2){
				attrName=attrName+"(多选)";
			}
			
			View attrTypeLayout=activity.getLayoutInflater().inflate(R.layout.activity_goodsdetails_attr_type, null);
			TextView attrNameView=(TextView)attrTypeLayout.findViewById(R.id.goods_attr_name);
			attrNameView.setText(attrName);
			
			GridView attrGridView=(GridView)attrTypeLayout.findViewById(R.id.goods_attr_gridView);
			GoodsAttrDialogAdapter adapter=new GoodsAttrDialogAdapter(activity,goodsAttr.getAttrItemList());
			adapter.setGoodsAttr(goodsAttr);
			attrGridView.setAdapter(adapter);
			
			goodsAttrLayout.addView(attrTypeLayout, layoutParams);
			
			adapterList.add(adapter);
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v==orderButton){
			//立即购买
			listener.addOrder();
		}else if(v==closeButton){
			//关闭
			this.cancel();
		}
	}
	
	public void show(String number) {
		super.show();
		numberInputView.setText(number);
	}
	public boolean isCheck(){
		if(goodsAttrList==null){
			return true;
		}
		if(goodsAttrList.size()!=0){
			for(TGoodsAttr goodsAttr:goodsAttrList){
				if(goodsAttr.getAttrType()!=1){
					continue;
				}
				//单选
				boolean flag=false;
				for(TGoodsAttrItem goodsAttrItem:goodsAttr.getAttrItemList()){
					if(goodsAttrItem.isSelected()){
						flag=true;
					}
				}
				if(!flag){
					DToastView.makeText(activity,"请先选择，"+goodsAttr.getAttrName(), Toast.LENGTH_SHORT).show();
					return false;
				}
			}
		}
		return true;
	}
	
	public List<String> getSelectedIDs(){
		List<String> ids=new ArrayList<String>();
		if(goodsAttrList==null){
			return ids;
		}
		for(TGoodsAttr goodsAttr:goodsAttrList){
			for(TGoodsAttrItem goodsAttrItem:goodsAttr.getAttrItemList()){
				if(goodsAttrItem.isSelected()){
					ids.add(goodsAttrItem.goods_attr_id);
				}
			}
		}
		return ids;
	}
	
	public void setAttrInfo(String goodsName,String shopPrice,String marketPrice,List<TGoodsAttr> goodsAttrList) {
		this.goodsName=goodsName;
		this.shopPrice=shopPrice;
		this.marketPrice=marketPrice;
		this.goodsAttrList=goodsAttrList;
	}

	public void addListener(GroupAttrDialog.Listener listener){
		this.listener=listener;
	}
	public interface Listener{
		public void modifyNumber(int number);
		public void modifyGoodsAttrItem(TGoodsAttr.TGoodsAttrItem goodsAttrItem);
		public void addOrder();//立即购买
	}
	
}
