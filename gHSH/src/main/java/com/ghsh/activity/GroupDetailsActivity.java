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
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.GoodsDetailsActivity.ShoppingCartModelResponse;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.view.GroupAttrDialog;
import com.ghsh.code.bean.TGoodsAttr;
import com.ghsh.code.bean.TGoodsAttr.TGoodsAttrItem;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.GroupModel;
import com.ghsh.code.volley.model.ShoppingCartModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.DateFormatUtil;
import com.ghsh.util.MenberUtils;
import com.ghsh.util.ShoppingCartUitl;
import com.ghsh.view.banner.BannerActivity;
import com.ghsh.view.banner.BannerImageView;
import com.ghsh.view.util.ViewTimeCount;
import com.ghsh.view.util.ViewUtils;
import com.ghsh.view.util.ViewUtils.NumberListener;

/**
 * 团购
 * */
public class GroupDetailsActivity extends BaseActivity implements OnClickListener,GroupAttrDialog.Listener{
	private TextView titleView;
	private BannerImageView bannerView;//商品图片
	private ImageButton addNumberButton,minusNumberButton;//加减数量
	private Button orderButton,shareButton;//立即购买,加入购物车,分享
	private Button goodsAttrButton,goodsDesButton,goodsEvaluateButton;;
	private TextView goodsNameView,goodsPriceView,goodsSourcePrice,numberInputView,countdownTotalTimeView;//商品名称，价格,库存
	private GroupModel groupModel;
	private DProgressDialog progressDialog;
	private TGroup group=null;
	private GroupAttrDialog groupAttrDialog;
	private GoodsModelResponse goodsModelResponse=new GoodsModelResponse();
	private boolean isOrder=false;
	private ViewTimeCount viewTimeCount;
	private ShoppingCartModel shoppingCartModel;
	private ShoppingCartModelResponse shoppingCartModelResponse=new ShoppingCartModelResponse();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);
		this.initView();
		this.initListener();
		groupModel=new GroupModel(this);
		groupModel.addResponseListener(goodsModelResponse);
		shoppingCartModel=new ShoppingCartModel(this);
		shoppingCartModel.addResponseListener(shoppingCartModelResponse);
		progressDialog.show();
		Intent intent=getIntent();
		String groupID=intent.getStringExtra("groupID");
		String userID=null;
		if(MenberUtils.isLogin(this)){
			userID=getUserID();
		}
		groupModel.getGroupDetails(groupID,userID);
	}

	private void initView() {
		groupAttrDialog=new GroupAttrDialog(this);
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.group_details_title);
		
		shareButton = (Button) this.findViewById(R.id.header_button_view);
		ViewUtils.setButtonDrawables(this, shareButton, R.drawable.goodsdetails_share_icon, 1);
		shareButton.setVisibility(View.VISIBLE);
		
		bannerView=(BannerImageView)this.findViewById(R.id.group_images);
		goodsNameView= (TextView) this.findViewById(R.id.group_name);
		goodsPriceView= (TextView) this.findViewById(R.id.group_price);
		goodsSourcePrice= (TextView) this.findViewById(R.id.group_sourcePrice);
		goodsAttrButton= (Button) this.findViewById(R.id.group_attr);
		orderButton= (Button) this.findViewById(R.id.orderButton);
		
		addNumberButton=(ImageButton) this.findViewById(R.id.number_add_button);
		minusNumberButton=(ImageButton) this.findViewById(R.id.number_minus_button);
		numberInputView=(TextView) this.findViewById(R.id.number_input);
		countdownTotalTimeView=(TextView) this.findViewById(R.id.group_countdownTotalTime);
		
		goodsDesButton=(Button) this.findViewById(R.id.goods_des_button);
		goodsEvaluateButton=(Button) this.findViewById(R.id.goods_evaluate_button);
		
		goodsSourcePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
		
	}

	private void initListener() {
		goodsAttrButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		orderButton.setOnClickListener(this);
		groupAttrDialog.addListener(this);
		goodsDesButton.setOnClickListener(this);
		goodsEvaluateButton.setOnClickListener(this);
		ViewUtils.addNumberListener(addNumberButton, minusNumberButton, numberInputView,new NumberListener() {
			@Override
			public void modifyNumber(int number) {
				GroupDetailsActivity.this.modifyNumber(number);
			}
		});
		bannerView.addListener(new BannerImageView.Listener() {
			@Override
			public void OnClickPicView(TPic pic) {
				if(bannerView.getPicList()==null||bannerView.getPicList().size()==0){
					return ;
				}
				Intent intent=new Intent(GroupDetailsActivity.this,BannerActivity.class);
				Bundle bundle=new Bundle();
				for(int i=0;i<bannerView.getPicList().size();i++){
					bundle.putSerializable(i+"", bannerView.getPicList().get(i));
				}
				intent.putExtra("picList", bundle);
				GroupDetailsActivity.this.startActivity(intent);
			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(viewTimeCount!=null){
			viewTimeCount.cancel();
		}
	}
	@Override
	public void onClick(View v) {
		if(v==shareButton){
			//分享
			Intent intent=new Intent(this,ShareActivity.class);
			intent.putExtra("goodsName", group.getGroupName());
			intent.putExtra("goodsImage", group.getGroupImage());
			this.startActivity(intent);
			overridePendingTransition(R.anim.dialog_button_in, R.anim.dialog_button_out);
		}else if(v==goodsAttrButton){
			//商品属性
			groupAttrDialog.show(numberInputView.getText().toString());
		}else if(v==orderButton){
			isOrder=true;
			//立即购买
			this.addOrder();
		}else if(v==goodsDesButton){
			//商品详情
			Intent intent=new Intent(this,GoodsDetailsMainActivity.class);
			intent.putExtra("tabIndex", 0);
			intent.putExtra("goodsID", group.getGoodsID());
			this.startActivity(intent);
		}else if(v==goodsEvaluateButton){
			//评价
			Intent intent=new Intent(this,GoodsDetailsMainActivity.class);
			intent.putExtra("tabIndex", 1);
			intent.putExtra("goodsID", group.getGoodsID());
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
				GroupDetailsActivity.this.finish();
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_GET){
					//查询商品详细返回
					group=(TGroup)bean.getObject();
					
					goodsNameView.setText(group.getGroupName());
					goodsPriceView.setText("￥"+group.getShopPrice());
					goodsSourcePrice.setText(group.getMarketPrice());
					if(new BigDecimal(group.getShopPrice()).doubleValue()==new BigDecimal(group.getMarketPrice()).doubleValue()){
						goodsSourcePrice.setVisibility(View.GONE);
					}else{
						goodsSourcePrice.setVisibility(View.VISIBLE);
					}
					
					List<TGoodsAttr> goodsAttrList=group.getGoodsAttrList();
					if(goodsAttrList.size()!=0){
						for(TGoodsAttr goodsAttr:goodsAttrList ){
							goodsAttrButton.setText(goodsAttrButton.getText()+goodsAttr.getAttrName()+" ");
						}
						groupAttrDialog.setAttrInfo(group.getGroupName(),group.getShopPrice(),group.getMarketPrice(),group.getGoodsAttrList());
						goodsAttrButton.setVisibility(View.VISIBLE);
					}
					viewTimeCount=new ViewTimeCount(countdownTotalTimeView, group.getCountdownTotalTime());
					viewTimeCount.start();
					bannerView.createPicView(group.getPicList());
					return;
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
		//判断是否已经选择商品属性
		if(!groupAttrDialog.isCheck()){
			if(!groupAttrDialog.isShowing()){
				groupAttrDialog.show(numberInputView.getText().toString());
			}
			return;
		}
		groupAttrDialog.dismiss();
		progressDialog.show();
		shoppingCartModel.addCart(getUserID(),group.getGoodsID(),group.getGroupID(), numberInputView.getText().toString(), groupAttrDialog.getSelectedIDs());
		
//		Bundle bundle=new Bundle();
//		TOrderItem orderItem=new TOrderItem();
//		orderItem.setGoodsID(group.getGoodsID());
//		orderItem.setGroupID(group.getGroupID());
//		orderItem.setGoodsName(group.getGroupName());
//		orderItem.setGoodsImage(group.getGroupImage());
//		orderItem.setGoodsPrice(group.getShopPrice());
//		orderItem.setMarketPrice(group.getMarketPrice());
//		orderItem.setGoodsNumber(numberInputView.getText().toString());
//		bundle.putSerializable("orderItem", orderItem);
//		bundle.putInt("orderType", 1);//团购商品
//		Intent intent=new Intent(GroupDetailsActivity.this,OrderDetalisActivity.class);
//		intent.putExtras(bundle);
//		this.startActivity(intent);
	}

	
	@Override
	public void modifyNumber(int number) {
		numberInputView.setText(number+"");
	}

	@Override
	public void modifyGoodsAttrItem(TGoodsAttrItem goodsAttrItem) {
		
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
					//立即下单
					ArrayList<String> cardIDList=new ArrayList<String>();
					cardIDList.add(bean.getObject()+"");
					Bundle bundle=new Bundle();
					bundle.putSerializable("cardIDList", cardIDList);
					Intent intent=new Intent(GroupDetailsActivity.this,OrderDetalisActivity.class);
					intent.putExtras(bundle);
					GroupDetailsActivity.this.startActivity(intent);
					return;
				}
			}
			showShortToast(message);
		}
	}
}
