package com.ghsh.activity.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.view.DToastView;
import com.ghsh.R;
/**
 * 界面回调监听默认实现类
 * 1.当前无数据且当前查询无数据，显示无数据界面
 * 2.当前有数据且当前查询有/无数据，显示数据页面
 * 3.当前无数据且当前无网络，显示无网络界面
 * 4.当前有数据且当前查询有/无网络，显示数据页面
 * */
public abstract class DResponseAbstract implements DResponseListener,OnClickListener {
	private Activity activity;
	private ViewGroup mainLayout;//主页面
	private View contentView;//内容页面
	private ViewGroup notNetwortLayout;//无网络页面
	private ViewGroup emptyLayout;//无数据页面
	private Button netWorkSettingButton,netWorkRefreshButton;//设置网络，重新加载
	private int notDataView=R.layout.empty_pager;
	private boolean isMoreData=true;//是否还有更多数据
	private View globalView;//全局View
	public DResponseAbstract(Activity activity) {
		this.activity = activity;
		mainLayout=(ViewGroup)this.findViewById(R.id.main_layout);
	}
	public DResponseAbstract(Activity activity,View globalView) {
		this.activity = activity;
		this.globalView=globalView;
		mainLayout=(ViewGroup)this.findViewById(R.id.main_layout);
	}
	
	public DResponseAbstract(Activity activity,int notDataView) {
		this.activity = activity;
		this.notDataView=notDataView;
		mainLayout=(ViewGroup)this.findViewById(R.id.main_layout);
	}

	@Override
	public final void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		this.isMoreData=true;
		this.onResponseStart();
		switch (result) {
		case DVolleyConstans.RESULT_NOT_NEWWORK:
			// 无网络
			this.notNetWork(error);
			break;
		case DVolleyConstans.RESULT_OK:
			// 成功
			this.onResponseSuccess(bean,message);
			this.notDataView();
			break;
		case DVolleyConstans.RESULT_FAIL:
			//失败
			if(message!=null&&!message.equals("")){
				DToastView.makeText(activity, message,Toast.LENGTH_SHORT).show();
			}else{
				DToastView.makeText(activity, error.getMessage(),Toast.LENGTH_SHORT).show();
			}
			break;
		}
		this.onResponseEnd(isMoreData);
	}
	//无网络
	private void notNetWork(Throwable error){
		if(mainLayout==null){
			DToastView.makeText(activity, error.getMessage(),Toast.LENGTH_SHORT).show();
			return;
		}
		if(contentView==null){
			contentView=this.findViewById(R.id.content_view);
		}
		if(contentView==null){
			DToastView.makeText(activity, error.getMessage(),Toast.LENGTH_SHORT).show();
			return;
		}
		//是否需要展示无网络页面
		if(this.isShowEmptyPageView()){
			if(notNetwortLayout==null){
				notNetwortLayout=(ViewGroup)activity.getLayoutInflater().inflate(R.layout.networt_not, null);
				netWorkSettingButton=(Button)notNetwortLayout.findViewById(R.id.network_not_setting_button);
				netWorkRefreshButton=(Button)notNetwortLayout.findViewById(R.id.network_not_refresh_button);
				netWorkSettingButton.setOnClickListener(this);
				netWorkRefreshButton.setOnClickListener(this);
				mainLayout.addView(notNetwortLayout);
			}
			if(emptyLayout!=null){
				emptyLayout.setVisibility(View.GONE);//隐藏没有数据界面
			}
			contentView.setVisibility(View.GONE);
			mainLayout.setVisibility(View.VISIBLE);
		}else{
			contentView.setVisibility(View.VISIBLE);
			if(notNetwortLayout!=null){
				notNetwortLayout.setVisibility(View.GONE);//隐藏网络界面
			}
			if(emptyLayout!=null){
				emptyLayout.setVisibility(View.GONE);//隐藏无数据页面
			}
			DToastView.makeText(activity, error.getMessage(),Toast.LENGTH_SHORT).show();
		}
	}

	// 无数据
	private void notDataView() {
		if(mainLayout==null){
			return;
		}
		if(contentView==null){
			contentView=this.findViewById(R.id.content_view);
		}
		if(contentView==null){
			return;
		}
		//是否需要展示无数据页面
		if(this.isShowEmptyPageView()){
			if(emptyLayout==null){
				emptyLayout=(ViewGroup)activity.getLayoutInflater().inflate(notDataView, null);
				mainLayout.addView(emptyLayout);
			}
			if(notNetwortLayout!=null){
				notNetwortLayout.setVisibility(View.GONE);//隐藏网络界面
			}
			contentView.setVisibility(View.GONE);
			emptyLayout.setVisibility(View.VISIBLE);
		}else{
			contentView.setVisibility(View.VISIBLE);
			if(emptyLayout!=null){
				emptyLayout.setVisibility(View.GONE);//隐藏无数据页面
			}
			if(notNetwortLayout!=null){
				notNetwortLayout.setVisibility(View.GONE);//隐藏网络界面
			}
		}
	}
	/**数据加载成功*/
	protected abstract void onResponseSuccess(ReturnBean bean, String message);
	
	/**数据加载开始*/
	protected void onResponseStart(){}
	
	/**数据加载结束*/
	protected void onResponseEnd(boolean isMoreData){}
	
	/**是否显示无数据界面*/
	protected boolean isShowEmptyPageView(){
		return true;
	}
	
	/**重新刷新*/
	protected void onRefresh(){
		
	}
	public final void setMoreData(boolean isMoreData) {
		this.isMoreData = isMoreData;
	}
	@Override
	public void onClick(View v) {
		if(v==netWorkSettingButton){
			 //设置网络,判断手机系统的版本  即API大于10 就是3.0或以上版本 
			Intent intent=null;
            if(android.os.Build.VERSION.SDK_INT>10){
                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            }else{
                intent = new Intent();
                ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            activity.startActivity(intent);
		}else if(v==netWorkRefreshButton){
			//重新加载
			this.onRefresh();
		}
	}
	
	//获取界面view
	private View findViewById(int id){
		if(globalView!=null){
			return globalView.findViewById(id);
		}else{
			return activity.findViewById(id);
		}
	}
}
