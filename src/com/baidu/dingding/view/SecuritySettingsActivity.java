package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxibaocuunModel;
import com.baidu.dingding.code.model.SearchSecurityModel;
import com.baidu.dingding.entity.SearchSecurityEntity;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 安全设置
 */
public class SecuritySettingsActivity extends BaseActivity implements DResponseListener {
    EditText editText_wenti, editText_daan, editText_email;
    Button btn;
    ProgressDialog progressDialog;
    MyGerenxinxibaocuunModel myGerenxinxibaocuunModel;
    SearchSecurityModel searchSecurityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
        initData();
        initModel();
        initView();
        initLener();
    }

    private void initData() {
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
    }

    private void initModel() {
        myGerenxinxibaocuunModel = new MyGerenxinxibaocuunModel(this);
        myGerenxinxibaocuunModel.addResponseListener(this);

        searchSecurityModel = new SearchSecurityModel(this);
        searchSecurityModel.addResponseListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        searchSecurityModel.findConsultList(userNumber);
    }

    private void initLener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });
    }

    private void setting() {
        String wenti = editText_wenti.getText().toString();
        String daan = editText_daan.getText().toString();
        String email = editText_email.getText().toString();
        if (wenti.equals("")) {
            Toasttool.MyToast(SecuritySettingsActivity.this, "请输入问题");
            return;
        }
        if (daan.equals("")) {
            Toasttool.MyToast(SecuritySettingsActivity.this, "请输入答案");
            return;
        }

        String token = (String) SharedPreferencesUtils.get(this, "token", "");

        myGerenxinxibaocuunModel.findConsultList(wenti, daan, userNumber, token);
        progressDialog.show();
    }

    String userNumber="";

    private void initView() {
        progressDialog = new ProgressDialog(this);
        editText_wenti = (EditText) this.findViewById(R.id.setting_edit_01);
        editText_daan = (EditText) this.findViewById(R.id.setting_edit_02);
        editText_email = (EditText) this.findViewById(R.id.setting_edit_03);
        btn = (Button) this.findViewById(R.id.setting_button_01);
    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        if (result == DVolleyConstans.RESULT_FAIL) {
            Toasttool.MyToastLong(getBaseContext(), message);
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.GERENXINXIBAOCUN) {
                Toasttool.MyToastLong(this, "设置成功");
            }
            if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
                SearchSecurityEntity searchSecurityEntity = (SearchSecurityEntity) bean.getObject();
                editText_wenti.setText(searchSecurityEntity.getPasswordQuestion());
                editText_daan.setText(searchSecurityEntity.getPasswordAnswer());
                editText_email.setText(searchSecurityEntity.getUserEmail());
            }
        }
    }
}
