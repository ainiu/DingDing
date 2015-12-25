package com.ghsh.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ghsh.R;
import com.ghsh.activity.GoodsDetailsConsultActivity.ConsultResponse;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.code.bean.TConsult;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.ConsultModel;
import com.ghsh.code.volley.model.GoodsModel;
import com.ghsh.view.DToastView;

/**
 * 商品详情
 * */
public class GoodsDetailsDesActivity extends Fragment{
	private WebView webView;
	private String goodsID;
	private View loadingLayout;
	private boolean isLoad=false;
	private GoodsModel goodsModel;
	private View view;
	public GoodsDetailsDesActivity(String goodsID){
		this.goodsID= goodsID;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_goodsdetails_des, null);
		this.initView();
		goodsModel=new GoodsModel(getActivity());
		goodsModel.addResponseListener(new GoodsResponse(getActivity(),view));
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		isLoad = false;
		goodsModel.getGoodsDesc(goodsID);
	}

	private void initView() {
		loadingLayout=view.findViewById(R.id.goodsdetails_des_loading_progress);
		
		webView=(WebView)view.findViewById(R.id.webView);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBlockNetworkImage(true);//把图片加载放在最后来加载渲染
//		webView.getSettings().setBuiltInZoomControls(true);
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		webView.setWebViewClient(new MyWebViewClient());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		webView.setVisibility(View.GONE);
		webView.removeAllViews();
		webView.destroy();
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				loadingLayout.setVisibility(View.GONE);
				webView.getSettings().setBlockNetworkImage(false);
				webView.getSettings().setJavaScriptEnabled(false);
				webView.setVisibility(View.VISIBLE);
			}
		}
	};
	
	final class InJavaScriptLocalObj{
		private boolean isLoaded = false;
		/**
		 * 将取得的html中不需要的内容去掉
		 * @param html
		 */
		@JavascriptInterface
		public void showSource(String html) {
			if(!isLoaded){
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
				isLoaded = true;
			}
		}
	}
	
	final class MyWebViewClient extends WebViewClient{ 
		public int count;
		public boolean shouldOverrideUrlLoading(WebView view, String url) {  
			view.loadUrl(url);  
			return true;  
		} 
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}
		public void onPageFinished(WebView view, String url) {
			view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +"document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			super.onPageFinished(view, url);
		}
	}
	class GoodsResponse extends DResponseAbstract{
		public GoodsResponse(Activity activity,View globalView) {
			super(activity,globalView);
		}
		@Override
		public void onResponseStart() {
//			loadingLayout.setVisibility(View.GONE);
//			webView.getSettings().setBlockNetworkImage(false);
//			webView.getSettings().setJavaScriptEnabled(false);
//			webView.setVisibility(View.VISIBLE);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				webView.loadDataWithBaseURL(null, bean.getObject()+"", "text/html", "utf-8", null);
			}
		}
	}
}
