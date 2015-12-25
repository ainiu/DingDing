package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.OpinionListViewtAdapter;
import com.ghsh.code.bean.TFeedback;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OpinionModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
/**
 * 意见反馈
 * */
public class OpinionActivity  extends BaseActivity implements OnClickListener{
	private TextView titleView;
	private Button addButton;
	private Spinner spinner;
	private OpinionModel opinionModel;
	private DProgressDialog progressDialog;
	private DListView refreshListView;
	private ListView listView;
	private OpinionListViewtAdapter adapter=new OpinionListViewtAdapter(this,new ArrayList<TFeedback>());
	private int currentPage=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_opinion);
		this.initView();
		this.initListener();
		opinionModel=new OpinionModel(this);
		opinionModel.addResponseListener(new OpinionResponse(this));
	}
	private void initView(){
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.opinion_title);
		
		refreshListView=(DListView)this.findViewById(R.id.content_view);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		listView.setAdapter(adapter);
		
		spinner=(Spinner)this.findViewById(R.id.opinion_spinner);
		addButton=(Button)this.findViewById(R.id.opinion_addButton);
	}
	private void initListener(){
		addButton.setOnClickListener(this);
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	opinionModel.findOpinionList(MenberUtils.getUserID(OpinionActivity.this), currentPage,spinner.getSelectedItemPosition());
	        }
        });
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				OpinionActivity.this.resetData();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		opinionModel.findOpinionList(MenberUtils.getUserID(this), currentPage,spinner.getSelectedItemPosition());
	}
	@Override
	public void onClick(View v) {
		if(v==addButton){
			Intent intent=new Intent(this,OpinionAddActivity.class);
			this.startActivityForResult(intent, 100);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK){
			if(requestCode==100){
				this.resetData();
			}
		}
	}
	class OpinionResponse extends DResponseAbstract{
		public OpinionResponse(Activity activity) {
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
				//咨询列表
				List<TFeedback> list=(List<TFeedback>)bean.getObject();
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
			OpinionActivity.this.resetData();
		}
	}
}
