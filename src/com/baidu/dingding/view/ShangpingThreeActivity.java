package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.FenleiAdapterThree;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.FenleiThreeModel;
import com.baidu.dingding.entity.EachBaen;
import com.baidu.dingding.fragment.ShopingXQ;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byAdministrator on 2015/10/31.
 * 分类3级
 */
public class ShangpingThreeActivity extends BaseActivity implements DResponseListener,View.OnClickListener {

    int position;
    String categoryId;
    String title_name;
    TextView tv_title;
    FenleiAdapterThree fenleiAdapterThree;
    List<EachBaen> list_Three = new ArrayList<EachBaen>();
    private int currentPage = 1; // 默认加载第一页
    private int sort=1;
    PullToRefreshListView mpulltolistView;
    private RadioButton bt1, bt2, bt3, bt4;
    private FenleiThreeModel fenleiThreeModel;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenlei_three);
        //初始化
        initModel();
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fenleiThreeModel.findConsultList(currentPage,categoryId,sort);
    }

    private void initModel() {
        fenleiThreeModel = new FenleiThreeModel(this);
        fenleiThreeModel.addResponseListener(this);
    }

    /**
     * 返回
     */
    public void doClick(View v) {
        this.finish();
    }

    private void initListener() {
        //滚动时不加载数据
        mpulltolistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ShangpingThreeActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                currentPage = 1;
                fenleiThreeModel.findConsultList(currentPage, categoryId, sort);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ShangpingThreeActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                fenleiThreeModel.findConsultList(currentPage, categoryId, sort);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EachBaen eachBaen=list_Three.get(position-1);
                Intent intent=new Intent(ShangpingThreeActivity.this, ShopingXQ.class);
                intent.putExtra("goodsId",eachBaen.getGoodsId());
                startActivity(intent);
            }
        });
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    private void initView() {
        tv_title = (TextView) this.findViewById(R.id.text_title);
        tv_title.setText(title_name);
        mpulltolistView = (PullToRefreshListView) this.findViewById(R.id.mpullto);
        mpulltolistView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelsup = mpulltolistView.getLoadingLayoutProxy(true, false);
        startLabelsup.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabelsup.setRefreshingLabel("正在载入...");// 刷新时
        startLabelsup.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout startLabels = mpulltolistView.getLoadingLayoutProxy(false, true);
        startLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
        listView = mpulltolistView.getRefreshableView();
        fenleiAdapterThree =new FenleiAdapterThree(this,list_Three);
        listView.setAdapter(fenleiAdapterThree);
        bt1 = (RadioButton) this.findViewById(R.id.bt1);
        bt2 = (RadioButton) this.findViewById(R.id.bt2);
        bt3 = (RadioButton) this.findViewById(R.id.bt3);
        bt4 = (RadioButton) this.findViewById(R.id.bt4);

    }

    private void initData() {
        try {
            position = this.getIntent().getExtras().getInt("position");
            title_name = this.getIntent().getExtras().getString("name");        //显示的标题
            categoryId = this.getIntent().getExtras().getString("categoryId");  //请求数据的参数
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
    }

    //处理数据
    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (bean.getObject()==null){
            Toasttool.MyToast(this, "没有数据了");
            mpulltolistView.onRefreshComplete();
            return;
        }
        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.SEARCH) {
                //查询所有
                try {
                    list_Three = (ArrayList<EachBaen>) bean.getObject();
                    if (currentPage==1){
                        fenleiAdapterThree.clearAllList();
                    }
                    if(list_Three!=null&&list_Three.size()!=0){
                        fenleiAdapterThree.addAllList(list_Three);
                        currentPage++;
                    }
                    mpulltolistView.onRefreshComplete();
                } catch (Exception e) {
                    LogUtil.i("适配器更新失败", "");
                    ExceptionUtil.handleException(e);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                list_Three.clear();
                fenleiThreeModel.findConsultList(currentPage,categoryId,sort);
            break;
            case R.id.bt2:
                sort=2;
                list_Three.clear();
                fenleiThreeModel.findConsultList(currentPage,categoryId,sort);
                break;
            case R.id.bt3:
                sort=3;
                list_Three.clear();
                fenleiThreeModel.findConsultList(currentPage,categoryId,sort);
                break;
            case R.id.bt4:
                sort=4;
                list_Three.clear();
                fenleiThreeModel.findConsultList(currentPage,categoryId,sort);
                break;
        }
    }
}
