package com.ghsh.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.ghsh.Constants;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.SinglePageModel;
import com.ghsh.view.DToastView;
import com.ghsh.R;
/**
 * 单页管理
 * */
public class SinglePageWebActivity extends BaseActivity implements DResponseListener{
	private TextView titleView;
	
    private WebView webView;
    private View loadingLayout;
    private String singlePageID;
    private SinglePageModel singlePageModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_page_web);
        this.initView();
        Intent intent = getIntent();
        String titleName=intent.getStringExtra("titleName");
        singlePageID=intent.getStringExtra("singlePageID");
        titleView.setText(titleName);
        
        singlePageModel=new SinglePageModel(this);
        singlePageModel.addResponseListener(this);
    }
    
	private void initView() {
		titleView = (TextView) this.findViewById(R.id.header_title_view);

		loadingLayout=this.findViewById(R.id.web_progress);
		
		webView = (WebView) findViewById(R.id.pay_web);
		webView.setWebViewClient(new MyWebViewClient());
//		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		WebSettings webSettings = webView.getSettings();
//		webSettings.setBuiltInZoomControls(true);
		
//		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		
//		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);
//		webSettings.setDomStorageEnabled(true);
//		webSettings.setBlockNetworkImage(true);//把图片加载放在最后来加载渲染
		
		
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 
        // 开启 DOM storage API 功能 
		webSettings.setDomStorageEnabled(true); 
        //开启 database storage API 功能 
		webSettings.setDatabaseEnabled(true);  
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME; 
        //设置数据库缓存路径 
        webSettings.setDatabasePath(Constants.getWebViewCachePath(this)); 
        //设置  Application Caches 缓存目录 
        webSettings.setAppCachePath(Constants.getWebViewCachePath(this)); 
        //开启 Application Caches 功能 
        webSettings.setAppCacheEnabled(true);  

	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		webView.setVisibility(View.GONE);
		webView.removeAllViews();
		webView.destroy();
	}
    private boolean isLoad=false;
    @Override
	protected void onResume() {
		super.onResume();
		if(!isLoad){
			singlePageModel.getSinglePage(singlePageID);
		}
	}
    
    private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				loadingLayout.setVisibility(View.GONE);
				webView.getSettings().setBlockNetworkImage(false);
//				webView.getSettings().setJavaScriptEnabled(false);
				webView.setVisibility(View.VISIBLE);
				isLoad=true;
			}
		}
	};
	
	
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
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
			super.onPageFinished(view, url);
		}
		 @Override
         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
             super.onReceivedError(view, errorCode, description, failingUrl);
             DToastView.makeText(SinglePageWebActivity.this, "加载失败，"+description, Toast.LENGTH_SHORT).show();
         } 
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
//				String html="<html><table style='width: 100%;' border='1' cellspacing='0' cellpadding='2' bordercolor='#000000'>"
//				+"<tbody>"
//				+"<tr>" 
//				+"<td><span style='font-size: 20px;'><span style='font-size: 20px;'><span style='font-size: 20px; '>深圳杰正武道文化传播有限公司简介</span></span></span></td>"
//				+"</tr>"
//				+"<tr>"
//				+"<td><span style='font-size: 20px;'><span style='font-size: 20px;;'><img src='http://www.dtouch.so/data/files/store_176/article/201507011629523553.jpg' alt='n_1547844627843597_640_480.jpg' /></span></span></td>"
//				+"</tr>"
//				+"<tr>"
//				+"<td><span style='font-size: 20px;'><span style='font-size: 20px;'>杰正跆拳道是 目前深圳市规模最大的跆拳道培训机构之一,在深圳市南山区、福田区、罗湖区、宝安区、龙岗区、龙华新区均设有教学基地。是由资深的跆拳道师范李文杰创办于 1996年，是深圳市工商管理局登记注册公司，是深圳市体育局跆拳道协会批准注册机构,是深圳市众多名校名园跆拳道培训机构。荣获&ldquo;深圳市跆拳道先进单 位&rdquo;，荣获&ldquo;深圳市十强跆拳道馆&rdquo;，荣获&ldquo;深圳市艺术协会副会长单位&rdquo;。<br /><br />杰正武道培训的项目有：跆拳道、武术、&nbsp; 太极拳、散打、空手道、剑道、咏春拳、双节棍等。<br /><br />&nbsp;<br /><br /><br />杰正理念：杰出武道&nbsp;&nbsp; 正义博爱</span></span></td>"
//				+"</tr>"
//				+"</tbody>"
//				+"</table></html>";
				webView.loadDataWithBaseURL(null,bean.getObject()+"" , "text/html",  "UTF-8", null);
				return;
			}
		}
		loadingLayout.setVisibility(View.GONE);
		webView.setVisibility(View.VISIBLE);
		this.showShortToast(message);
	}
}
