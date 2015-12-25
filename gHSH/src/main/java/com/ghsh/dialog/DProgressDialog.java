package com.ghsh.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.ghsh.R;

/**
 * 加载页面
 * */
public class DProgressDialog extends  Dialog {
	 
	private Context context;
	public DProgressDialog(Context context) {
		super(context, R.style.dialog_style_comment);
		this.context = context;
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.alert_progress_view);
        this.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
//        this.setCancelable(false);//设置进度条是否可以按退回键取消
    }
}
