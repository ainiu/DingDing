package com.ghsh.activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.R;

public class PayWebActivity extends BaseActivity implements OnClickListener{

	private TextView titleView;
	private ImageView backView;
    private WebView webView;
    private ImageView web_back,goForward,reload;
    private String actionName = "";
    private String params1 = "";
    private ProgressBar web_progress;
    private String pay_result="unkown";

    public int payResult=0;//支付状态 0:取消 1:成功 2:失败
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_web);
        this.initView();
        this.initListener();
        Intent intent = getIntent();
        String html = intent.getStringExtra("html");
        webView.loadData(html, "text/html", "UTF-8");
    }
    
	private void initView() {
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText("支付");
		backView= (ImageView) this.findViewById(R.id.header_back_view);
		web_progress = (ProgressBar) findViewById(R.id.web_progress);
		webView = (WebView) findViewById(R.id.pay_web);
		webView.setWebViewClient(new MyWebViewClient());
		webView.setWebChromeClient(new MyWebChromeClient());
		WebSettings webSettings = webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
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
	protected void onStart() {
		super.onStart();
		backView.setOnClickListener(this);
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
    	}else if(v==backView){
    		//返回
    		this.payResult(payResult);
    		this.finish();
    	}
	}
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// 监测callback，
//			if (url.startsWith(BeeQuery.wapCallBackUrl())) {
//				
//			}
        	Log.i("支付宝支付", url);
//			String params = url.substring(url.lastIndexOf("?") + 1);
//			if (params != null && !"".equals(params)) {
//				String param[] = params.split("&");
//				if (param.length == 2) {
//					params1 = param[0];
//					String param1[] = params1.split("=");
//					if (param1.length == 2) {
//						actionName = param1[0];
//						if (actionName.equals("err")) {
//							String errcode = param1[1];
//							if (errcode.equals("0")) {
//								// 支付成功
//								Intent data = new Intent();
//								pay_result = "success";
//								data.putExtra("pay_result", "success");
//								setResult(Activity.RESULT_OK, data);
//								finish();
//							} else if (errcode.equals("1")) {
//								Intent data = new Intent();
//								pay_result = "fail";
//								data.putExtra("pay_result", pay_result);
//								setResult(Activity.RESULT_OK, data);
//								finish();
//							} else {
//								Intent data = new Intent();
//								pay_result = "fail";
//								data.putExtra("pay_result", pay_result);
//								setResult(Activity.RESULT_OK, data);
//								finish();
//							}
//						}
//					}
//				}
//			}
			super.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }else{
            	this.payResult(payResult);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
            web_progress.setVisibility(View.VISIBLE);
            web_progress.setProgress(newProgress);
            if(newProgress >= 100) {
                web_progress.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }
    }
    
    private void payResult(int payResult){
    	switch(payResult){
		case 0:
			//取消
			this.showShortToast(R.string.pay_result_text_cancel);
			break;
		case 1:
			//成功
			break;
		case 2:
			//失败
			this.showShortToast(R.string.pay_result_text_fail);
			break;
		}
    }
}
