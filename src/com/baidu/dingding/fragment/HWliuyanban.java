package com.baidu.dingding.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.LiuyanbanModel;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

/**
 * Created by Administrator on 2015/11/10.海外留言板
 */
public class HWliuyanban extends Fragment implements DResponseListener {

    View view;
    EditText et_body;
    CheckBox checkBox;
    Button btn_clear, btn_sub;
    TextView textView;
    private LiuyanbanModel liuyanbanModel;
    String token;
    String userNumber;
    ProgressDialog progressDialog;

    private void initData() {
        try {
            token = (String) SharedPreferencesUtils.get(getActivity(), "token", "");
            userNumber = (String) SharedPreferencesUtils.get(getActivity(), "userNumber", "");
            LogUtil.i("卖家留言", "token=" + token + "userNumber=" + userNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            goodsId = bundle.getString("goodsId");
        }
    }

    String goodsId;

    private void initModel() {
        liuyanbanModel = new LiuyanbanModel(getContext());
        liuyanbanModel.addResponseListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.haiwai_liuyanban, container, false);
        initData();
        initModel();
        initView();
        initLinener();
        return view;
    }


    private void initLinener() {
        //清空
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_body.setText("");
            }
        });
        //提交
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_body.length() == 0) {
                    Toasttool.MyToast(getActivity(), "留言不能为空");
                    return;
                }
                if (token.isEmpty()) {
                    // Toasttool.MyToast(getActivity(),"你还没有登录");
                    islogindialog();
                }
                String concent = et_body.getText().toString();
                try {
                    liuyanbanModel.findConsultList(goodsId, userNumber, concent, token);
                    progressDialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                    islogindialog();
                }
            }
        });
    }

    private void islogindialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("请选择是否登录");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        et_body = (EditText) view.findViewById(R.id.liuyanban_editText);
        checkBox = (CheckBox) view.findViewById(R.id.liuyanban_check);
        btn_clear = (Button) view.findViewById(R.id.liuyanban_btn_clear);
        btn_sub = (Button) view.findViewById(R.id.liuyanban_btn_submit);
        textView = (TextView) view.findViewById(R.id.liuyanban_jubao);
    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        //错误处理
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        if (result == 1) {
            islogindialog();
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.HAIWAILIUYANBAN) {
                Toasttool.MyToast(getActivity(), "收藏成功");
            }
        }
    }
}
