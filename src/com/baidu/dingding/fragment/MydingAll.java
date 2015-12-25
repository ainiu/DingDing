package com.baidu.dingding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.MydingAllAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.MydindanModel;
import com.baidu.dingding.entity.DingdanEntity;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25. 全部
 */
public class MydingAll extends Fragment implements DResponseListener {
    private View view;
    private PullToRefreshListView pullToRefreshListView;
    private int currentPage=1;  //默认是一
    private MydindanModel mydindanModel;
    private String token="",userNumber="";
    private int orderStatus=5;
    private List<DingdanEntity> contentList=new ArrayList<DingdanEntity>();
    private MydingAllAdapter mydingAllAdapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_mydingall,container,false);
        initData();
        initModel();
        initView();
        initLener();
        return view;
    }

    private void initData() {
        token= (String) SharedPreferencesUtils.get(getActivity(), "token", "");
        userNumber= (String) SharedPreferencesUtils.get(getActivity(),"userNumber","");
    }


    private void initModel() {
        mydindanModel= new MydindanModel(getActivity());
        mydindanModel.addResponseListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mydindanModel.findConsultList(userNumber, token, orderStatus, currentPage);
    }

    private void initLener() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                currentPage = 1;
                mydindanModel.findConsultList(userNumber, token, orderStatus, currentPage);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                mydindanModel.findConsultList(userNumber, token, orderStatus, currentPage);
            }
        });
    }

    private void initView() {
        pullToRefreshListView= (PullToRefreshListView) view.findViewById(R.id.pull_list);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH); //设置下拉刷新
        ILoadingLayout startLabelsup = pullToRefreshListView .getLoadingLayoutProxy(true, false);
        startLabelsup.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabelsup.setRefreshingLabel("正在载入...");// 刷新时
        startLabelsup.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout startLabels = pullToRefreshListView .getLoadingLayoutProxy(false, true);
        startLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
        mydingAllAdapter=new MydingAllAdapter(getActivity(),contentList);
        listView = pullToRefreshListView.getRefreshableView();
        listView.setAdapter(mydingAllAdapter);
       // mydingAllAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);

        //错误处理
        if (error != null) {
            Toasttool.MyToast(getActivity(),error.getMessage());
            return;
        }

        //成功处理
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.METHOD_GEREN_DINGDAN) {
                //查询订单
                try {
                    contentList= (List<DingdanEntity>) bean.getObject();
                    mydingAllAdapter.setBeanList(contentList);
                    mydingAllAdapter.notifyDataSetChanged();

                    /*if (currentPage==1){
                        mydingAllAdapter.clearAllList();
                    }*/
                    if(contentList!=null&&contentList.size()!=0){
                        currentPage++;
                    }
                    pullToRefreshListView.onRefreshComplete();
                } catch (Exception e) {
                    LogUtil.i("适配器更新失败", "");
                    ExceptionUtil.handleException(e);
                }
            }
        }


    }


}
