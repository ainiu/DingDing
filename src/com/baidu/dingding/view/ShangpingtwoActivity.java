package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.FenleiAdapterTwo;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.FenleiTwoModel;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * categoryId
 * Created by Administrator on 2015/10/30.
 */
public class ShangpingtwoActivity extends BaseActivity implements DResponseListener, AdapterView.OnItemClickListener {

    int position;
    String categoryId;
    String title_name;
    GridView gridView;
    ImageView imageView;
    TextView tv_title;
    FenleiTwoModel fenleiTwoModel;
    List<FenLei> list=new ArrayList<FenLei>();
   /*** Context context; 传这个还更新不了*/
    FenleiAdapterTwo fenleiAdapterTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenlei_two);
        initModel();
        initData();
        initView();
        initLinen();
    }

    /**
     * 初始化model
     */
    private void initModel() {
        fenleiTwoModel = new FenleiTwoModel(this);
        fenleiTwoModel.addResponseListener(this);

    }
    /**请求数据*/
    @Override
    protected void onResume() {
        super.onResume();
        if (categoryId.isEmpty()) {
            Toast.makeText(this, "请稍等", Toast.LENGTH_LONG).show();
        }
        try {
            fenleiTwoModel.findConsultList(categoryId);
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
             position = this.getIntent().getExtras().getInt("position");
            title_name = this.getIntent().getExtras().getString("name");        //显示的标题
            categoryId = this.getIntent().getExtras().getString("categoryId");  //请求数据的参数
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }

    }

    /**
     * 监听
     */
    private void initLinen() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView.setOnItemClickListener(this);
    }

    /**
     * 初始化
     */
    private void initView() {
        tv_title = (TextView) this.findViewById(R.id.text_title);
        tv_title.setText(title_name);
        gridView = (GridView) this.findViewById(R.id.fenlei_two_grid);
        imageView = (ImageView) this.findViewById(R.id.image_01);
        try {
            fenleiAdapterTwo=new FenleiAdapterTwo(this,list);
            gridView.setAdapter(fenleiAdapterTwo);
        }catch (Exception e){
            LogUtil.i("适配器出错了","");
            ExceptionUtil.handleException(e);
        }
    }

    /**
     * 界面回调
     */
    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        //成功处理

        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_QUERYALL) {
                //更新界面
                try {
                    list = (ArrayList<FenLei>) bean.getObject();
                    fenleiAdapterTwo.setBeanList(list);
                    fenleiAdapterTwo.notifyDataSetChanged();
                }catch (Exception e){
                    LogUtil.i("适配器更新失败","");
                    ExceptionUtil.handleException(e);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.i("分类点击事件", "position=" + position + "id=" + id);
        Intent intent = new Intent().setClass(ShangpingtwoActivity.this, ShangpingThreeActivity.class);
        Bundle bu = new Bundle();
        bu.putInt("postion", position);
        FenLei fenlei = list.get(position);
        bu.putString("name", fenlei.getName());
        bu.putString("categoryId", fenlei.getCategoryId());
        intent.putExtras(bu);
        this.startActivity(intent);
    }
}
