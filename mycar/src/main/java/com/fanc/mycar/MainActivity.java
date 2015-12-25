package com.fanc.mycar;
/**用户选择登录界面*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fanc.mycar.view.LoginPeopleActivity;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn_sj,btn_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        btn_sj.setOnClickListener(this);
        btn_user.setOnClickListener(this);
    }

    private void initView() {
        btn_sj= (Button) this.findViewById(R.id.main_button_01);
        btn_user= (Button) this.findViewById(R.id.main_button_02);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //司机
            case R.id.main_button_01:
                Intent intent1=new Intent();
                intent1.setClass(this, LoginPeopleActivity.class);
                this.startActivity(intent1);
                break;
            //个人
            case R.id.main_button_02:
                Intent intent2=new Intent();
                intent2.setClass(this, LoginPeopleActivity.class);
                this.startActivity(intent2);
                break;
        }
    }
}
