package com.baidu.dingding.view;

/*
 * ע�����
 * **/

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.RegisModel;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisActivity extends BaseActivity implements DResponseListener {

    private static final int MSG_SUCCESS = 0; // ��ȡͼƬ�ɹ��ı�ʶ
    private static final int MSG_FAILURE = 1; // ��ȡͼƬʧ�ܵı�ʶ
    private static final int REGIS_MSG_STATUS = 2; // ע��״̬

    private EditText editName, editCode, editPassed, editPassword;
    private Button imageButton, loginButton, regisButton, termButton;
    private CheckBox checkBox;
    private String responseMsg = "";
    private String userNumber, password, passed, code;
    private ImageView iv_yzm, iv_back;
    private String Sessionid, result;
    private ProgressDialog progressDialog;
    RegisModel regisModel;
    /**
     * @handler ���������Ϊstatic�����������ڴ�й¶
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    iv_yzm.setImageBitmap((Bitmap) msg.obj);
                    break;

                case REGIS_MSG_STATUS:
                    Toast.makeText(getApplication(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
        // ��ʼ���ؼ�
        initModel();
        setInitialize();

    }

    private void initModel() {
        regisModel = new RegisModel(this);
        regisModel.addResponseListener(this);
    }


    private void setInitialize() {
        // TODO Auto-generated method stub
        progressDialog = new ProgressDialog(this);
        LogUtil.i("ע��", "��ʼ��ʼ��");
        editName = (EditText) findViewById(R.id.regis_edit_01);
        editPassword = (EditText) findViewById(R.id.regis_edit_02);
        editPassed = (EditText) findViewById(R.id.regis_edit_03);
        editCode = (EditText) findViewById(R.id.regis_edit_04);
        imageButton = (Button) findViewById(R.id.regis_button_01);
        regisButton = (Button) findViewById(R.id.regis_button_03);
        termButton = (Button) findViewById(R.id.regis_button_02);
        loginButton = (Button) findViewById(R.id.regis_button_04);
        iv_yzm = (ImageView) findViewById(R.id.regis_image_06);
        iv_back = (ImageView) findViewById(R.id.regis_image_01);
        checkBox = (CheckBox) findViewById(R.id.regis_checkbox_01);
        // initStatus();

    }

    private String YZM_CODE, YZM_LOGPATH;

    // ��ȡ���ص����
    private String httpContent(String yANZHENG) {

        // TODO Auto-generated method stub
        try {
            URL url = new URL(yANZHENG);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            /*
             * String cookieval = connection.getHeaderField("Set-cookie"); if
			 * (cookieval != null) { Sessionid = (String)
			 * cookieval.subSequence(0, cookieval.indexOf(";")); LogUtil.i("ע��",
			 * "sessionid=" + Sessionid); }
			 */
			/*
			 * if(null!= Sessionid) { connection.setRequestProperty("Cookie",
			 * Sessionid); }
			 */
            connection.setReadTimeout(20 * 1000);
            connection.setConnectTimeout(20 * 1000);
            // ���������ͷ
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
			/*
			 * // ���ӷ����� connection.connect(); OutputStream os =
			 * connection.getOutputStream(); os.flush();
			 */
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpStatus.SC_OK) {
                InputStream response_in = connection.getInputStream();

                String cookieval = connection.getHeaderField("Set-cookie");
                if (cookieval != null) {
                    Sessionid = (String) cookieval.subSequence(0,
                            cookieval.indexOf(";"));
                    LogUtil.i("ע��", "Sessionid=" + Sessionid);
                }
                return chageInputStream(response_in);
            }

        } catch (Exception e) {
            // TODO: handle exception
            ExceptionUtil.handleException(e);
        }

        return "";

    }

    private String chageInputStream(InputStream is) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        try {
            while ((len = is.read(data)) != -1) {
                stream.write(data, 0, len);
            }
            return new String(stream.toByteArray(), "GBK");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            ExceptionUtil.handleException(e);
        } finally {
            try {
                stream.flush();
                stream.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                ExceptionUtil.handleException(e);
            }
        }
        return "";
    }

    public void doClick(View v) {
        // ��ȡ�û������ֵ
        switch (v.getId()) {
            // �����廻��ͼƬ
            case R.id.regis_button_01:

			/*LogUtil.i("ע��", "������ͼƬ");
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String url = Consts.YANZHENG;
					result = httpContent(url);
					LogUtil.i("��֤��", "result=" + result);
					// result={"message":"��","content":{"sessionId":"","logPath":"http://183.234.117.210:9090/AppServer/security/createVerifyCodeImage",
					// "code":"Dm4j"},"result":"0"}
					try {
						// ����result
						JSONObject jsonObject = (JSONObject) new JSONObject(
								result).get("content");
						YZM_LOGPATH = (String) jsonObject.get("logPath");
						YZM_CODE = (String) jsonObject.get("code");

						LogUtil.i("��֤��", "yzm_logPath=" + YZM_LOGPATH
								+ "YZM_CODE=" + YZM_CODE);
						// ���Sessionid��yzm_logPath��ȡͼƬ
						Bitmap yzm_imagview = HttpUtils.postImag(YZM_LOGPATH,
								Sessionid);
						handler.obtainMessage(MSG_SUCCESS, yzm_imagview)
								.sendToTarget();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						ExceptionUtil.handleException(e);
					}
				}

			}).start();*/

                break;

            case R.id.regis_button_02:
                Intent intent2 = new Intent(this, GongGaoActivity.class);
                startActivity(intent2);
                break;
            // ע��
            case R.id.regis_button_03:
                Regis();
                break;

            case R.id.regis_button_04:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.regis_image_01:
                finish();
                break;
        }
    }

    private void Regis() {
        userNumber = editName.getText().toString().trim();
        password = editPassed.getText().toString().trim();
        passed = editPassed.getText().toString().trim();
        code = editCode.getText().toString().trim();

        if (userNumber.isEmpty()) {
            Toast.makeText(this, "�û�����Ϊ��", Toast.LENGTH_LONG).show();
            return;
        }
		/*
		 * if(userNumber.endsWith(".com")){ Toast.makeText(this, "�����ʽ����",
		 * Toast.LENGTH_LONG).show(); return; }
		 */
        if (password.isEmpty()) {
            Toast.makeText(this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passed.isEmpty()) {
            Toast.makeText(this, "ȷ�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passed)) {
            Toast.makeText(this, "���벻һ��,����������", Toast.LENGTH_SHORT).show();
            return;
        }

        regisModel.findConsultList(userNumber, password, passed);
        progressDialog.show();
        /*if (code.isEmpty()) {
            Toast.makeText(this, "��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
            return;
        }*/
        /*if (!checkBox.isChecked()) {
            Toast.makeText(this, "��,�㻹û���Ķ���������", Toast.LENGTH_SHORT).show();
            return;
        }*/
       /* if (!code.equalsIgnoreCase(YZM_CODE)) {
            Toast.makeText(this, "��,��֤���������", Toast.LENGTH_SHORT).show();
            return;
        }*/

       /* new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String result = HttpUtils.getContentPost(Consts.REGIS,
                        userNumber, password, passed, code);
                LogUtil.i("ע��", "result=" + result);
                //result={"message":"��","content":"ע��ɹ���","result":"0"}
                try {
                    JSONObject jsonObject = (JSONObject) new JSONObject(result);
                    String REGIS_STAUS = (String) jsonObject.get("result");
                    String REGIS_MSG_SUCCESS = (String) jsonObject.get("content");
                    String REGIS_MSG_DEFILS = (String) jsonObject.get("message");
                    if ("1".equals(REGIS_STAUS)) {
                        handler.obtainMessage(REGIS_MSG_STATUS, REGIS_MSG_DEFILS)
                                .sendToTarget();
                    }
                    progressDialog.dismiss();
                    if ("0".equals(REGIS_STAUS)) {
                        handler.obtainMessage(REGIS_MSG_STATUS, REGIS_MSG_SUCCESS)
                                .sendToTarget();
                        Intent intent = new Intent();
                        intent.putExtra("userNumber", userNumber);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    ExceptionUtil.handleException(e);
                }
            }
        }).start();*/

    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("�ص�", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
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
            if (bean.getType() == DVolleyConstans.REGIS) {
                try {
                    SharedPreferencesUtils.clear(getApplication());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtils.put(getApplication(), "userNumber", userNumber);
                this.finish();

            }
        }

    }
}
