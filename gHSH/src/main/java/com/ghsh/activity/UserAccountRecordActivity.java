package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.UserAccountRecordListViewtAdapter;
import com.ghsh.code.bean.TWithdrawRecode;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.UserAccountModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
/**
 * 账户明细
 * */
public class UserAccountRecordActivity extends BaseActivity {
	private TextView titleView;
	private DProgressDialog progressDialog;
	private DListView refreshListView;
	private ListView listView;
	private UserAccountRecordListViewtAdapter adapter=new UserAccountRecordListViewtAdapter(this,new ArrayList<TWithdrawRecode>());
	private UserAccountModel userAccountModel;
	private int currentPage=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_user_account_record);
		this.initView();
		this.initListener();
		userAccountModel=new UserAccountModel(this);
		userAccountModel.addResponseListener(new UserAccountRecordResponse(this));
		this.resetData();
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.user_account_title_record);
		
		refreshListView= (DListView) this.findViewById(R.id.content_view);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		listView.setDivider(this.getResources().getDrawable(R.drawable.line_dotted));
		listView.setDividerHeight(2);
		listView.setAdapter(adapter);
	}
	
	private void initListener(){
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	userAccountModel.findRecordList(MenberUtils.getUserID(UserAccountRecordActivity.this), currentPage);
	        }
        });
	}

	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		userAccountModel.findRecordList(MenberUtils.getUserID(UserAccountRecordActivity.this), currentPage);
	}
	
	class UserAccountRecordResponse extends DResponseAbstract{
		public UserAccountRecordResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			refreshListView.onStopUpRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//返回查询所有收藏
				List<TWithdrawRecode> list=(List<TWithdrawRecode>) bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			UserAccountRecordActivity.this.resetData();
		}
	}
}
