package com.ghsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.R;
/**
 * 订单-发票
 * */
public class OrderReceiptActivity extends BaseActivity implements OnClickListener{
	private TextView titleView;
	private Button okButton;//完成按钮
	private String invoiceTitle;//发票抬头
	private EditText riseView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_receipt);
		this.initView();
		this.initListener();
		invoiceTitle=getIntent().getStringExtra("invoiceTitle");
		if(invoiceTitle==null||invoiceTitle.equals(this.getResources().getString(R.string.orderdetails_text_receipt_no))){
			invoiceTitle="";
		}
		riseView.setText(invoiceTitle);
	}
	private void initView() {
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.order_receipt_title);
		
		okButton=(Button)this.findViewById(R.id.header_button_view);
		okButton.setText(R.string.done);
		okButton.setVisibility(View.VISIBLE);
		
		riseView = (EditText) this.findViewById(R.id.receipt_rise_view);
	}
	
	private void initListener() {
		okButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v==okButton){
			String rise=riseView.getText().toString();
			Intent intent=new Intent();
			intent.putExtra("invoiceTitle", rise);
			setResult(Activity.RESULT_OK, intent);
			this.finish();
		}
	}
}
