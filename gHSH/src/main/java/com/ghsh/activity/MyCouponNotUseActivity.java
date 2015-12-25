package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.MyCouponGridViewtAdapter;
import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TMyCoupon;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MyCouponModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.pullrefresh.DGridView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;

/**
 * 未使用优惠劵
 * */
public class MyCouponNotUseActivity extends Fragment {
	private DProgressDialog progressDialog;
	private View view;
	private DGridView refreshGridView;
	private GridView gridView;
	private MyCouponGridViewtAdapter adapter;
	
	private int currentPage=1;
	
	private int couponType=2;//未使用
	private MyCouponModel myCouponModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_mycoupon_layout, null);
		this.initView();
		this.initListener();
		myCouponModel=new MyCouponModel(getActivity());
		myCouponModel.addResponseListener(new CouponResponse(this.getActivity(),view));
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		this.resetData();
	}

	private void initView() {
		progressDialog=new DProgressDialog(getActivity());
		
		refreshGridView=(DGridView)view.findViewById(R.id.content_view);
		refreshGridView.setPullDownEnabled(false);
		refreshGridView.setPullUpEnabled(true);
		gridView=refreshGridView.getRefreshableView();
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(20);
		gridView.setHorizontalSpacing(20);
		gridView.setPadding(20, 20, 20, 20);
		
		adapter=new MyCouponGridViewtAdapter(getActivity(),new ArrayList<TMyCoupon>());
		gridView.setAdapter(adapter);
	}

	private void initListener() {
		refreshGridView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	myCouponModel.findCouponList(BaseActivity.getUserID(),couponType,currentPage);
	        }
        });
	}
	
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		myCouponModel.findCouponList(BaseActivity.getUserID(),couponType,currentPage);
	}
	
	class CouponResponse extends DResponseAbstract{
		public CouponResponse(Activity activity,View globalView) {
			super(activity,globalView);
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
				//返回查询所有收货地址
				List<TMyCoupon> list=(List<TMyCoupon>)bean.getObject();
				adapter.setList(list);
				adapter.notifyDataSetChanged();
				return;
			}
		} 
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			progressDialog.show();
			myCouponModel.findCouponList(BaseActivity.getUserID(),couponType,currentPage);
		}
	}
}
