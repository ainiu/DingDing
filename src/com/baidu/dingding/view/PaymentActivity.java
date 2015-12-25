package com.baidu.dingding.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.ToPayModel;
import com.baidu.dingding.entity.ToPayBean;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;
import com.baidu.dingding.zhifu.activity.ZhifuActivity;

import java.util.ArrayList;

public class PaymentActivity extends BaseActivity implements DResponseListener {
    private RelativeLayout zhifu;
    private ToPayModel toPayModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initData();
        initModel();
        initView();
        initLener();
    }

    private void initLener() {
        zhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPayModel.findConsultList(userNumber, token, GsonTools.listToString(ordernolist, ','));
                dialog.show();
            }
        });
    }

    private void initModel() {
        toPayModel = new ToPayModel(this);
        toPayModel.addResponseListener(this);
    }

    ArrayList<String> ordernolist;
    String userNumber="", token="";

    private void initData() {
        ordernolist = getIntent().getStringArrayListExtra("ordernolist");
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        token = (String) SharedPreferencesUtils.get(this, "token", "");
    }

    private Dialog dialog;

    private void initView() {
        zhifu = (RelativeLayout) this.findViewById(R.id.parme);
        dialog = MyProgressDialog.createLoadingDialog(this, this.getResources().getString(R.string.dialog_jiazai));
    }

    public void doClick(View view) {
        this.finish();
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        dialog.dismiss();
        LogUtil.i("确认请求回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        if (result == DVolleyConstans.RESULT_FAIL) {
            Toasttool.MyToastLong(this, message);
            return;
        }
        //错误处理
        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.TOPAY) {
                ToPayBean toPayBean= (ToPayBean) bean.getObject();
                try {
                    Intent intent = new Intent(this, ZhifuActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("toPayBean", toPayBean);
                    intent.putExtras(bundle);
                    LogUtil.i("准备支付数据", "toPayBean=" + toPayBean.toString());
                    this.startActivity(intent);
                    this.finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
