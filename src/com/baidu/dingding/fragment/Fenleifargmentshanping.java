package com.baidu.dingding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.FenleiAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.FenleiModel;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.ShangpingtwoActivity;

import java.util.ArrayList;
import java.util.List;

public class Fenleifargmentshanping extends Fragment implements DResponseListener, AdapterView.OnItemClickListener {

    private View view;
    private GridView gridView;
    FenleiModel fenleiModel;
    List<FenLei> list = new ArrayList<FenLei>();
    FenleiAdapter fenleiAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        fenleiModel = new FenleiModel(getActivity());
        fenleiModel.addResponseListener(this);

    }
    @Override
    public void onResume() {
        super.onResume();
        //发请求拿数据
        fenleiModel.findConsultList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fenlei_fragment_shangpin, null);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        gridView.setOnItemClickListener(this);
    }


    private void initView() {
        gridView = (GridView) view.findViewById(R.id.fenlei_shanping_grid);
        fenleiAdapter = new FenleiAdapter(getActivity(), list);
        gridView.setAdapter(fenleiAdapter);
    }

    //回调数据结果
    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {

        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
                //查询所有
                try {
                    list = (ArrayList<FenLei>) bean.getObject();
                    fenleiAdapter.setBeanList(list);
                    fenleiAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.i("适配器更新失败", "");
                    ExceptionUtil.handleException(e);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LogUtil.i("分类点击事件", "position=" + position + "id=" + id);
        Intent intent = new Intent(getActivity(), ShangpingtwoActivity.class);
        Bundle bu = new Bundle();
        bu.putInt("postion", position);
        FenLei fenlei = list.get(position);
        bu.putString("name", fenlei.getName());
        bu.putString("categoryId", fenlei.getCategoryId());
        intent.putExtras(bu);
        startActivity(intent);
    }
}
