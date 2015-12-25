package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.ShoppingCartListViewtAdapter;
import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ShoppingCartModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.ShoppingCartUitl;
import com.ghsh.view.util.DAlertUtil;
/**
 * 购物车
 * */
public class ShoppingCartActivity extends BaseActivity implements OnClickListener,ShoppingCartListViewtAdapter.Listener{
	private TextView titleView;
	private ListView listView;
	private Button editButton;//编辑按钮
	private CheckBox checkBox;
	private TextView checkBoxText,totalNumber,clearButton;
	private ShoppingCartListViewtAdapter adapter=new ShoppingCartListViewtAdapter(this,new ArrayList<TShoppingCart>());
	private ShoppingCartModel shoppingCartModel;
	private DProgressDialog progressDialog;
	private TextView totalView,goodsTotalView;
	private LinearLayout totalLayout;//价格布局
	private Button okButton,deleteButton,gotoHomeButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppingcart);
		this.initView();
		this.initListener();
		shoppingCartModel=new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(new ShoppingcartResponse(this));
	}
	
	private void initView() {
		if(getIntent().getBooleanExtra("isBackButton", false)){
			
		}else{
			super.setBackButtonVisible(View.GONE);
			super.setBackExit(true);
		}
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.shoppingcart_title);
		editButton=(Button)this.findViewById(R.id.header_button_view);
		editButton.setText(R.string.edit);
		editButton.setTag(true);
		Drawable drawable=this.getResources().getDrawable(R.drawable.shopingcart_edit_icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		editButton.setCompoundDrawables(drawable, null, null, null); //设置左图标
		
		
		listView= (ListView) this.findViewById(R.id.shoppingcart_listView);
		listView.setAdapter(adapter);
		totalView=(TextView) this.findViewById(R.id.shoppingcart_total);
		goodsTotalView=(TextView) this.findViewById(R.id.shoppingcart_goods_total);
		okButton=(Button) this.findViewById(R.id.shoppingcart_ok_button);
		deleteButton=(Button) this.findViewById(R.id.shoppingcart_delete_button);
		checkBox=(CheckBox) this.findViewById(R.id.shoppingcart_checkBox);
		checkBoxText=(TextView) this.findViewById(R.id.shoppingcart_checkBox_text);
		totalLayout=(LinearLayout) this.findViewById(R.id.shoppingcart_total_layout);
		totalNumber=(TextView) this.findViewById(R.id.shoppingcart_total_number);
		clearButton=(TextView) this.findViewById(R.id.shoppingcart_clear_button);
	}
	
	
	private void initListener() {
		okButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		clearButton.setOnClickListener(this);
		adapter.addListener(this);
		editButton.setOnClickListener(this);
		checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.selectAll(checkBox.isChecked());
				update();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TShoppingCart shoppingCart=adapter.getItem(position);
				Intent intent=new Intent(ShoppingCartActivity.this,GoodsDetailsActivity.class);
				intent.putExtra("goodsID", shoppingCart.getGoodsID());
				ShoppingCartActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		progressDialog.show();
		shoppingCartModel.findCartList(getUserID());
	}
	@Override
	public void onClick(View v) {
		if(v==okButton){
			//结算
			ArrayList<String> cardIDList=new ArrayList<String>();
			for(TShoppingCart cart:adapter.getList()){
				if(cart.isSelected()){
					cardIDList.add(cart.getShoppingID()+"");
				}
			}
			if(cardIDList.size()==0){
				this.showShortToast(R.string.shoppingcart_tip_cart_empty);
				return;
			}
			Bundle bundle=new Bundle();
			bundle.putSerializable("cardIDList", cardIDList);
			Intent intent=new Intent(this,OrderDetalisActivity.class);
			intent.putExtras(bundle);
			this.startActivity(intent);
		}else if(v==editButton){
			if((Boolean)editButton.getTag()){
				editButton.setText(R.string.cancel);
				editButton.setTag(false);
				adapter.setEdit(true);
				totalLayout.setVisibility(View.GONE);
				checkBoxText.setVisibility(View.VISIBLE);
				deleteButton.setVisibility(View.VISIBLE);
				okButton.setVisibility(View.GONE);
			}else{
				editButton.setText(R.string.edit);
				editButton.setTag(true);
				adapter.setEdit(false);
				totalLayout.setVisibility(View.VISIBLE);
				checkBoxText.setVisibility(View.GONE);
				deleteButton.setVisibility(View.GONE);
				okButton.setVisibility(View.VISIBLE);
			}
			adapter.notifyDataSetChanged();
		}else if(v==deleteButton){
			//批量删除
			final List<String> list=adapter.getSelectCartIDs();
			if(list.size()==0){
				this.showShortToast(R.string.shoppingcart_tip_cart_empty);
				return;
			}
			DAlertUtil.alertOKAndCel(this, R.string.shoppingcart_alter_delete_more, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					progressDialog.show();
					shoppingCartModel.delMoreCard(ShoppingCartActivity.this.getUserID(),list);
				}
			}).show();
		}else if(v==clearButton){
			//清空
			final List<String> list=adapter.getAllCartIDs();
			if(list.size()==0){
				return;
			}
			DAlertUtil.alertOKAndCel(this, R.string.shoppingcart_delete_clear_tip, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					progressDialog.show();
					shoppingCartModel.delMoreCard(ShoppingCartActivity.this.getUserID(),list);
				}
			}).show();
		}
	}
	class ShoppingcartResponse extends DResponseAbstract{
		public ShoppingcartResponse(Activity activity) {
			super(activity,R.layout.activity_shoppingcart_empty);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_SHOOPINGCART_QUERY_LIST){
				//查询购物车结果
				List<TShoppingCart> list=(List<TShoppingCart>)bean.getObject();
				adapter.setList(list);
				adapter.notifyDataSetChanged();
				editButton.setVisibility(View.VISIBLE);
				ShoppingCartActivity.this.update();
				ShoppingCartUitl.setCartNumber(ShoppingCartActivity.this, list.size());
			}else if(bean.getType()==DVolleyConstans.METHOD_DELETE){
				//删除
				String cartID=bean.getObject()+"";
				adapter.removeCartByID(cartID);
				ShoppingCartActivity.this.update();
				ShoppingCartUitl.clearCartNumber(ShoppingCartActivity.this, 1);
			}else if(bean.getType()==DVolleyConstans.METHOD_SHOOPINGCART_DELETE_MORE){
				//批量删除
				List<String> list=(List<String>)bean.getObject();
				adapter.removeCartByIDs(list);
				ShoppingCartActivity.this.update();
				ShoppingCartUitl.clearCartNumber(ShoppingCartActivity.this, list.size());
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_SHOOPINGCART_MODIFY_NUMBER){
				//修改数量
				TShoppingCart shoppingCart=(TShoppingCart)bean.getObject();
				adapter.modifyNumber(shoppingCart.getShoppingID(), shoppingCart.getGoodsNumber());
				ShoppingCartActivity.this.update();
				return;
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			boolean isShow=adapter.getList().size()==0;
			if(isShow){
				editButton.setVisibility(View.GONE);
			}else{
				editButton.setVisibility(View.VISIBLE);
			}
			return isShow;
		}
		@Override
		protected void onRefresh() {
			progressDialog.show();
			shoppingCartModel.findCartList(getUserID());
		}
		@Override
		protected void onResponseEnd(boolean isMoreData){
			gotoHomeButton=(Button) ShoppingCartActivity.this.findViewById(R.id.shopping_cart_goto_home_button);
			if(gotoHomeButton!=null){
				if(ShoppingCartActivity.this.isBackButtonVisible()==View.VISIBLE){
					gotoHomeButton.setVisibility(View.GONE);
				}else{
					gotoHomeButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							ShoppingCartActivity.this.startActivity("homePage",null);
						}
					});
				}
			}
		}
	}
	
	private void update(){
		totalView.setText(this.getResources().getString(R.string.shoppingcart_text_order_total)+"￥"+adapter.totalPrice());
		goodsTotalView.setText("￥"+adapter.totalPrice());
		totalNumber.setText(String.format(getResources().getString(R.string.shoppingcart_text_top_number), adapter.getAllGoodsNumber()+""));
		okButton.setText(getResources().getString(R.string.shoppingcart_button_accounts)+"("+adapter.getSelectShoopingNumber()+")");
	}
	@Override
	public void modifyCartNumber(TShoppingCart shoppingCart, String number) {
		progressDialog.show();
		shoppingCartModel.modifyCartNumber(shoppingCart.getShoppingID(), number);
	}
	
	@Override
	public void changeCart(TShoppingCart shoppingCart) {
		this.update();
		checkBox.setChecked(adapter.isAllSelect());
	}

	@Override
	public void deleteCartNumber(int position) {
		TShoppingCart cart = adapter.getItem(position);
		List<String> lists = new ArrayList<String>();
		lists.add(cart.getShoppingID());
		shoppingCartModel.delMoreCard(ShoppingCartActivity.this.getUserID(),lists);
		adapter.removeCartByID(cart.getShoppingID());
		ShoppingCartActivity.this.update();
	}
}
