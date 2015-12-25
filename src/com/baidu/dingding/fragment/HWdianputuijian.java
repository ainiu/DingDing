package com.baidu.dingding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.HaiwaishopingTJAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.HaiwaiTJModel;
import com.baidu.dingding.entity.HaiwaituijianBean;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.海外店铺推荐
 */
public class HWdianputuijian extends Fragment implements DResponseListener {

    HaiwaiTJModel haiwaiTJModel;
    MyGridView gridView;
    private View view;
    HaiwaishopingTJAdapter haiwaishopingTJAdapter;
    List<HaiwaituijianBean> list = new ArrayList<HaiwaituijianBean>();
    String shopId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        shopId = bundle.getString("shopId");
        //LogUtil.i("商品详情店铺推荐接受id", "shopId=" + shopId);
    }


    private void initModel() {
        haiwaiTJModel = new HaiwaiTJModel(getContext());
        haiwaiTJModel.addResponseListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fenlei_fragment_dianpu, container, false);
        initModel();
        initView();
        initlener();
        return view;
    }

    private void initlener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toasttool.MyToast(getActivity(),position+"");
                HaiwaituijianBean haiwaituijianBean =list.get(position);
                Intent intent=new Intent(getActivity(),ShopingXQ.class);
                intent.putExtra("goodsId",haiwaituijianBean.getGoodsId());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        gridView = (MyGridView) view.findViewById(R.id.fenlei_shanping_grid);
        haiwaishopingTJAdapter = new HaiwaishopingTJAdapter(getContext(), list);
        gridView.setAdapter(haiwaishopingTJAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        haiwaiTJModel.findConsultList(shopId);
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("商品详情店铺推荐", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toasttool.MyToast(getActivity(),error.getMessage());
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.HAIWAIXIANGQINGDIANPUTUIJIAN) {
                //查询所有
                try {
                    list = (List<HaiwaituijianBean>) bean.getObject();
                    haiwaishopingTJAdapter.setBeanList(list);
                    haiwaishopingTJAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.i("适配器更新失败", "");
                    ExceptionUtil.handleException(e);
                }
            }
        }
    }

}
