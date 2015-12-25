package com.baidu.dingding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.DianpuXQActivity;
import com.baidu.dingding.adapter.DianAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.ShopModel;
import com.baidu.dingding.entity.Dianpu;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class Fenleifargmentdianpu extends Fragment implements DResponseListener {

    private View view;
    private GridView gridView;
    private DianAdapter dianAdapter;
    private List<Dianpu> list_dian = new ArrayList<Dianpu>();
    ShopModel shopModel;


    private void initModel() {
        shopModel = new ShopModel(getActivity());
        shopModel.addResponseListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fenlei_fragment_dianpu, null);
        initModel();
        initView();
        initLener();
        return view;
    }

    private void initLener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dianpu diandan = list_dian.get(position);
                Intent intent=new Intent(getActivity(), DianpuXQActivity.class);
                intent.putExtra("shopId",diandan.getShopId());
                intent.putExtra("shopName",diandan.getShopName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shopModel.findConsultList();
    }

    private void initView() {
        gridView = (GridView) view.findViewById(R.id.fenlei_shanping_grid);
        dianAdapter = new DianAdapter(getActivity(), list_dian);
        gridView.setAdapter(dianAdapter);
    }
    /**
     * 回调处理
     */
    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        try {
            LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        } catch (Exception e) {
            LogUtil.i("界面回调出错", "");
        }
        //错误处理
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.SEARCH) {
                //查询所有
                try {
                    list_dian = (ArrayList<Dianpu>) bean.getObject();
                    dianAdapter.setBeanList(list_dian);
                    dianAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.i("适配器更新失败", "");
                    ExceptionUtil.handleException(e);
                }
            }
        }
    }
}
