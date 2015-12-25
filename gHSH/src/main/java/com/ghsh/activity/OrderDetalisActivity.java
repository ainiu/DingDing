package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.OrderDetailsListViewtAdapter;
import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TMyBonus;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.bean.TPayment;
import com.ghsh.code.bean.TSendInfo;
import com.ghsh.code.bean.TShipping;
import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OrderModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.pay.OrderPayUtil;
import com.ghsh.util.BigDecimalUtils;
import com.ghsh.util.DateFormatUtil;
import com.ghsh.util.MenberUtils;
import com.ghsh.view.util.DAlertUtil;

/**
 * 订单详情
 * */
public class OrderDetalisActivity extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	
	private ListView listView;
	private OrderDetailsListViewtAdapter adapter=new OrderDetailsListViewtAdapter(this,new ArrayList<TOrderItem>());
	/**地址布局信息*/
	private TextView nameView,mobileView,addressView,postZipView;
	private ImageView addressBeforeView;
	private RelativeLayout addressLayout;
	/**取消布局信息*/
	private View cancelLayout;//取消布局
	private Button cancelButton,returnButton;//取消订单,订单退款
	/**订单信息*/
	private RelativeLayout paymentwayLayout,paywayLayout,receiptLayout,couponLayout,orderstatusLayout,bonusLayout;
	private TextView paymentwayView,paywayView,couponView,receiptView,orderstatusView,orderstatusDescView,bonusView;
	private ImageView paymentwayBeforeView,paywayBeforeView,couponBeforeView,receiptBeforeView,bonusBeforeView;
	private TextView ensurePhone,ordertime,totalMoney,freightView,goodsMoney,goodsNumberTotal;//客服电话,下单时间,总金额,运费,商品金额,商品数量
	private Button paymentwayButton,confirmButton,receiptButton;//去付款,提交订单,确认收货
	private View buttonLayout;//底部面板
	private EditText commentView;//评论输入框
	/**其他*/
	private LinearLayout ensureLayout;
	private TSendInfo sendInfo=null;//地址
	private TShipping shipping=null;//配送方式
	private TPayment payment=null;//支付方式
	private TCoupon coupon=null;//优惠劵
	private TMyBonus myBonus=null;//红包
	private String invPayee=null;//发票抬头
	private OrderModel orderModel;
	private DProgressDialog progressDialog;
	private String orderID=null;
	private OrderPayUtil orderPayUtil;
	private TOrder order;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!this.checkLogin()) {
			return;
		}
		setContentView(R.layout.activity_orderdetails);
		this.initView();
		this.initListener();
		orderModel=new OrderModel(this);
		orderModel.addResponseListener(this);
		orderPayUtil =new OrderPayUtil(this,progressDialog);
		this.initData();
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.orderdetails_title);
		titleView.setVisibility(View.VISIBLE);

		nameView=(TextView)this.findViewById(R.id.orderdetails_address_userinfo_name);
		mobileView=(TextView)this.findViewById(R.id.orderdetails_address_userinfo_mobile);
		addressView=(TextView)this.findViewById(R.id.order_confirm_userinfo_address);
		postZipView=(TextView)this.findViewById(R.id.order_confirm_userinfo_postZip);
		addressBeforeView=(ImageView)this.findViewById(R.id.order_confirm_userinfo_before);
		addressLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_address_layout);
		
		cancelLayout=this.findViewById(R.id.orderdetails_layout_cancel);
		cancelButton=(Button)this.findViewById(R.id.orderdetails_button_cancel);
		returnButton=(Button)this.findViewById(R.id.orderdetails_button_return);
		
		paymentwayLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_paymentway_layout);
		paywayLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_payway_layout);
		couponLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_coupon_layout);
		receiptLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_receipt_layout);
		orderstatusLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_orderstatus_layout);
		bonusLayout=(RelativeLayout)this.findViewById(R.id.orderdetails_bonus_layout);
		
		paymentwayView=(TextView)this.findViewById(R.id.orderdetails_paymentway_text);
		paywayView=(TextView)this.findViewById(R.id.orderdetails_payway_text);
		couponView=(TextView)this.findViewById(R.id.orderdetails_coupon_text);
		receiptView=(TextView)this.findViewById(R.id.orderdetails_receipt_text);
		orderstatusView=(TextView)this.findViewById(R.id.orderdetails_orderstatus_text);
		orderstatusDescView=(TextView)this.findViewById(R.id.orderdetails_orderstatus_desc);
		bonusView=(TextView)this.findViewById(R.id.orderdetails_bonus_text);

		paymentwayBeforeView=(ImageView)this.findViewById(R.id.orderdetails_paymentway_before);
		paywayBeforeView=(ImageView)this.findViewById(R.id.orderdetails_payway_before);
		couponBeforeView=(ImageView)this.findViewById(R.id.orderdetails_coupon_before);
		receiptBeforeView=(ImageView)this.findViewById(R.id.orderdetails_receipt_before);
		bonusBeforeView=(ImageView)this.findViewById(R.id.orderdetails_bonus_before);
		
		ensurePhone=(TextView)this.findViewById(R.id.orderdetails_ensure_phone);
		ordertime=(TextView)this.findViewById(R.id.orderdetails_ordertime);
		totalMoney=(TextView)this.findViewById(R.id.orderdetails_totalMoney);
		freightView=(TextView)this.findViewById(R.id.orderdetails_freight);
		goodsMoney=(TextView)this.findViewById(R.id.orderdetails_goodsMoney);
		goodsNumberTotal=(TextView)this.findViewById(R.id.orderdetails_goods_number_total);
		paymentwayButton=(Button)this.findViewById(R.id.orderdetails_button_paymentway);
		confirmButton=(Button)this.findViewById(R.id.orderdetails_button_confirm);
		receiptButton=(Button)this.findViewById(R.id.orderdetails_button_receipt);
		buttonLayout=this.findViewById(R.id.orderdetails_button_layout);
		
		ensureLayout=(LinearLayout)this.findViewById(R.id.orderdetails_ensure_layout);
		listView=(ListView)this.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		
		commentView=(EditText)this.findViewById(R.id.orderdetalis_item_comment);
		
	}
	
	
	private void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TOrderItem orderItem=adapter.getItem(position);
				Intent intent=new Intent(OrderDetalisActivity.this,GoodsDetailsActivity.class);
				intent.putExtra("goodsID", orderItem.getGoodsID());
				OrderDetalisActivity.this.startActivity(intent);
			}
		});
	}

	private void initData(){
		Intent intent=this.getIntent();
		int orderType=intent.getIntExtra("orderType", 0);
		orderID=intent.getStringExtra("orderID");
		switch(orderType){
			case 0:
				//订单确认
				titleView.setText(R.string.orderdetails_title_confirm);
				progressDialog.show();
				orderModel.getConfirmOrderInfo(getUserID(),null,null,null,this.getCartIDs());
				break;
			default:
				//其他订单
				progressDialog.show();
				orderModel.getOrderDetail(orderID);
				break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		orderPayUtil.onActivityResult(requestCode, resultCode, data);//支付回调
		if (resultCode == Activity.RESULT_OK) {
			//提交信息返回
			if(requestCode==1){
				//选择地址返回
				sendInfo=(TSendInfo)data.getSerializableExtra("sendInfo");
				nameView.setText(sendInfo.getConsignee());
				mobileView.setText(sendInfo.getMobile());
				addressView.setText(getResources().getString(R.string.orderdetails_text_address)+sendInfo.getRegionName()+" "+sendInfo.getAddress());
				postZipView.setText(getResources().getString(R.string.orderdetails_text_postZip)+sendInfo.getZipcode());
				progressDialog.show();
				orderModel.getConfirmOrderInfo(getUserID(),sendInfo.getSendInfoID(),shipping.getShippingID(),payment.getPaymentID(),this.getCartIDs());
			}else if(requestCode==2){
				//选择支付方式返回
				payment=(TPayment)data.getSerializableExtra("payment");
				paymentwayView.setText(payment.getPaymentName());
			}else if(requestCode==3){
				//选择配送方式返回
				shipping=(TShipping)data.getSerializableExtra("shipping");
				paywayView.setText(shipping.getShippingName());
				progressDialog.show();
				orderModel.getConfirmOrderInfo(getUserID(),sendInfo.getSendInfoID(),shipping.getShippingID(),payment.getPaymentID(),this.getCartIDs());
			}else if(requestCode==4){
				//选择优惠劵返回
				coupon=(TCoupon)data.getSerializableExtra("coupon");
				if(coupon.getCouponID()==null||coupon.getCouponID().equals("")){
					couponView.setText(this.getResources().getString(R.string.order_coupon_tip_no));
				}else{
					couponView.setText(this.getResources().getString(R.string.order_coupon_price)+"￥"+coupon.getPrice());
				}
			}else if(requestCode==5){
				//发票信息返回
				invPayee=data.getStringExtra("invoiceTitle");
				if(invPayee==null||invPayee.equals("")){
					receiptView.setText(R.string.orderdetails_text_receipt_no);
				}else{
					receiptView.setText(invPayee);
				}
			}else if(requestCode==6){
				//选择红包返回
				myBonus=(TMyBonus)data.getSerializableExtra("myBonus");
				String money="0.0";
				if(myBonus.getBonusID()==null||myBonus.getBonusID().equals("")){
					bonusView.setText(this.getResources().getString(R.string.order_bonus_tip_no));
				}else{
					bonusView.setText(this.getResources().getString(R.string.order_bonus_price)+"￥"+myBonus.getTypeMoney());
					money=myBonus.getTypeMoney();
				}
				totalMoney.setText(BigDecimalUtils.subtract(order.getOrderAmount(), money)+"");
			}
		}else{
			//直接单机返回按钮返回
			if(requestCode==1){
				//地址
				if(sendInfo!=null){
					return;
				}
				DAlertUtil.alertOKAndCel(this, R.string.orderdetails_alter_message_sendinfo, new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent(OrderDetalisActivity.this,MySendInfoActivity.class);
						intent.putExtra("isSelect", true);
						OrderDetalisActivity.this.startActivityForResult(intent, 1);
					}
				}, new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						OrderDetalisActivity.this.finish();
					}
				}).show();
			}
		}
	}
	
	public List<String> getCartIDs(){
		ArrayList<String> cardIDList=new ArrayList<String>();
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null&&bundle.size()!=0){
			cardIDList=(ArrayList<String>)bundle.getSerializable("cardIDList");
		}
		return cardIDList;
	}
	
	@Override
	public void onClick(View v) {
		if(v==addressLayout){
			//选择地址
			Intent intent=new Intent(this,MySendInfoActivity.class);
			intent.putExtra("isSelect", true);
			this.startActivityForResult(intent, 1);
		}else if(v==paymentwayLayout){
			//选择支付方式
			Intent intent=new Intent(this,OrderPaymentWayActivity.class);
			intent.putExtra("paymentID", payment.getPaymentID());
			this.startActivityForResult(intent,2);
		}else if(v==paywayLayout){
			//选择配送方式
			if(shipping==null){
				this.showShortToast("暂不支持当前地区配送，请更换配送地址！");
				return;
			}
			Intent intent=new Intent(this,OrderPayWayActivity.class);
			intent.putExtra("shippingID", shipping.getShippingID());
			intent.putExtra("addressID", sendInfo.getSendInfoID());
			this.startActivityForResult(intent, 3);
		}else if(v==couponLayout){
			//选择优惠劵
			Intent intent=new Intent(this,OrderCouponActivity.class);
			intent.putExtra("money", goodsMoney.getText().toString().trim());
			if(coupon!=null){
				intent.putExtra("couponID", coupon.getCouponID());
			}
			this.startActivityForResult(intent, 4);
		}else if(v==receiptLayout){
			//发票信息
			Intent intent=new Intent(this,OrderReceiptActivity.class);
			intent.putExtra("receiptRise", receiptView.getText().toString());
			this.startActivityForResult(intent, 5);
		}else if(v==bonusLayout){
			//选择红包
			Intent intent=new Intent(this,OrderBonusActivity.class);
			intent.putExtra("money", goodsMoney.getText().toString().trim());
			if(myBonus!=null){
				intent.putExtra("bonusID", myBonus.getBonusID());
			}
			this.startActivityForResult(intent, 6);
		}else if(v==cancelButton){
			//取消订单
			DAlertUtil.alertOKAndCel(this, R.string.order_list_alter_message_cancel, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					progressDialog.show();
					orderModel.cancelOrder(orderID);
				}
			}).show();
		}else if(v==receiptButton){
			//确认收货
			DAlertUtil.alertOKAndCel(this, R.string.order_list_alter_message_receipt, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					progressDialog.show();
					orderModel.receiptOrder(orderID);
				}
			}).show();
		}else if(v==returnButton){
			//订单退款
			DAlertUtil.alertOKAndCel(this, R.string.order_list_alter_message_return, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent=new Intent(OrderDetalisActivity.this,OrderReturnActivity.class);
					intent.putExtra("orderID", orderID);
					OrderDetalisActivity.this.startActivity(intent);
					OrderDetalisActivity.this.finish();
				}
			}).show();
		}else if(v==confirmButton){
			//提交订单
			if(sendInfo==null){
				this.showShortToast("请选择收货地址！");
				return;
			}
			if(payment==null){
				this.showShortToast("请选择支付方式！");
				return;
			}
			if(shipping==null){
				this.showShortToast("请选择配送方式！");
				return;
			}
			String userID=MenberUtils.getUserID(this);
			String paymentID=payment.getPaymentID();
			String shippingID=shipping.getShippingID();
			String addressID=sendInfo.getSendInfoID();
			String bonusID=myBonus==null||myBonus.getBonusID()==null?"":myBonus.getBonusID();
			String goodsAmount=goodsMoney.getText().toString();
			String orderAmount=totalMoney.getText().toString();
			String shippingFee=freightView.getText().toString();
			List<String> cardIDList=this.getCartIDs();
			String orderComment=commentView.getText().toString().trim();
			orderModel.addOrder( userID, paymentID, shippingID, addressID,bonusID, goodsAmount, orderAmount, shippingFee, invPayee,orderComment,cardIDList);
		}else if(v==paymentwayButton){
			//去支付
			orderPayUtil.payOrder(orderID, null,new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					OrderDetalisActivity.this.finish();
				}
			});
		}
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			this.finish();
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_ORDER_GET_CONFIRM_INFO){
				//查询确认订单信息
				this.resuleConfirmOrder((TOrder)bean.getObject());
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_GET){
				//查询订单详细
				TOrder order=(TOrder)bean.getObject();
				this.setOrder(order);
				return;
			}else if(bean.getType()==DVolleyConstans.METHOD_ORDER_ADD){
				//提交订单
				this.resultAddOrder((TOrder)bean.getObject(),message);
				return;
			}else if (bean.getType() == DVolleyConstans.METHOD_ORDER_CANCE) {
				// 取消订单
				this.showShortToast("订单取消成功");
				Intent intent = new Intent();
				setResult(Activity.RESULT_OK, intent);  
		        finish();
				return;
			}
		}
		this.showShortToast(message);
	}
	/**查询确认订单返回*/
	private void resuleConfirmOrder(TOrder order){
		sendInfo=order.getSendInfo();
		if(sendInfo==null){
			Intent intent=new Intent(this,MySendInfoAddActivity.class);
			this.startActivityForResult(intent, 1);
		}
		this.setOrder(order);
	}
	/**提交订单返回*/
	private void resultAddOrder(final TOrder order,String message){
		orderID=order.getOrderID();
		if(order==null||order.getPayment()==null){
			OrderDetalisActivity.this.showShortToast(message);
			return;
		}
		if(!order.getPayment().isOnline()){
			//不是在线支付，跳转提交成功页面
			this.showShortToast(message);
			this.finish();
			return;
		}
		//在线支付，提示是否立即支付
		orderPayUtil.payOrder(orderID,null, new android.content.DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OrderDetalisActivity.this.finish();
			}
		});
	}
	
	
	private void setOrder(TOrder order){
		this.order=order;
		adapter.setList(order.getOrderItemList());
		adapter.notifyDataSetChanged();
		goodsMoney.setText(order.getGoodsAmount());
		freightView.setText(order.getShippingFee());
		totalMoney.setText(order.getOrderAmount());
		
		goodsNumberTotal.setText(String.format(getResources().getString(R.string.orderdetails_text_goods_number_total),order.getGoodsNumber()));
		orderstatusView.setText(Constants.ORDER_TYLE_MAP().get(order.getOrderType()));
		sendInfo=order.getSendInfo();
		if(sendInfo!=null){
			nameView.setText(sendInfo.getConsignee());
			mobileView.setText(sendInfo.getMobile());
			if(mobileView.getText().toString()==""){
				mobileView.setText(sendInfo.getTel());
			}
			addressView.setText(getResources().getString(R.string.orderdetails_text_address)+sendInfo.getRegionName()+" "+sendInfo.getAddress());
			postZipView.setText(getResources().getString(R.string.orderdetails_text_postZip)+sendInfo.getZipcode());
		}
		payment=order.getPayment();
		if(payment!=null){  
			paymentwayView.setText(payment.getPaymentName());
		}
		shipping=order.getShipping();
		if(shipping!=null){
			paywayView.setText(shipping.getShippingName());
		}
		//优惠劵
		coupon=order.getCoupon();
		if(coupon!=null){
			if(coupon.getCouponID()!=null&&!coupon.getCouponID().equals("")){
				couponView.setText(this.getResources().getString(R.string.order_coupon_price)+"￥"+coupon.getPrice());
			}
		}
		//红包
		myBonus=order.getMyBonus();
		if(myBonus!=null){
			bonusView.setText(this.getResources().getString(R.string.order_bonus_price)+"￥"+myBonus.getTypeMoney());
		}
		ensurePhone.setText(order.getShopPhone());
		//发票信息
		invPayee=order.getInvPayee();
		if(invPayee!=null&&!invPayee.equals("")){
			receiptView.setText(invPayee);
		}
		StringBuffer orderStatusBudder=new StringBuffer();
		if(order.getAddTime()!=null&&!order.getAddTime().equals("")){
			orderStatusBudder.append("下单时间：").append(order.getAddTime()).append("\n");
		}
		if(order.getPayTime()!=null&&!order.getPayTime().equals("")){
			orderStatusBudder.append("支付时间：").append(order.getPayTime()).append("\n");
		}
		
		if(order.getConfirmTime()!=null&&!order.getConfirmTime().equals("")){
			orderStatusBudder.append("确认时间：").append(order.getConfirmTime()).append("\n");
		}
		if(order.getShipTime()!=null&&!order.getShipTime().equals("")){
			orderStatusBudder.append("发货时间：").append(order.getShipTime()).append("\n");
		}
		if(order.getEvaluationTime()!=null&&!order.getEvaluationTime().equals("")){
			orderStatusBudder.append("评论时间：").append(order.getEvaluationTime()).append("\n");
		}
		if(orderStatusBudder.length()!=0){
			orderStatusBudder.deleteCharAt(orderStatusBudder.length()-1);
		}
		ordertime.setText(orderStatusBudder.toString());
		
		if(order.getOrderType()==null){
			//订单确认
			//确认订单按钮
			confirmButton.setVisibility(View.VISIBLE);
			confirmButton.setOnClickListener(this);
			//收货地址
			addressLayout.setOnClickListener(this);
			addressBeforeView.setVisibility(View.VISIBLE);
			//支付方式
			paymentwayLayout.setOnClickListener(this);
			paymentwayBeforeView.setVisibility(View.VISIBLE);
			//优惠劵
			couponLayout.setOnClickListener(this);
			couponBeforeView.setVisibility(View.VISIBLE);
			//红包
			bonusLayout.setOnClickListener(this);
			bonusBeforeView.setVisibility(View.VISIBLE);
			//配送方式
			paywayLayout.setOnClickListener(this);
			paywayBeforeView.setVisibility(View.VISIBLE);
			//发票信息
			receiptLayout.setOnClickListener(this);
			receiptBeforeView.setVisibility(View.VISIBLE);
			//订单状态
			orderstatusLayout.setVisibility(View.GONE);
			//订单描述
			ensureLayout.setVisibility(View.GONE);
			//订单留言
			commentView.setVisibility(View.VISIBLE);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_1)){ 
			//待付款
			//收货地址
//			addressBeforeView.setVisibility(View.VISIBLE);
//			addressLayout.setOnClickListener(this);
			//取消订单
			cancelLayout.setVisibility(View.VISIBLE);
			cancelButton.setVisibility(View.VISIBLE);
			cancelButton.setOnClickListener(this);
			//支付方式
//			paymentwayBeforeView.setVisibility(View.VISIBLE);
//			paymentwayLayout.setOnClickListener(this);
			//优惠劵
//			couponLayout.setOnClickListener(this);
//			couponBeforeView.setVisibility(View.VISIBLE);
			//配送方式
//			paywayBeforeView.setVisibility(View.VISIBLE);
//			paywayLayout.setOnClickListener(this);
			//发票信息
//			receiptLayout.setOnClickListener(this);
//			receiptBeforeView.setVisibility(View.VISIBLE);
			//支付按钮
			paymentwayButton.setVisibility(View.VISIBLE);
			paymentwayButton.setOnClickListener(this);
			//订单状态
//			orderstatusDescView.setVisibility(View.VISIBLE);
//			orderstatusDescView.setText(String.format(getResources().getString(R.string.orderdetails_text_orderstatuc_1),DateFormatUtil.addDay(order.getAddTime(), 7)));
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_2)){
			//待发货
			buttonLayout.setVisibility(View.GONE);
			//订单退款
			cancelLayout.setVisibility(View.VISIBLE);
			returnButton.setVisibility(View.VISIBLE);
			returnButton.setOnClickListener(this);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_3)){
			//待收货
			receiptButton.setVisibility(View.VISIBLE);
			receiptButton.setOnClickListener(this);
//			orderstatusDescView.setVisibility(View.VISIBLE);
//			orderstatusDescView.setText(String.format(getResources().getString(R.string.orderdetails_text_orderstatuc_3),DateFormatUtil.addDay(order.getShipTime(), 7)));
			//订单退款
			cancelLayout.setVisibility(View.VISIBLE);
			returnButton.setVisibility(View.VISIBLE);
			returnButton.setOnClickListener(this);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_4)){
			//交易成功
			buttonLayout.setVisibility(View.GONE);
		}else if(order.getOrderType().equals(Constants.ORDER_TYLE_5)){
			//交易取消
			buttonLayout.setVisibility(View.GONE);
		}
	}
}
