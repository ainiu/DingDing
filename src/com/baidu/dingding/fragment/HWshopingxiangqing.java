package com.baidu.dingding.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.baidu.dingding.R;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.view.MyView.MyScrollView;

/**
 * Created by Administrator on 2015/11/10.海外商品详情
 */
public class HWshopingxiangqing extends Fragment  {
    private View view;
    private WebView webView;
    String web_logpath;
    private  MyScrollView myScrollView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.activity_gong_gao,container,false);
        initView();
        return view;
    }

    private void initData() {
        Bundle bundle=getArguments();
        if(!bundle.isEmpty()) {
            web_logpath = bundle.getString("description");
            LogUtil.i("商品详情店铺推荐接受id", "web_logpath=" + web_logpath);
        }
    }

    private void initView() {
        webView= (WebView) view.findViewById(R.id.gonggao_web);
        webView.loadDataWithBaseURL(null, web_logpath, "text/html", "utf-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        myScrollView= (MyScrollView) view.findViewById(R.id.myscroll);
        myScrollView.smoothScrollTo(0,0);
    }


}
