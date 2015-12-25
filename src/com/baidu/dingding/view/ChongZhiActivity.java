package com.baidu.dingding.view;
/**
 * ��������
 * */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.HttpUtils;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONObject;

public class ChongZhiActivity extends Activity {
	private static final int HONGZHI_SUCCESS = 0;
	private EditText yanZheng,password,passed;
	private Button button;
	private ImageView imageView;
	private String  ZHAOHUI_CODE,ZHAOHUI_PWD,ZHAOHUI_PED,result,senderContent;
	
	private String chongzhi_STAUS,chongzhi_MSG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chong_zhi);
		//��ʼ���ؼ�
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		yanZheng = (EditText) findViewById(R.id.chongzhi_edit_01);
		password = (EditText) findViewById(R.id.chongzhi_edit_02);
		passed = (EditText) findViewById(R.id.chongzhi_edit_03);
		button = (Button) findViewById(R.id.chongzhi_button_01);
	}

	public void doClick(View view) {
		
		
		switch (view.getId()) {
		
		case R.id.chongzhi_button_01:
			//��ȡ�û���������
			getInput();
			if(ZHAOHUI_CODE.isEmpty()){
				Toast.makeText(getApplication(), "��������֤��", Toast.LENGTH_SHORT).show();
				return ;
			}
			if(ZHAOHUI_PWD.isEmpty()){
				Toast.makeText(getApplication(), "����������", Toast.LENGTH_SHORT).show();
				return ;
			}
			if(ZHAOHUI_PED.isEmpty()){
				Toast.makeText(getApplication(), "������ȷ������", Toast.LENGTH_SHORT).show();
				return ;
			}
			if(!ZHAOHUI_PWD.equals(ZHAOHUI_PED)){
				Toast.makeText(getApplication(), "���벻һ��", Toast.LENGTH_SHORT).show();
				return ;
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						result = HttpUtils.getZhPost(Consts.CHONGZHI, senderContent,ZHAOHUI_CODE, ZHAOHUI_PWD, ZHAOHUI_PED);
						LogUtil.i("���÷��ؽ��-->", "result="+result);
						JSONObject jsonObject =new JSONObject(result);
						chongzhi_STAUS = (String) jsonObject.get("result");
						chongzhi_MSG = (String) jsonObject.get("content");
						LogUtil.i("��������", "���ZHAOHUIMIMA_STAUS="+chongzhi_STAUS+"chongzhi_MSG="+chongzhi_MSG);
					} catch (Exception e) {
						// TODO: handle exception
						ExceptionUtil.handleException(e);
					}
				}
			}).start();
			
			Toast.makeText(getApplication(), chongzhi_MSG, Toast.LENGTH_LONG).show();
			
			if("0".equals(chongzhi_STAUS)){
				Intent intent = new Intent(ChongZhiActivity.this, MainActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.chongzhi_image_01:
			finish();
		}
	}

	private void getInput() {
		
		// TODO Auto-generated method stub
		Intent it=getIntent();
		senderContent=it.getStringExtra("senderContent");
		 ZHAOHUI_CODE=yanZheng.getText().toString();
		 ZHAOHUI_PWD=password.getText().toString();
		 ZHAOHUI_PED=passed.getText().toString();
		 LogUtil.i("��������", "senderContent="+senderContent+"ZHAOHUI_CODE="+ZHAOHUI_CODE+"ZHAOHUI_PWD="+"ZHAOHUI_PED");
	}



}
