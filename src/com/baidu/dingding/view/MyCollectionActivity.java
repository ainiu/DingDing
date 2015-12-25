package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.CheckCollectionAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MyGerenxinxibaocuunModel;
import com.baidu.dingding.code.model.MyShoucangjiaModel;
import com.baidu.dingding.entity.CollectionEntity;
import com.baidu.dingding.fragment.ShopingXQ;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionActivity extends BaseActivity implements DResponseListener ,CheckCollectionAdapter.Imgclickitemid{

    GridView gridView;
    MyShoucangjiaModel myShoucangjiaModel;
    MyGerenxinxibaocuunModel myGerenxinxibaocuunModel;
    List<CollectionEntity> list = new ArrayList<CollectionEntity>();
    CheckCollectionAdapter checkCollectionAdapter;
    Boolean isShowDelete=false;
    Button btn;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initModel();
        initView();
        initLener();
    }

    private void initLener() {
        checkCollectionAdapter.addListener(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }
                checkCollectionAdapter.setIsShowDelete(isShowDelete);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isShowDelete) {
                    String goodsId = list.get(position).getGoodsId();
                    Intent intent = new Intent(MyCollectionActivity.this, ShopingXQ.class);
                    intent.putExtra("goodsId", goodsId);
                    startActivity(intent);
                }
            }
        });
        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                *//*if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                }
                checkCollectionAdapter.setIsShowDelete(isShowDelete);
                Toasttool.MyToast(MyCollectionActivity.this, "" + position);*//*
                return true;
            }
        });*/
    }
    String userNumber;

    @Override
    protected void onResume() {
        super.onResume();
        userNumber = (String) SharedPreferencesUtils.get(this, "userNumber", "");
        myShoucangjiaModel.findConsultList(userNumber);
        progressDialog.show();
    }



    private void initModel() {
        myShoucangjiaModel = new MyShoucangjiaModel(this);
        myShoucangjiaModel.addResponseListener(this);
        myGerenxinxibaocuunModel=new MyGerenxinxibaocuunModel(this);
        myGerenxinxibaocuunModel.addResponseListener(this);
    }

    private void initView() {
        progressDialog=new ProgressDialog(this);
        gridView = (GridView) this.findViewById(R.id.mycollection_girdView_01);
        checkCollectionAdapter = new CheckCollectionAdapter(this, list);
        gridView.setAdapter(checkCollectionAdapter);
        btn= (Button) this.findViewById(R.id.mycollection_button_01);
    }

    public void doClick(View view) {
        this.finish();
    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        if (error != null) {
            Toasttool.MyToastLong(this, error.getMessage()+message);
            return;
        }

        if (result == DVolleyConstans.RESULT_OK) {
            if(bean.getType()==DVolleyConstans.GERENXINXIBAOCUN){
                Toasttool.MyToastLong(this, "删除成功");
                checkCollectionAdapter.notifyDataSetChanged();
            }
            if (bean.getType() == DVolleyConstans.SHOUCHANGLIEBIAO) {
                list = (List<CollectionEntity>) bean.getObject();
                checkCollectionAdapter.setBeanList(list);
                checkCollectionAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getPostion(int postion) {
        // Toasttool.MyToast(this, "" + postion);
        CollectionEntity collectionEntity= (CollectionEntity) list.get(postion);
        String token = (String) SharedPreferencesUtils.get(this, "token", "");
        myGerenxinxibaocuunModel.findConsultList(token,userNumber,collectionEntity.getGoodsCollectionId());
        progressDialog.show();
    }
}
