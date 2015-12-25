package com.baidu.dingding.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.dingding.R;


public class DAlertDialog extends Dialog implements View.OnClickListener{

	private Context context;
	private TextView textView;
	private Button cancelButton,confirmButton;
	private OnClickListener cancelListener,confirmListener;
	private int meaasge,okText;
	public DAlertDialog(Context context) {
		super(context, R.style.dialog_style_comment);
		this.context = context;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_alterdialog);

		Window window = this.getWindow();
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		WindowManager m =  ((Activity)context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		p.width = d.getWidth();
		window.setAttributes(p);

		this.initView();
		this.initListener();
	}
	private void initView() {
		textView=(TextView)this.findViewById(R.id.alterdialog_text);
		cancelButton=(Button)this.findViewById(R.id.alterdialog_button_cancel);
		confirmButton=(Button)this.findViewById(R.id.alterdialog_button_confirm);

		if(meaasge!=0){
			textView.setText(meaasge);
		}
		if(okText!=0){
			confirmButton.setText(okText);
		}
	}
	private void initListener() {
		cancelButton.setOnClickListener(this);
		confirmButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v==confirmButton){
			if(confirmListener!=null){
				confirmListener.onClick(this, 0);
			}
			this.cancel();
		}else if(v==cancelButton){
			if(cancelListener!=null){
				cancelListener.onClick(this, 0);
			}
			this.cancel();
		}
	}
	public void setMessage(int message){
		this.meaasge=message;
		if(textView!=null){
			textView.setText(message);
		}
	}

	public void setOKText(int okText){
		this.okText=okText;
		if(confirmButton!=null){
			confirmButton.setText(okText);
		}
	}
	public void addCancelListener(
			OnClickListener cancelListener) {
		this.cancelListener = cancelListener;
	}

	public void addConfirmListener(
			OnClickListener confirmListener) {
		this.confirmListener = confirmListener;
	}
}
