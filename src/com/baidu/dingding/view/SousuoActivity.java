package com.baidu.dingding.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.baidu.dingding.R;
import com.baidu.dingding.activity.BaseActivity;
import com.baidu.dingding.adapter.SousuoAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.SearchModel;
import com.baidu.dingding.entity.EachBaen;
import com.baidu.dingding.fragment.ShopingXQ;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/*
 * �0<x<101
 * **/
public class SousuoActivity extends BaseActivity implements SearchView.OnQueryTextListener ,DResponseListener{

	private SearchView sv;
	private Button soushuo_finlish;
	private PullToRefreshListView mpulltolistView;
	private int currentPage = 1;
	private String operationType = ""+1;
	private ListView listView;
	private SousuoAdapter sousuoAdapter;
	List<EachBaen> search_list=new ArrayList<EachBaen>();
	SearchModel searchModel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sousuo);
		initModel();
		initView();
		setlientenr();
	}

	private void initModel() {
		searchModel=new SearchModel(this);
		searchModel.addResponseListener(this);
	}
	private void setlientenr() {
		mpulltolistView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(SousuoActivity.this, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						currentPage = 1;
						searchModel.findConsultList(operationType, currentPage, input);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(SousuoActivity.this, System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						searchModel.findConsultList(operationType, currentPage, input);
					}
				});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				EachBaen search = search_list.get(position-1);
				Intent intent=new Intent(SousuoActivity.this, ShopingXQ.class);
				intent.putExtra("goodsId",search.getGoodsId());
				startActivity(intent);
			}
		});
		// finsh
		soushuo_finlish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		sv = (SearchView) this.findViewById(R.id.sousuo_image_each);
		sv.setIconifiedByDefault(false);
		sv.setOnQueryTextListener(this);
		sv.setSubmitButtonEnabled(false);
		sv.setQueryHint("搜索");
		soushuo_finlish = (Button) this.findViewById(R.id.soushuo_finlish);
		mpulltolistView = (PullToRefreshListView) this.findViewById(R.id.pull_list);
		mpulltolistView.setMode(Mode.BOTH);
		ILoadingLayout startLabelsup = mpulltolistView .getLoadingLayoutProxy(true, false);
		startLabelsup.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabelsup.setRefreshingLabel("正在载入...");// 刷新时
		startLabelsup.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout startLabels = mpulltolistView .getLoadingLayoutProxy(false, true);
		startLabels.setPullLabel("加载更多...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示
		listView=mpulltolistView.getRefreshableView();
		sousuoAdapter=new SousuoAdapter(this,search_list);
		listView.setAdapter(sousuoAdapter);
	}

	String input="";
	@Override
	public  boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		if (Integer.parseInt(query) > 100 || Integer.parseInt(query) < -100) {
			Toasttool.MyToast(this,"请输入合法字符");
			return false;
		} else {
			input=query;
			searchModel.findConsultList(operationType,currentPage,input);
		}
		return true;
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
		LogUtil.i("界面回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
		//错误处理
		if (error != null) {
			Toasttool.MyToast(this, error.getMessage());
			return;
		}
		if (result == DVolleyConstans.RESULT_OK) {
			if (bean.getType() == DVolleyConstans.SEARCH) {
				try {
					search_list = (List<EachBaen>) bean.getObject();
					/*if (currentPage==1){
						sousuoAdapter.clearAllList();
					}*/
					if(search_list!=null&&search_list.size()!=0){
						sousuoAdapter.addAllList(search_list);
						currentPage++;
					}
					mpulltolistView.onRefreshComplete();
				} catch (Exception e) {
					LogUtil.i("适配器更新出错", "");
					ExceptionUtil.handleException(e);
				}

			}
		}
	}

}
