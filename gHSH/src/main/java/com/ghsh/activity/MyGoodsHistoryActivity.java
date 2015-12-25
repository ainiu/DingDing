package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.MyGoodsHistoryGridViewtAdapter;
import com.ghsh.code.bean.TGoodsHistory;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MyCollectModel;
import com.ghsh.code.volley.model.PersonalModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.pullrefresh.DGridView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;
/**
 * 商品浏览历史
 * */
public class MyGoodsHistoryActivity  extends BaseActivity implements OnClickListener{
	private TextView titleView;
	private Button editButton;//编辑按钮
	private DGridView refreshGridView;
	private GridView gridView;
	private MyGoodsHistoryGridViewtAdapter adapter=new MyGoodsHistoryGridViewtAdapter(this, new ArrayList<TGoodsHistory>());
	private DProgressDialog progressDialog;
	private PersonalModel personalModel;
	private int currentPage=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_mygoodshistory);
		this.initView();
		this.initListener();
		personalModel=new PersonalModel(this);
		personalModel.addResponseListener(new HistoryResponse(this));
		this.resetData();
	}

	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.myGoodsHistory_title);
		editButton = (Button) this.findViewById(R.id.header_button_view);
		editButton.setVisibility(View.VISIBLE);
		editButton.setText(R.string.edit);
		editButton.setTag(true);
		
		refreshGridView= (DGridView) this.findViewById(R.id.content_view);
		refreshGridView.setPullDownEnabled(false);
		refreshGridView.setPullUpEnabled(false);
		gridView=refreshGridView.getRefreshableView();
		gridView.setNumColumns(GridView.AUTO_FIT);
		gridView.setPadding(20, 0, 20, 0);
		gridView.setHorizontalSpacing(20);
		gridView.setVerticalSpacing(20);
		gridView.setAdapter(adapter);
	}
	
	private void initListener() {
		editButton.setOnClickListener(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TGoodsHistory collect=adapter.getItem(position);
				Intent intent=new Intent(MyGoodsHistoryActivity.this,GoodsDetailsActivity.class);
				intent.putExtra("goodsID", collect.getGoodsID());
				MyGoodsHistoryActivity.this.startActivity(intent);
			}
		});
		refreshGridView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	personalModel.findGoodsHistoryList(MyGoodsHistoryActivity.this.getUserID(),currentPage);
	        }
        });
		adapter.addCallBackListener(new MyGoodsHistoryGridViewtAdapter.CallBackListener() {
			@Override
			public void deleteCollect(final String id) {
				DAlertUtil.alertOKAndCel(MyGoodsHistoryActivity.this, R.string.myCollect_alter_message_delete, new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressDialog.show();
						personalModel.deleteGoodsHistory(id);
					}
				}).show();
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if(v==editButton){
			//编辑
			if((Boolean)editButton.getTag()){
				editButton.setText(R.string.cancel);
				editButton.setTag(false);
				adapter.setEdit(true);
			}else{
				editButton.setText(R.string.edit);
				editButton.setTag(true);
				adapter.setEdit(false);
			}
			adapter.notifyDataSetChanged();
		}
	}
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		personalModel.findGoodsHistoryList(this.getUserID(),currentPage);
	}
	class HistoryResponse extends DResponseAbstract{
		public HistoryResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			refreshGridView.onStopUpRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//返回查询所有收藏
				List<TGoodsHistory> list=(List<TGoodsHistory>) bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}else if(bean.getType()==DVolleyConstans.METHOD_DELETE){
				//删除收藏
				String id=bean.getObject()+"";
				adapter.removeByGoodsID(id);
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			boolean isShow=adapter.getList().size()==0;
			if(isShow){
				editButton.setVisibility(View.GONE);
			}else{
				editButton.setVisibility(View.VISIBLE);
			}
			return isShow;
		}
		@Override
		protected void onRefresh() {
			MyGoodsHistoryActivity.this.resetData();
		}
	}
}
