package com.ghsh.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.view.GoodsAttrDialog;
import com.ghsh.code.bean.TGoods;
import com.ghsh.code.bean.TGoodsAttr;
import com.ghsh.code.bean.TGoodsAttr.TGoodsAttrItem;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.GoodsModel;
import com.ghsh.code.volley.model.MyCollectModel;
import com.ghsh.code.volley.model.ShoppingCartModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.ShoppingCartUitl;
import com.ghsh.view.banner.BannerActivity;
import com.ghsh.view.banner.BannerImageView;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.view.util.ViewUtils.NumberListener;

/**
 * 商品详细
 * */
public class GoodsDetailsActivity extends BaseActivity implements OnClickListener,GoodsAttrDialog.Listener{
	private ImageView collectView,cartButton;//收藏
	private TextView titleView;
	private BannerImageView bannerView;//商品图片
	private ImageButton addNumberButton,minusNumberButton;//加减数量
	private Button orderButton,addCartButton,shareButton;//立即购买,加入购物车,分享
	private Button goodsAttrButton,goodsDesButton,goodsEvaluateButton;
	private TextView goodsNameView,goodsPriceView,goodsSourcePrice,numberInputView,cartNumberView;//商品名称，价格,库存
	private GoodsModel goodsModel;
	private MyCollectModel collectModel;
	private ShoppingCartModel shoppingCartModel;
	private DProgressDialog progressDialog;
	private TGoods goods=null;
	private GoodsAttrDialog goodsAttrDialog;
	private GoodsModelResponse goodsModelResponse=new GoodsModelResponse();
	private ShoppingCartModelResponse shoppingCartModelResponse=new ShoppingCartModelResponse();
	private MyCollectModelResponse myCollectModelResponse=new MyCollectModelResponse();
	private boolean isOrder=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsdetails);
		this.initView();
		this.initListener();
		goodsModel=new GoodsModel(this);
		goodsModel.addResponseListener(goodsModelResponse);
		collectModel=new MyCollectModel(this);
		collectModel.addResponseListener(myCollectModelResponse);
		shoppingCartModel=new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(shoppingCartModelResponse);
		
		progressDialog.show();
		Intent intent=getIntent();
		String goodsID=intent.getStringExtra("goodsID");
		String userID=null;
		if(MenberUtils.isLogin(this)){
			userID=getUserID();
		}
		goodsModel.getGoodsDetails(goodsID,userID);
	}

	private void initView() {
		goodsAttrDialog=new GoodsAttrDialog(this);
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.goods_details_title);
		
		shareButton = (Button) this.findViewById(R.id.header_button_view);
		ViewUtils.setButtonDrawables(this, shareButton, R.drawable.goodsdetails_share_icon, 1);
		shareButton.setVisibility(View.VISIBLE);
		
		bannerView=(BannerImageView)this.findViewById(R.id.goods_images);
		goodsNameView= (TextView) this.findViewById(R.id.goods_name);
		goodsPriceView= (TextView) this.findViewById(R.id.goods_price);
		goodsSourcePrice= (TextView) this.findViewById(R.id.goods_sourcePrice);
		collectView= (ImageView) this.findViewById(R.id.goods_collect);
		cartButton= (ImageView) this.findViewById(R.id.goodsdetails_cart_button);
		cartNumberView= (TextView) this.findViewById(R.id.goodsdetails_cart_number);
		goodsAttrButton= (Button) this.findViewById(R.id.goods_attr);
		orderButton= (Button) this.findViewById(R.id.orderButton);
		addCartButton= (Button) this.findViewById(R.id.addCartButton);
		
		addNumberButton=(ImageButton) this.findViewById(R.id.number_add_button);
		minusNumberButton=(ImageButton) this.findViewById(R.id.number_minus_button);
		numberInputView=(TextView) this.findViewById(R.id.number_input);
		
		goodsDesButton=(Button) this.findViewById(R.id.goods_des_button);
		goodsEvaluateButton=(Button) this.findViewById(R.id.goods_evaluate_button);
		
		goodsSourcePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
		
	}

	private void initListener() {
		goodsAttrButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		orderButton.setOnClickListener(this);
		addCartButton.setOnClickListener(this);
		collectView.setOnClickListener(this);
		cartButton.setOnClickListener(this);
		goodsDesButton.setOnClickListener(this);
		goodsEvaluateButton.setOnClickListener(this);
		goodsAttrDialog.addListener(this);
		ViewUtils.addNumberListener(addNumberButton, minusNumberButton, numberInputView,new NumberListener() {
			@Override
			public void modifyNumber(int number) {
				GoodsDetailsActivity.this.modifyNumber(number);
			}
		});
		bannerView.addListener(new BannerImageView.Listener() {
			@Override
			public void OnClickPicView(TPic pic) {
				if(bannerView.getPicList()==null||bannerView.getPicList().size()==0){
					return ;
				}
				Intent intent=new Intent(GoodsDetailsActivity.this,BannerActivity.class);
				Bundle bundle=new Bundle();
				for(int i=0;i<bannerView.getPicList().size();i++){
					bundle.putSerializable(i+"", bannerView.getPicList().get(i));
				}
				intent.putExtra("picList", bundle);
				GoodsDetailsActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		cartNumberView.setVisibility(View.GONE);
		if(MenberUtils.isLogin(this)){
			int cartNumber=ShoppingCartUitl.getCartNumber(GoodsDetailsActivity.this);
			if(cartNumber>0){
				cartNumberView.setVisibility(View.VISIBLE);
				cartNumberView.setText(cartNumber+"");
			}
		}
	}
	@Override
	public void onClick(View v) {
		if(v==shareButton){
			//分享
			Intent intent=new Intent(this,ShareActivity.class);
			intent.putExtra("goodsName", goods.getName());
			intent.putExtra("goodsImage", goods.getDefaultImage());
			this.startActivity(intent);
			overridePendingTransition(R.anim.dialog_button_in, R.anim.dialog_button_out);
		}else if(v==goodsAttrButton){
			//商品属性
			goodsAttrDialog.show(numberInputView.getText().toString());
		}else if(v==addCartButton){
			//加入购物车
			isOrder=false;
			this.addShoppingCart();
		}else if(v==orderButton){
			isOrder=true;
			//立即购买
			this.addOrder();
		}else if(v==collectView){
			//收藏
			this.addCollect();
		}else if(v==cartButton){
			//购物车
			Intent intent=new Intent(this,ShoppingCartActivity.class);
			intent.putExtra("isBackButton", true);
			this.startActivity(intent);
		}else if(v==goodsDesButton){
			//商品详情
			Intent intent=new Intent(this,GoodsDetailsMainActivity.class);
			intent.putExtra("tabIndex", 0);
			intent.putExtra("goodsID", goods.getGoodsID());
			intent.putExtra("goodsName", goods.getName());
			this.startActivity(intent);
		}else if(v==goodsEvaluateButton){
			//评价
			Intent intent=new Intent(this,GoodsDetailsMainActivity.class);
			intent.putExtra("tabIndex", 1);
			intent.putExtra("goodsID", goods.getGoodsID());
			intent.putExtra("goodsName", goods.getName());
			this.startActivity(intent);
		}
	}
	
	/**商品model返回接口*/
	class GoodsModelResponse implements DResponseListener{
		@Override
		public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
			progressDialog.dismiss();
			if(error!=null){
				showShortToast(error.getMessage());
				GoodsDetailsActivity.this.finish();
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_GET){
					//查询商品详细返回
					goods=(TGoods)bean.getObject();
					
					goodsNameView.setText(goods.getName());
					goodsPriceView.setText("￥"+goods.getShopPrice());
					goodsSourcePrice.setText(goods.getMarketPrice());
					if(new BigDecimal(goods.getShopPrice()).doubleValue()==new BigDecimal(goods.getMarketPrice()).doubleValue()){
						goodsSourcePrice.setVisibility(View.GONE);
					}else{
						goodsSourcePrice.setVisibility(View.VISIBLE);
					}
					if(goods.isCollect()){
						collectView.setImageResource(R.drawable.collect_select_icon);
					}
					goodsEvaluateButton.setText(GoodsDetailsActivity.this.getResources().getString(R.string.goods_details_evaluate)+"("+goods.getCommentsNumber()+")");
					
					List<TGoodsAttr> goodsAttrList=goods.getGoodsAttrList();
					if(goodsAttrList.size()!=0){
						for(TGoodsAttr goodsAttr:goodsAttrList ){
							goodsAttrButton.setText(goodsAttrButton.getText()+goodsAttr.getAttrName()+" ");
						}
						goodsAttrDialog.setAttrInfo(goods.getName(),goods.getShopPrice(),goods.getMarketPrice(),goods.isCollect(),goods.getGoodsAttrList());
						goodsAttrButton.setVisibility(View.VISIBLE);
					}
					bannerView.createPicView(goods.getPicList());
					return;
				}
			}
			showShortToast(message);
		}
	}
	class MyCollectModelResponse implements DResponseListener{
		@Override
		public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
			progressDialog.dismiss();
			if(error!=null){
				showShortToast(error.getMessage());
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_ADD){
					//添加收藏
					collectView.setImageResource(R.drawable.collect_select_icon);
					goods.setCollect(true);
				}else if(bean.getType()==DVolleyConstans.METHOD_DELETE){
					//删除收藏
					collectView.setImageResource(R.drawable.collect_icon);
					goods.setCollect(false);
				}
			}
			showShortToast(message);
		}
	}
	class ShoppingCartModelResponse implements DResponseListener{
		@Override
		public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
			progressDialog.dismiss();
			if(error!=null){
				showShortToast(error.getMessage());
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_SHOOPINGCART_ADD){
					//加入购物车返回
					if(isOrder){
						//立即下单
						ArrayList<String> cardIDList=new ArrayList<String>();
						cardIDList.add(bean.getObject()+"");
						Bundle bundle=new Bundle();
						bundle.putSerializable("cardIDList", cardIDList);
						Intent intent=new Intent(GoodsDetailsActivity.this,OrderDetalisActivity.class);
						intent.putExtras(bundle);
						GoodsDetailsActivity.this.startActivity(intent);
						return;
					}else{
						//加入购物车
						ShoppingCartUitl.addCartNumber(GoodsDetailsActivity.this);
						cartNumberView.setVisibility(View.VISIBLE);
						cartNumberView.setText(ShoppingCartUitl.getCartNumber(GoodsDetailsActivity.this)+"");
					}
				}
			}
			showShortToast(message);
		}
	}

	
	@Override
	public void addOrder() {
		if(!MenberUtils.isLogin(this)){
			this.startLogin();
			return;
		}
		this.addShoppingCart();
	}

	@Override
	public void addCollect() {
		if(!MenberUtils.isLogin(this)){
			this.startLogin();
			return;
		}
		progressDialog.show();
		if(goods.isCollect()){
			collectModel.deleteCollect(getUserID(), goods.getGoodsID());
		}else{
			collectModel.addCollect(getUserID(), goods.getGoodsID());
		}
	}

	@Override
	public void modifyNumber(int number) {
		numberInputView.setText(number+"");
	}

	@Override
	public void modifyGoodsAttrItem(TGoodsAttrItem goodsAttrItem) {
		
	}

	@Override
	public void addShoppingCart() {
		//判断是否已经选择商品属性
		if(!goodsAttrDialog.isCheck()){
			if(!goodsAttrDialog.isShowing()){
				goodsAttrDialog.show(numberInputView.getText().toString());
			}
			return;
		}
		goodsAttrDialog.dismiss();
		progressDialog.show();
		shoppingCartModel.addCart(getUserID(),goods.getGoodsID(),null, numberInputView.getText().toString(), goodsAttrDialog.getSelectedIDs());
	}
}
