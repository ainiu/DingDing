package com.baidu.dingding.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.YcdAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.YcdModel;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.MyView.MyGridView;
import com.baidu.dingding.view.ShangpingtwoActivity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment implements DResponseListener {


    View view;
    YcdModel ycdModel;
    List<FenLei> list = new ArrayList<FenLei>();
    YcdAdapter ycdAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ycdModel = new YcdModel(getActivity());
        ycdModel.addResponseListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag, container, false);
        initData();
        initView();
        initLener();
        return view;
    }

    private void initLener() {
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FenLei fen = list.get(position);
                //Toasttool.MyToast(getActivity(), position + ""+fen.getCategoryId()+fen.getName());
                Intent intent=new Intent(getActivity(), ShangpingtwoActivity.class);
                Bundle bu = new Bundle();
                bu.putInt("position",position);
                bu.putString("name", fen.getName());
                bu.putString("categoryId", fen.getCategoryId());
                intent.putExtras(bu);
                startActivity(intent);
            }
        });
    }

    String ycdId;

    private void initData() {
        Bundle mBundle = getArguments();
        ycdId = mBundle.getString("ycdId");
        LogUtil.i("接受原产地id","ycdId="+ycdId);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!ycdId.isEmpty()) {
            ycdModel.findConsultList(ycdId);
        }
    }

    private MyGridView myGridView;

    private void initView() {
        myGridView = (MyGridView) view.findViewById(R.id.gridView1);
        ycdAdapter = new YcdAdapter(getActivity(), list);
        myGridView.setAdapter(ycdAdapter);
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("原产地址回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_GET) {
                //查询所有
                list = (ArrayList<FenLei>) bean.getObject();
                ycdAdapter.setBeanList(list);
                ycdAdapter.notifyDataSetChanged();
            }
        }
    }
}
