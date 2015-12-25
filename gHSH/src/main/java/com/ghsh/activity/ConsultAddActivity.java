package com.ghsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ConsultModel;
import com.ghsh.dialog.DProgressDialog;

/**
 * 添加商品咨询
 * */
public class ConsultAddActivity extends BaseActivity implements OnClickListener,DResponseListener {
	private TextView titleView;
	private DProgressDialog progressDialog;
	private EditText inputView;
	private Button okButton;
	private Spinner spinner;
	private CheckBox anonymousView;
	private ConsultModel consultModel;
	private String goodsID;
	private String goodsName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_goodsdetails_consult_add);
		this.initView();
		this.initListener();
		consultModel=new ConsultModel(this);
		consultModel.addResponseListener(this);
		Intent intent=getIntent();
		goodsID=intent.getStringExtra("goodsID");
		goodsName=intent.getStringExtra("goodsName");
	}

	private void initView() {
		progressDialog = new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.consult_add_title);
		spinner= (Spinner) this.findViewById(R.id.consult_add_spinner);
		inputView=(EditText) this.findViewById(R.id.consult_add_inputView);
		okButton=(Button) this.findViewById(R.id.consult_add_submit);
		anonymousView=(CheckBox) this.findViewById(R.id.consult_add_checkBox);
	}

	private void initListener() { 
		okButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==okButton){
			//提交
			String comment=inputView.getText().toString().trim();
			if(comment.equals("")){
				this.showShortToast(R.string.consult_add_input_tip);
				return;
			}
			progressDialog.show();
			consultModel.addConsult(this.getUserID(),spinner.getSelectedItemPosition()+"",goodsID,goodsName, comment,anonymousView.isChecked());
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
			if(bean.getType()==DVolleyConstans.METHOD_CONSULT_ADD){
				Intent intent = new Intent();
				setResult(Activity.RESULT_OK, intent);  
	            finish(); 
			}
		}
		this.showShortToast(message);
	}
}
