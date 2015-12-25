package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxiModel;
import com.baidu.dingding.code.model.MyGerenxinxibaocuunModel;
import com.baidu.dingding.entity.Gerenxinxi;
import com.baidu.dingding.entity.SearchSecurityEntity;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LianXiActivity extends BaseActivity implements DResponseListener{
	EditText editText_phone,editText_adress,editText_qq,editText_youbian;
	ProgressDialog progressDialog;
	MyGerenxinxiModel myLianxiModel;
	MyGerenxinxibaocuunModel myGerenxinxibaocuunModel;
	Button button;
	int opertionType=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lian_xi);
		initModel();
		initData();
		initView();
		initLener();
	}

	private void initLener() {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessge();
			}
		});
	}

	private void sendMessge() {
		String phone=editText_phone.getText().toString();
		String adress=editText_phone.getText().toString();
		String qq=editText_phone.getText().toString();
		String youbian=editText_phone.getText().toString();
		myGerenxinxibaocuunModel.findConsultList(token,userNumber,phone,adress,qq,youbian,opertionType);
		progressDialog.show();
	}

	String userNumber="";
	String token="";
	private void initData() {
		userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
		token= (String) SharedPreferencesUtils.get(this,"token","");
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLianxiModel.findConsultList(userNumber);
		progressDialog.show();
	}

	private void initModel() {
		myLianxiModel= new MyGerenxinxiModel(this);
		myLianxiModel.addResponseListener(this);
		myGerenxinxibaocuunModel=new MyGerenxinxibaocuunModel(this);
		myGerenxinxibaocuunModel.addResponseListener(this);
	}

	private void initView() {
		progressDialog=new ProgressDialog(this);
		editText_phone= (EditText) this.findViewById(R.id.lianxi_text_03);
		editText_adress= (EditText) this.findViewById(R.id.lianxi_text_05);
		editText_qq= (EditText) this.findViewById(R.id.lianxi_text_07);
		editText_youbian= (EditText) this.findViewById(R.id.lianxi_text_09);
		button= (Button) this.findViewById(R.id.lianxi_btn);
	}

	public void doClick(View view){
		this.finish();
	}


	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		LogUtil.i("联系方式", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
		progressDialog.dismiss();
		if (result == DVolleyConstans.RESULT_FAIL) {
			Toasttool.MyToastLong(this, message);
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			if(bean.getType()==DVolleyConstans.GERENXINXIBAOCUN){
				Toasttool.MyToastLong(this, "修改成功");
			}
			if (bean.getType() == DVolleyConstans.GERENXINXI) {
				 Gerenxinxi gerenxinxi = (Gerenxinxi) bean.getObject();
				editText_phone.setText(gerenxinxi.getMobile());
				editText_adress.setText(gerenxinxi.getAddress());
				editText_qq.setText(gerenxinxi.getQq());
				editText_youbian.setText(gerenxinxi.getPost());
			}
		}
	}
}
