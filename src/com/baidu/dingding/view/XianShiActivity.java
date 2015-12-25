package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.XianshiAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.XianshiModel;
import com.baidu.dingding.entity.XianShiEntity;
import com.baidu.dingding.fragment.ShopingXQ;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ʱ����
 */
public class XianShiActivity extends BaseActivity implements  DResponseListener {


    private ListView listview;
    private GridView gridView;
    public static boolean TAB = true;
    XianshiModel xianshiModel;
    List<XianShiEntity> xsDB = new ArrayList<XianShiEntity>();
    XianshiAdapter xianshiAdapter;
    ImageView qiehuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xian_shi);
        initModel();
        initView();
        setlistener();
    }

    private void initModel() {
        xianshiModel = new XianshiModel(this);
        xianshiModel.addResponseListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        xianshiModel.findConsultList();
    }

    private void setlistener() {
        qiehuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TAB = !TAB;
                updateLayout();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XianShiEntity xianShiEntity=xsDB.get(position);
                Intent intent=new Intent(XianShiActivity.this, ShopingXQ.class);
                intent.putExtra("goodsId", xianShiEntity.getGoodsId());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        listview = (ListView) this.findViewById(R.id.xianshi_listView);
        gridView = (GridView) this.findViewById(R.id.xianshi_gridView);
        qiehuan= (ImageView) this.findViewById(R.id.xianshi_qiehuan);
        xianshiAdapter = new XianshiAdapter(this, xsDB);
        listview.setAdapter(xianshiAdapter);
    }

    public void doClick(View v) {
        this.finish();
    }


    private void updateLayout() {
        if (TAB) {
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(xianshiAdapter);
            gridView.setVisibility(View.GONE);
            xianshiAdapter.notifyDataSetChanged();

        } else {
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(xianshiAdapter);
            listview.setVisibility(View.GONE);
            xianshiAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("限时抢购", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_XIANSHI) {
                xsDB = (List<XianShiEntity>) bean.getObject();
                try {
                    xianshiAdapter.setBeanList(xsDB);
                    xianshiAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    ExceptionUtil.handleException(e);
                }

            }
        }
    }
}
