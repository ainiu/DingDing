package com.ghsh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.OpinionModel;
import com.ghsh.code.volley.model.PersonalModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;
/**
 * 意见反馈
 * */
public class OpinionAddActivity  extends BaseActivity implements OnClickListener,DResponseListener{
	private TextView titleView;
	private Button okButton;
	private EditText commentTitleView,commentView;
	private OpinionModel opinionModel;
	private DProgressDialog progressDialog;
	private Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion_add);
		this.initView();
		this.initListener();
		opinionModel=new OpinionModel(this);
		opinionModel.addResponseListener(this);
	}
	private void initView(){
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.opinion_title);
		
		commentView=(EditText)this.findViewById(R.id.opinion_item_comment);
		commentTitleView=(EditText)this.findViewById(R.id.opinion_item_title);
		
		spinner=(Spinner)this.findViewById(R.id.opinion_add_spinner);
		
		okButton=(Button)this.findViewById(R.id.header_button_view);
		okButton.setText(R.string.submit);
		okButton.setVisibility(View.VISIBLE);
	}
	private void initListener(){
		okButton.setOnClickListener(this);
		commentTitleView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60)});
		commentView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(140)});
	}
	@Override
	public void onClick(View v) {
		if(v==okButton){
			//提交
			String title=commentTitleView.getText().toString().trim();
			String comment=commentView.getText().toString().trim();
			if(title.equals("")){
				this.showShortToast(R.string.opinion_tip_title);
				return;
			}
			if(comment.equals("")){
				this.showShortToast(R.string.opinion_tip_comment);
				return;
			}
			progressDialog.show();
			opinionModel.addOpinion(MenberUtils.getUserID(this),title,comment,spinner.getSelectedItemPosition());
		}
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_ADD){
				this.showShortToast(message);
				setResult(Activity.RESULT_OK);
				this.finish();
				return;
			}
		}
		this.showShortToast(message);
	}
}
