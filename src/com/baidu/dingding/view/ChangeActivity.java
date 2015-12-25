package com.baidu.dingding.view;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxibaocuunModel;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeActivity extends BaseActivity implements DResponseListener {

    EditText oldPassword;
    EditText newPassword;
    EditText password;
    Button button;
    MyGerenxinxibaocuunModel myGerenxinxibaocuunModel;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        initModel();
        initView();
        initLenar();
    }

    private void initLenar() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chengpass();
            }
        });
    }

    private void chengpass() {
        String jiumima=oldPassword.getText().toString().trim();
        String newpass=newPassword.getText().toString().trim();
        String querenpassword=password.getText().toString().trim();
        if(jiumima.equals("")){
            Toasttool.MyToast(ChangeActivity.this,"请输入原密码");
            return;
        }
        if(newpass.equals("")){
            Toasttool.MyToast(ChangeActivity.this,"请输入新密码");
            return;
        }
        if(querenpassword.equals("")){
            Toasttool.MyToast(ChangeActivity.this,"请输入确认密码");
            return;
        }
        if(!querenpassword.equals(newpass)){
            Toasttool.MyToast(ChangeActivity.this,"密码不一致");
            return;
        }
        String userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        String token = (String) SharedPreferencesUtils.get(this, "token", "");
        myGerenxinxibaocuunModel.findConsultList(jiumima,newpass,querenpassword,userNumber,token);
        progressDialog.show();
    }

    private void initModel() {
        myGerenxinxibaocuunModel=new MyGerenxinxibaocuunModel(this);
        myGerenxinxibaocuunModel.addResponseListener(this);
    }

    private void initView() {
        // TODO Auto-generated method stub
        progressDialog=new ProgressDialog(this);
        oldPassword = (EditText) findViewById(R.id.change_edit_01);
        newPassword = (EditText) findViewById(R.id.change_edit_02);
        password = (EditText) findViewById(R.id.change_edit_03);
        button = (Button) findViewById(R.id.change_button_01);
    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
       LogUtil.i("回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        if(result== DVolleyConstans.RESULT_FAIL){
            Toasttool.MyToastLong(getBaseContext(), message);
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.GERENXINXIBAOCUN) {
                Toasttool.MyToastLong(this, "修改成功");
            }
        }
    }


}
