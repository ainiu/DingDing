package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.GoodsDetailsConsultListViewtAdapter;
import com.ghsh.code.bean.TConsult;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ConsultModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.DToastView;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;

/**
 * 商品咨询
 * */
public class GoodsDetailsConsultActivity extends Fragment implements OnClickListener,GoodsDetailsConsultListViewtAdapter.Listener{
	private DListView refreshListView;
	private ListView listView;
	private GoodsDetailsConsultListViewtAdapter adapter; 
	private ConsultModel consultModel;
	private DProgressDialog progressDialog;
	private Button addButton;
	private Spinner spinner;
	private String goodsID,goodsName;
	private int currentPage=1;
	private View view;
	
	public GoodsDetailsConsultActivity(String goodsID,String goodsName){
		this.goodsID= goodsID;
		this.goodsName=goodsName;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_goodsdetails_consult, null);
		this.initView();
		this.initListener();
		consultModel=new ConsultModel(getActivity());
		consultModel.addResponseListener(new ConsultResponse(getActivity(),view));
		return view;
	}

	private void initView() {
		progressDialog=new DProgressDialog(getActivity());
		refreshListView=(DListView)view.findViewById(R.id.content_view);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		adapter = new GoodsDetailsConsultListViewtAdapter(getActivity(), new ArrayList<TConsult>());
		listView.setAdapter(adapter);
		addButton=(Button)view.findViewById(R.id.consult_addButton);
		spinner=(Spinner)view.findViewById(R.id.consult_spinner);
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		consultModel.findConsultList(goodsID, currentPage, spinner.getSelectedItemPosition());
//	}
	private void initListener() {
		adapter.addListener(this);
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	consultModel.findConsultList(goodsID, currentPage,spinner.getSelectedItemPosition());
	        }
        });
		addButton.setOnClickListener(this);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				GoodsDetailsConsultActivity.this.resetData();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	
	
	@Override
	public void onClick(View v) {
		if(v==addButton){
			Intent intent=new Intent(getActivity(),ConsultAddActivity.class);
			intent.putExtra("goodsID", goodsID);
			intent.putExtra("goodsName",goodsName);
			this.startActivityForResult(intent, 1);
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			this.resetData();
		}
	}
	
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		consultModel.findConsultList(goodsID, currentPage,spinner.getSelectedItemPosition());
	}
	
	class ConsultResponse extends DResponseAbstract{
		public ConsultResponse(Activity activity,View globalView) {
			super(activity,globalView);
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
			if(bean.getType()==DVolleyConstans.METHOD_CONSULT_QUERY_LIST){
				//咨询列表
				List<TConsult> list=(List<TConsult>)bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}else if(bean.getType()==DVolleyConstans.METHOD_CONSULT_ADD_SATISFIED){
				//满意--不满意
				TConsult consult=(TConsult)bean.getObject();
				if(consult!=null){
					adapter.modifySatisfied(consult.getConsultID(), consult.getSatisfied()!=0?true:false);	
				}else{
					DToastView.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
				}
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			GoodsDetailsConsultActivity.this.resetData();
		}
	}
	
	@Override
	public void onSatisfiedClick(String consultID,boolean flag) {
		if(!BaseActivity.checkLogin()){
			return;
		}
		progressDialog.show();
		consultModel.addSatisfied(BaseActivity.getUserID(), consultID, flag?1:0);
	}
}
