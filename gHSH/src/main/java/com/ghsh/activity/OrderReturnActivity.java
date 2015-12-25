package com.ghsh.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.CouponModel;
import com.ghsh.code.volley.model.OrderModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 订单-选择优惠劵
 * */
public class OrderReturnActivity extends BaseActivity implements DResponseListener,OnClickListener{
	private TextView titleView;
	private OrderModel orderModel;
	private DProgressDialog progressDialog; 
	private String orderID;
	private Button returnButton;
	private EditText descView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_return);
		this.initView();
		this.initListener();
		orderID=getIntent().getStringExtra("orderID");
		orderModel=new OrderModel(this);
		orderModel.addResponseListener(this);
		
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_return_title);
		
		returnButton= (Button) this.findViewById(R.id.order_return_button);
		descView= (EditText) this.findViewById(R.id.order_return_desc);
	}

	private void initListener() {
		returnButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v==returnButton){
			String desc=descView.getText().toString().trim();
			if(desc.equals("")){
				this.showShortToast("请输入退款原因");
				return;
			}
			progressDialog.show();
			orderModel.orderReturn(orderID, desc);
		}
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_ORDER_RETURN){
				//申请退款返回
				this.showShortToast(message);
				this.finish();
				return;
			}
		}
		this.showShortToast(error.getMessage());
	}
	
}
