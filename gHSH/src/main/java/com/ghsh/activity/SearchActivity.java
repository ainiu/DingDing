package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.view.DSearchLablesView;
import com.ghsh.code.bean.TKeyword;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.SearchModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.SearchUtil;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;

public class SearchActivity extends BaseActivity implements OnClickListener,DSearchLablesView.Listener,DResponseListener{
	private TextView titleView;
	private Button searchButton;//搜索按钮
	private EditText searchView;//搜索内容
	private ImageView searchDelView;//删除搜索框按钮
	private View searchLayout;//头部搜索框
	private TextView clearView;//清空按钮
	private LinearLayout searchHotlayout;//热门关键字
	private LinearLayout historyLayout;//历史记录
	private DSearchLablesView searchLablesView;//历史记录列表
	
	private SearchModel searchModel;
	private DProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		this.initViews();
		this.initListener(); 
		
		searchModel=new SearchModel(this);
		searchModel.addResponseListener(this);
//		progressDialog.show();
//		searchModel.queryKeyWork();
	}
	
	private void initViews(){
		progressDialog=new DProgressDialog(this);
		
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setVisibility(View.GONE);
		searchButton = (Button) this.findViewById(R.id.header_button_view);
		searchButton.setText(R.string.search);
		searchButton.setVisibility(View.VISIBLE);
		
		
		searchView=(EditText)this.findViewById(R.id.header_search_view);
		searchDelView=(ImageView)this.findViewById(R.id.header_search_del_view);
		searchLayout=this.findViewById(R.id.header_search_layout);
		searchLayout.setVisibility(View.VISIBLE);
		
		clearView= (TextView) this.findViewById(R.id.search_clear_button);
		historyLayout=(LinearLayout)this.findViewById(R.id.search_history_layout);
		searchHotlayout=(LinearLayout)this.findViewById(R.id.search_hot_layout);
		
		searchLablesView=new DSearchLablesView(this);
		searchLablesView.addTexts(SearchUtil.readeAll(this),historyLayout.getPaddingLeft()+historyLayout.getPaddingRight());
		searchLablesView.addListener(this);
		historyLayout.addView(searchLablesView);
	}
	
	private void initListener(){
		searchButton.setOnClickListener(this);
		clearView.setOnClickListener(this);
		searchDelView.setOnClickListener(this);
		searchView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s!=null&&!s.toString().equals("")){
					searchDelView.setVisibility(View.VISIBLE);
				}else{
					searchDelView.setVisibility(View.GONE);
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if(v==searchButton){
			//搜索
			String keyword=searchView.getText().toString().trim();
			this.startSearch(keyword);
		}else if(v==clearView){
			//清空本地缓存
			if(searchLablesView.getChildCount()==0){
				return;
			}
			DAlertUtil.alertOKAndCel(this, R.string.search_alter_message_clear, new android.content.DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					searchLablesView.removeAllViews();
					SearchUtil.clearAll(SearchActivity.this);
				}
			}).show();
		}else if(v==searchDelView){
			//删除
			searchView.setText("");
		}
	}

	@Override
	public void onClickLable(String keyword) {
		this.startSearch(keyword);
	}
	
	private void startSearch(String keyword){
		if(keyword.equals("")){
			this.showShortToast(R.string.search_tip_content_empty);
			return;
		}
		SearchUtil.write(this, keyword);
		Intent intent=new Intent(this,GoodsListActivity.class);
		intent.putExtra("keyword", keyword);
		this.startActivity(intent);
		this.finish();
	}

	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				List<TKeyword> list=(List<TKeyword>)bean.getObject();
				if(list!=null&&list.size()!=0){
					List<String> keyList=new ArrayList<String>();
					for(TKeyword key:list){
						keyList.add(key.getName());
					}
					DSearchLablesView searchLablesView=new DSearchLablesView(this);
					searchLablesView.addTexts(keyList,searchHotlayout.getPaddingLeft()+searchHotlayout.getPaddingRight());
					searchLablesView.addListener(this);
					searchHotlayout.addView(searchLablesView);
				}
				return;
			}
		}
		this.showShortToast(message);
	}
}
