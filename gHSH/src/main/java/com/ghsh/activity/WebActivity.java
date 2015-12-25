package com.ghsh.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.R;
/**
 * 浏览器
 * */
public class WebActivity extends BaseActivity implements OnClickListener{
	private TextView titleView;
	
    private WebView webView;
    private ImageView web_back,goForward,reload;
    private View loadingLayout;
    private String href;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_web);
        this.initView();
        this.initListener();
        Intent intent = getIntent();
        String titleName=intent.getStringExtra("titleName");
        href=intent.getStringExtra("href");
        titleView.setText(titleName);
    }
    
	private void initView() {
		titleView = (TextView) this.findViewById(R.id.header_title_view);

		loadingLayout=this.findViewById(R.id.web_progress);
		
		webView = (WebView) findViewById(R.id.pay_web);
		webView.setWebViewClient(new MyWebViewClient());
		webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		
		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setBlockNetworkImage(true);//把图片加载放在最后来加载渲染
		
		web_back = (ImageView) findViewById(R.id.web_back);
		goForward = (ImageView) findViewById(R.id.goForward);
		reload = (ImageView) findViewById(R.id.reload);
	}

	private void initListener() {
		web_back.setOnClickListener(this);
		goForward.setOnClickListener(this);
		reload.setOnClickListener(this);
	}
    @Override
	public void onClick(View v) {
    	if(v==web_back){
    		//web返回
    		 if (webView.canGoBack()) {
                 webView.goBack();
             }
    	}else if(v==goForward){
    		//web前进
    		webView.goForward();
    	}else if(v==reload){
    		//web刷新
    		 webView.reload();
    	}
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
			webView.loadUrl(href);
			this.isLoad=true;
		}
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
}
