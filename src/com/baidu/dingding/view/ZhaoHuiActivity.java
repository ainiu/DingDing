package com.baidu.dingding.view;

/*
 * 找回密码
 * 
 * **/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.CallbackModel;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

public class ZhaoHuiActivity extends BaseActivity implements DResponseListener{
	private ImageView imageView;
	private Button button1;
	private EditText et_Name;
	private String userName,PHONE_EMAL_TYPE,ZHAOHUIMIMA_STAUS,ZHAOHUI_MSG;
	CallbackModel callbackModel;
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhao_hui);
		// 初始化控件
		initModel();
		initView();
	}

	private void initModel() {
		callbackModel=new CallbackModel(this);
		callbackModel.addResponseListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		progressDialog=new ProgressDialog(this);
		imageView = (ImageView) findViewById(R.id.zhaohui_image_01);
		button1 = (Button) findViewById(R.id.zhaohui_button_01);
		et_Name=(EditText) findViewById(R.id.zhaohui_edit_01);
	}

	public void doClick(View view) {
		userName=et_Name.getText().toString();

		switch (view.getId()) {
		case R.id.zhaohui_button_01:
			if(userName.isEmpty()){
				Toast.makeText(getApplication(), "请输入账户", Toast.LENGTH_SHORT).show();
				return;
			}
			callbackModel.findConsultList(userName);
			progressDialog.show();
			//向服务器提交数据
			//然后判断返回的数据
			/*new Thread(new Runnable() {
				
				@Override
				public  void run() {
					// TODO Auto-generated method stub
					String result = HttpUtils.getContentPost(Consts.ZHAOHUI,userName);
					LogUtil.i("找回密码", "result="+result);
					//result={"message":"无","content":"验证码发送成功！","result":"0"}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						JSONObject jsonObject = (JSONObject) new JSONObject(result);
						ZHAOHUIMIMA_STAUS = (String) jsonObject.get("result");
						LogUtil.i("找回密码", "ZHAOHUIMIMA_STAUS="+ZHAOHUIMIMA_STAUS);
						ZHAOHUI_MSG = (String) jsonObject.get("content");
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						ExceptionUtil.handleException(e);
					}
				}
			}).start();
			
			Toast.makeText(ZhaoHuiActivity.this, ZHAOHUI_MSG,
					Toast.LENGTH_LONG).show();
			//如果返回结果为0
			if ("0".equals(ZHAOHUIMIMA_STAUS)) {
				Intent intent = new Intent(ZhaoHuiActivity.this,
						ChongZhiActivity.class);
				intent.putExtra("senderContent", userName);
				startActivity(intent);
			}*/
			break;
		case R.id.zhaohui_image_01:
			finish();
			break;
		}
	}


	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		LogUtil.i("找回密码", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
		progressDialog.dismiss();
		if (error != null) {
			Toasttool.MyToastLong(this, error.getMessage() + message);
			return;
		}
		if (result == DVolleyConstans.RESULT_FAIL) {
			Toasttool.MyToastLong(this, message);
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			if (bean.getType() == DVolleyConstans.CALLBACKPASS) {
				Intent intent = new Intent(ZhaoHuiActivity.this,
						ChongZhiActivity.class);
				intent.putExtra("senderContent", userName);
				startActivity(intent);
				finish();
			}
		}
	}
}
