package com.baidu.dingding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.ShopingXQAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.SearchModel;
import com.baidu.dingding.entity.EachBaen;
import com.baidu.dingding.fragment.ShopingXQ;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.ProgrressDialog.ProgressDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺详情
 */
public class DianpuXQActivity extends BaseActivity implements DResponseListener {
    private PullToRefreshListView pullToRefreshListView;
    private PullToRefreshGridView pullToRefreshGridView;
    public  boolean TAB = true;
    SearchModel searchModel;
    Button qiehuan;
    TextView tv_name;
    ShopingXQAdapter shopingXQAdapter;
    List<EachBaen> search_list = new ArrayList<EachBaen>();
    private int currentPage = 1;
    ProgressDialog progressDialog;
    ListView listView;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpuxq);
        initModel();
        initData();
        initView();
        setlistener();
    }

    String shopName, shopId;

    private void initData() {
        shopName = getIntent().getStringExtra("shopName");
        shopId = getIntent().getStringExtra("shopId");
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchModel.findConsultList(shopId, currentPage);
        progressDialog.show();
    }

    private void initModel() {
        searchModel = new SearchModel(this);
        searchModel.addResponseListener(this);
    }


    private void setlistener() {
        qiehuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TAB = !TAB;
                updateLayout();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EachBaen earch = search_list.get(position - 1);
                Intent intent = new Intent(DianpuXQActivity.this, ShopingXQ.class);
                intent.putExtra("goodsId", earch.getGoodsId());
                startActivity(intent);
            }
        });
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toasttool.MyToast(DianpuXQActivity.this,"position="+position);
                EachBaen earch = search_list.get(position);
                Intent intent = new Intent(DianpuXQActivity.this, ShopingXQ.class);
                intent.putExtra("goodsId", earch.getGoodsId());
                startActivity(intent);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(DianpuXQActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                currentPage = 1;
                searchModel.findConsultList(shopId, currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(DianpuXQActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                searchModel.findConsultList(shopId, currentPage);
            }
        });
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(DianpuXQActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                currentPage = 1;
                searchModel.findConsultList(shopId, currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(DianpuXQActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                searchModel.findConsultList(shopId, currentPage);
            }
        });
    }

    private void initView() {
        // TODO Auto-generated method stub
        progressDialog = new ProgressDialog(this);
        tv_name = (TextView) this.findViewById(R.id.zhaohui_text_01);
        tv_name.setText(shopName);
        qiehuan = (Button) this.findViewById(R.id.xianshi_qiehuan);
        shopingXQAdapter=new ShopingXQAdapter(this,search_list);

        pullToRefreshListView = (PullToRefreshListView) this.findViewById(R.id.dianpu_listView);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelsuplist = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        startLabelsuplist.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabelsuplist.setRefreshingLabel("正在载入...");// 刷新时
        startLabelsuplist.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout startLabelslist = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        startLabelslist.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        startLabelslist.setRefreshingLabel("正在载入...");// 刷新时
        startLabelslist.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
        listView=pullToRefreshListView.getRefreshableView();
        listView.setAdapter(shopingXQAdapter);

        pullToRefreshGridView = (PullToRefreshGridView) this.findViewById(R.id.dianpu_gridView);
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelsup = pullToRefreshGridView.getLoadingLayoutProxy(true, false);
        startLabelsup.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabelsup.setRefreshingLabel("正在载入...");// 刷新时
        startLabelsup.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout startLabels = pullToRefreshGridView.getLoadingLayoutProxy(false, true);
        startLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
        gridView=pullToRefreshGridView.getRefreshableView();
        gridView.setAdapter(shopingXQAdapter);
        pullToRefreshGridView.setVisibility(View.GONE);

    }

    public void doClick(View v) {
        this.finish();
    }
    /*private Swich swich;
    public interface Swich {
        void getPostion(boolean isShow);
    }
    public void addListener(Swich swich){
        this.swich = swich;
    }*/

    private void updateLayout() {
        if (TAB) {
            shopingXQAdapter.setIsShowDelete(TAB);
            pullToRefreshListView.setVisibility(View.VISIBLE);
            pullToRefreshGridView.setVisibility(View.GONE);
            shopingXQAdapter.notifyDataSetChanged();
        } else {
            shopingXQAdapter.setIsShowDelete(TAB);
            pullToRefreshListView.setVisibility(View.GONE);
            pullToRefreshGridView.setVisibility(View.VISIBLE);
            shopingXQAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("限时抢购", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        progressDialog.dismiss();
        if (error != null) {
            Toasttool.MyToast(this, error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.SEARCH) {
                search_list = (List<EachBaen>) bean.getObject();
                if (currentPage==1){
                    shopingXQAdapter.clearAllList();
                }
                if(search_list!=null&&search_list.size()!=0){
                    shopingXQAdapter.addAllList(search_list);
                    currentPage++;
                }
                pullToRefreshGridView.onRefreshComplete();
                pullToRefreshListView.onRefreshComplete();
            }
        }
    }



}
