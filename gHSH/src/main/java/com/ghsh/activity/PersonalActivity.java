package com.ghsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TPersonalBean;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.PageDataModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
import com.ghsh.view.pullrefresh.DScrollView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;

/**
 * 个人中心页面
 * */
public class PersonalActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private Button setingButton;
	private DScrollView pullToRefresh;
	private ScrollView scrollView;
	private ImageView imageView;
	private Button loginButton,regButton;
	private TextView userNameView,collectView;
	private Button list_collectButton,list_msgButton,list_addressButton,list_userButton,list_allOrderButton,list_opinionButton,list_historyButton,list_withdrawButton,list_couponButton,list_useraccountrecordButton,list_bonusButton,list_rechargeButton;//我的收藏,我的地址
	private Button paymentButton,receiptButton,shipButton,evaluateButton;//待付款,待发货,待收货,待评价,所有订单
	private TextView paymentBadge,receiptBadge,shipBadge,evaluateBadge;
	private View loginLayout,loginLayout2;//登陆才可以查看的view
	private TextView levelNameView,growthvalueView;//级别名称，成长值
	private TextView moneyView;
	private PageDataModel pageDataModel;
	private DProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		this.initView();
		this.initListener();
		pageDataModel=new PageDataModel(this);
		pageDataModel.addResponseListener(this);
	}

	private void initView() {
		super.setBackButtonVisible(View.GONE);
		super.setBackExit(true);
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.personal_title);
		setingButton= (Button) this.findViewById(R.id.header_button_view);
		Drawable drawable=this.getResources().getDrawable(R.drawable.personal_setting_icon);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		setingButton.setCompoundDrawables(null, null, drawable, null); //设置左图标
		setingButton.setVisibility(View.VISIBLE);
		
		pullToRefresh=(DScrollView)this.findViewById(R.id.personal_refresh_listView);
		pullToRefresh.setPullDownEnabled(true);
		scrollView=pullToRefresh.getRefreshableView();
		scrollView.addView(this.getView(R.layout.activity_personal_scrollview));
		
		imageView=(ImageView)scrollView.findViewById(R.id.personal_image);
		loginButton=(Button)scrollView.findViewById(R.id.personal_login_button);
		regButton=(Button)scrollView.findViewById(R.id.personal_reg_button);
		userNameView=(TextView)scrollView.findViewById(R.id.personal_username);
		levelNameView=(TextView)scrollView.findViewById(R.id.personal_usern_level_name);
		growthvalueView=(TextView)scrollView.findViewById(R.id.personal_usern_growthvalue);
		moneyView=(TextView)scrollView.findViewById(R.id.personal_money);
		
		loginLayout=scrollView.findViewById(R.id.personal_login_info_layout);
		loginLayout2=scrollView.findViewById(R.id.personal_login_info_layout2);
		
		list_collectButton=(Button)scrollView.findViewById(R.id.personal_list_collect_button);
		list_addressButton=(Button)scrollView.findViewById(R.id.personal_list_address_button);
		list_userButton=(Button)scrollView.findViewById(R.id.personal_list_user_button);
		list_allOrderButton=(Button)scrollView.findViewById(R.id.personal_list_allorder_button);
		list_msgButton=(Button)scrollView.findViewById(R.id.personal_list_msg_button);
		list_opinionButton=(Button)scrollView.findViewById(R.id.personal_list_opinion_button);
		list_historyButton=(Button)scrollView.findViewById(R.id.personal_list_history_button);
		list_withdrawButton=(Button)scrollView.findViewById(R.id.personal_list_withdraw_button);
		list_rechargeButton=(Button)scrollView.findViewById(R.id.personal_list_recharge_button);
		list_couponButton=(Button)scrollView.findViewById(R.id.personal_list_coupon_button);
		list_useraccountrecordButton=(Button)scrollView.findViewById(R.id.personal_list_useraccountrecord_button);
		list_bonusButton=(Button)scrollView.findViewById(R.id.personal_list_bonus_button);
		
		paymentButton=(Button)scrollView.findViewById(R.id.personal_payment_button);
		receiptButton=(Button)scrollView.findViewById(R.id.personal_receipt_button);
		shipButton=(Button)scrollView.findViewById(R.id.personal_ship_button);
		evaluateButton=(Button)scrollView.findViewById(R.id.personal_evaluate_button);
		
		paymentBadge=(TextView)scrollView.findViewById(R.id.personal_payment_button_tip);
		receiptBadge=(TextView)scrollView.findViewById(R.id.personal_receipt_button_tip);
		shipBadge=(TextView)scrollView.findViewById(R.id.personal_ship_button_tip);
		evaluateBadge=(TextView)scrollView.findViewById(R.id.personal_evaluate_button_tip);
		
		collectView=(TextView)scrollView.findViewById(R.id.personal_my_collect_button_text);
		
	}
	
	private void initListener() {
		loginButton.setOnClickListener(this);
		regButton.setOnClickListener(this);
		setingButton.setOnClickListener(this);
		imageView.setOnClickListener(this);
		list_collectButton.setOnClickListener(this);
		list_addressButton.setOnClickListener(this);
		list_userButton.setOnClickListener(this);
		list_allOrderButton.setOnClickListener(this);
		list_msgButton.setOnClickListener(this);
		list_opinionButton.setOnClickListener(this);
		list_historyButton.setOnClickListener(this);
		list_withdrawButton.setOnClickListener(this);
		list_rechargeButton.setOnClickListener(this);
		list_couponButton.setOnClickListener(this);
		list_useraccountrecordButton.setOnClickListener(this);
		list_bonusButton.setOnClickListener(this);
		paymentButton.setOnClickListener(this);
		receiptButton.setOnClickListener(this);
		shipButton.setOnClickListener(this);
		evaluateButton.setOnClickListener(this);
		pullToRefresh.setOnRefreshListener(new DOnRefreshListener() {
			@Override
			public void onPullDownToRefresh() {
				if(MenberUtils.isLogin(PersonalActivity.this)){
					pageDataModel.getPersionPage(MenberUtils.getUserID(PersonalActivity.this));
				}else{
					pullToRefresh.onStopDownRefresh(null);
				}
			}
			@Override
			public void onPullUpToRefresh() {
				
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(MenberUtils.isLogin(this)){
			userNameView.setText(this.getMenberName());
			loginLayout.setVisibility(View.VISIBLE);
			loginLayout2.setVisibility(View.VISIBLE);
			pageDataModel.getPersionPage(MenberUtils.getUserID(PersonalActivity.this));
			loginButton.setVisibility(View.GONE);
			regButton.setVisibility(View.GONE);
		}else{
			userNameView.setText("");
			loginLayout.setVisibility(View.GONE);
			loginLayout2.setVisibility(View.GONE);
			loginButton.setVisibility(View.VISIBLE);
			regButton.setVisibility(View.VISIBLE);
			
			paymentBadge.setText("0");
			paymentBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
			paymentBadge.setBackgroundDrawable(null);
			
			receiptBadge.setText("0");
			receiptBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
			receiptBadge.setBackgroundDrawable(null);
			
			shipBadge.setText("0");
			shipBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
			shipBadge.setBackgroundDrawable(null);
			
			evaluateBadge.setText("0");
			evaluateBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
			evaluateBadge.setBackgroundDrawable(null);
			
			imageView.setImageResource(R.drawable.personal_no_active_user_icon);
			collectView.setText("");
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v==setingButton){
			//设置
			Intent intent=new Intent(this,SettingActivity.class);
			this.startActivity(intent);
			
//			Intent intent=new Intent(this,MapGaoDeActivity.class);
//			this.startActivity(intent);
			
//			Intent intent=new Intent(this,GroupbuyListActivity.class);
//			this.startActivity(intent);
//			Intent intent=new Intent(this,CommissionActivity.class);
//			this.startActivity(intent);
//			Intent intent=new Intent(this,BargainActivity.class);
//			this.startActivity(intent);
		}else if(v==loginButton){
			//登录
			Intent intent=new Intent(this,LoginActivity.class);
			this.startActivity(intent);
		}else if(v==regButton){
			//注册
			Intent intent=new Intent(this,RegisterMobileActivity.class);
			this.startActivity(intent);
		}else if(v==list_collectButton){
			//我的收藏
			Intent intent=new Intent(this,MyCollectActivity.class);
			this.startActivity(intent);
		}else if(v==list_addressButton){
			//我的地址
			Intent intent=new Intent(this,MySendInfoActivity.class);
			this.startActivity(intent);
		}else if(v==list_msgButton){
			//我的消息
			Intent intent=new Intent(this,MessageActivity.class);
			this.startActivity(intent);
		}else if(v==list_bonusButton){
			//我的红包
			Intent intent=new Intent(this,MyBonusActivity.class);
			this.startActivity(intent);
		}else if(v==list_couponButton){
			//我的优惠劵
			Intent intent=new Intent(this,MyCouponActivity.class);
			this.startActivity(intent);
		}else if(v==list_withdrawButton){
			//提现
			Intent intent=new Intent(this,UserAccountWithdrawActivity.class);
			this.startActivity(intent);
		}else if(v==list_rechargeButton){
			//充值
			Intent intent=new Intent(this,UserAccountRechargeActivity.class);
			this.startActivity(intent);
		}else if(v==list_userButton){
			//账户管理
			Intent intent=new Intent(this,MyInformationActivity.class);
			this.startActivity(intent);
		}else if(v==list_useraccountrecordButton){
			//账户明细
			Intent intent=new Intent(this,UserAccountRecordActivity.class);
			this.startActivity(intent);
		}else if(v==list_opinionButton){
			//意见反馈
			Intent intent=new Intent(this,OpinionActivity.class);
			this.startActivity(intent);
		}else if(v==list_historyButton){
			//历史浏览
			Intent intent=new Intent(this,MyGoodsHistoryActivity.class);
			this.startActivity(intent);
		}else if(v==paymentButton){
			//待付款
			Intent intent=new Intent(this,OrderListActivity.class);
			intent.putExtra("orderType", 1);
			this.startActivity(intent);
		}else if(v==receiptButton){
			//待发货
			Intent intent=new Intent(this,OrderListActivity.class);
			intent.putExtra("orderType", 2);
			this.startActivity(intent);
		}else if(v==shipButton){
			//待收货
			Intent intent=new Intent(this,OrderListActivity.class);
			intent.putExtra("orderType", 3);
			this.startActivity(intent);
		}else if(v==evaluateButton){
			//待评价
			Intent intent=new Intent(this,OrderListActivity.class);
			intent.putExtra("orderType", 4);
			this.startActivity(intent);
		}else if(v==list_allOrderButton){
			//所有订单
			Intent intent=new Intent(this,OrderListActivity.class);
			intent.putExtra("orderType", 0);
			this.startActivity(intent);
		}else if(v==imageView){
			//单击头像
			if(MenberUtils.isLogin(this)){
				Intent intent=new Intent(this,UploadImageActivity.class);
				this.startActivityForResult(intent, 2);
			}else{
				Intent intent=new Intent(this,LoginActivity.class);
				this.startActivity(intent);
			}
		}
	}
	
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		pullToRefresh.onStopDownRefresh(null);
		if(error!=null){
			showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				//状态返回
				TPersonalBean personalBean=(TPersonalBean)bean.getObject();
				if(!personalBean.getNumberWaitPay().equals("")&&!personalBean.getNumberWaitPay().equals("0")){
					paymentBadge.setText(personalBean.getNumberWaitPay());
					paymentBadge.setTextColor(getResources().getColor(R.color.white));
					paymentBadge.setBackgroundResource(R.drawable.badgeview_bg);
				}else{
					paymentBadge.setText("0");
					paymentBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
					paymentBadge.setBackgroundDrawable(null);
				}
				if(!personalBean.getNumberDeliveryPay().equals("")&&!personalBean.getNumberDeliveryPay().equals("0")){
					receiptBadge.setText(personalBean.getNumberDeliveryPay());
					receiptBadge.setTextColor(getResources().getColor(R.color.white));
					receiptBadge.setBackgroundResource(R.drawable.badgeview_bg);
				}else{
					receiptBadge.setText("0");
					receiptBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
					receiptBadge.setBackgroundDrawable(null);
				}
				if(!personalBean.getNumberReceiptPay().equals("")&&!personalBean.getNumberReceiptPay().equals("0")){
					shipBadge.setText(personalBean.getNumberReceiptPay());
					shipBadge.setTextColor(getResources().getColor(R.color.white));
					shipBadge.setBackgroundResource(R.drawable.badgeview_bg);
				}else{
					shipBadge.setText("0");
					shipBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
					shipBadge.setBackgroundDrawable(null);
				}
				if(!personalBean.getNumberCommentPay().equals("")&&!personalBean.getNumberCommentPay().equals("0")){
					evaluateBadge.setText(personalBean.getNumberCommentPay());
					evaluateBadge.setTextColor(getResources().getColor(R.color.white));
					evaluateBadge.setBackgroundResource(R.drawable.badgeview_bg);
				}else{
					evaluateBadge.setText("0");
					evaluateBadge.setTextColor(getResources().getColor(R.color.font_color_666666));
					evaluateBadge.setBackgroundDrawable(null);
				}
				
//				String collectText=personalBean.getFarvouriteNumber()+" "+this.getResources().getString(R.string.personal_collect);
//				collectView.setText(collectText);
				DVolley.getImage(personalBean.getPortraitURL(),imageView,R.drawable.personal_no_active_user_icon,true);
				levelNameView.setText(personalBean.getLevleName());
				growthvalueView.setText(personalBean.getGrowthPoint()+"");
				moneyView.setText("余额："+personalBean.getMoney());
//				pointView.setText("积分："+personalBean.getPoint());
				return;
			}
		}
		showShortToast(message);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			if(requestCode==2){
				//上传图片返回
				if(data==null){
					return;
				}
				String imageUrl=data.getStringExtra("imageUrl");
				if(imageUrl!=null&&!imageUrl.equals("")){
					DVolley.getImage(imageUrl,imageView,R.drawable.personal_no_active_user_icon,true);
				}
			}
		}
	}
}
