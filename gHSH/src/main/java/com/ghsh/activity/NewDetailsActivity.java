package com.ghsh.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ghsh.activity.SinglePageWebActivity.MyWebViewClient;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.NewsModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 新闻详情页面
 * */
public class NewDetailsActivity extends BaseActivity implements DResponseListener{
	private TextView title;
	private NewsModel newsModel; 
	private DProgressDialog progressDialog;
	private TextView titleView,timeView;
	private WebView webView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        this.initView();
        this.initListener();
        Intent intent=this.getIntent();
        String newsID=intent.getStringExtra("newsID");
        newsModel = new NewsModel(this);
        newsModel.addResponseListener(this);
        progressDialog.show();
        newsModel.getNewsByID(newsID);
    }
    
	private void initView() {
		progressDialog=new DProgressDialog(this);
		title = (TextView) findViewById(R.id.header_title_view);
		title.setText("新闻详情");
		
		titleView=(TextView) findViewById(R.id.new_details_title);
		timeView=(TextView) findViewById(R.id.new_details_time);
		
		webView=(WebView) findViewById(R.id.new_details_webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSaveFormData(true);
		webView.setWebViewClient(new MyWebViewClient());
		
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
			webView.loadUrl("javascript:(function(){" +
					"var script = document.createElement('script');"
					+ "script.type = 'text/javascript';"
					+ "script.text = \"function ResizeImages() { "
					+ "var myimg;"
					+ "for(i=0;i <document.images.length;i++)" + "{"
					+ "myimg = document.images[i];" + "myimg.setAttribute('style','max-width:100%;height:auto');"
					+ "}" + "}\";"
					+ "document.getElementsByTagName('head')[0].appendChild(script);" + 
					"})()"); 
			webView.loadUrl("javascript:ResizeImages()");
			super.onPageFinished(view, url);
		}
	}

	private void initListener(){
		
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				//最新新闻列表
				TNews news=(TNews)bean.getObject();
				titleView.setText(news.getTitle());
				timeView.setText(news.getAddTime());
				webView.loadDataWithBaseURL(null, news.getContent(),"text/html", "utf-8", null);
			}
		}
	}
}
