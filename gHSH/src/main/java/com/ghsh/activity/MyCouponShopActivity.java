package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.activity.MyCouponNotUseActivity.CouponResponse;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.MyCouponGridViewtAdapter;
import com.ghsh.code.bean.TCoupon;
import com.ghsh.code.bean.TMyCoupon;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MyCouponModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.DToastView;
import com.ghsh.view.pullrefresh.DGridView;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;

/**
 * 店铺优惠劵
 * */
public class MyCouponShopActivity extends Fragment{
	private DProgressDialog progressDialog;
	private View view;
	private DGridView refreshGridView;
	private GridView gridView;
	private MyCouponGridViewtAdapter adapter;
	
	private int currentPage=1;
	
	private int couponType=1;//店铺优惠劵
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
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TMyCoupon coupon=adapter.getItem(position);
				if(coupon.getStatus().equals(Constants.COUPON_STATUS_1)){
					progressDialog.show();
					myCouponModel.receiveCoupon(BaseActivity.getUserID(), coupon.getCouponID());
				}else{
					DToastView.makeText(getActivity(), "该优惠劵 "+Constants.COUPON_STATUS_1_MAP().get(coupon.getStatus()), Toast.LENGTH_SHORT).show();
				}
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
			}else if(bean.getType()==DVolleyConstans.METHOD_ADD){
				//领取优惠劵返回
				adapter.receiveCoupon(bean.getObject()+"");
				DToastView.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
