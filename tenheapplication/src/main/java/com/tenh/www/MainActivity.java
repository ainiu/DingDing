package com.tenh.www;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String url = "http://www.tengho.com.cn/mobile/";
        webView.loadUrl(url);
    }

    private void initView() {
        //webView = (WebView) findViewById(R.id.web_view);
        webView = (WebView) this.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
       // webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setSaveFormData(false);
        webView.setVisibility(View.VISIBLE);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
       // webView.setWebViewClient(new MyWebViewClient());
       // webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(WebView.SCHEME_TEL)) {
                    //�绰
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.toLowerCase().startsWith("sms")) {
                    //����
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", "");
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("是否要退出");
                alert.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                alert.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
