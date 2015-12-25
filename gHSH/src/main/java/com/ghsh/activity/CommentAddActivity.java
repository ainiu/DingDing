package com.ghsh.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.R;
import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.CommentAddListViewViewtAdapter;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.CommentModel;
import com.ghsh.code.volley.model.OrderModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.MenberUtils;

/**
 * 商品评论
 * */
public class CommentAddActivity extends BaseActivity implements OnClickListener ,DResponseListener{
	private TextView titleView;
	private DProgressDialog progressDialog;
	private CommentModel commentModel;
	private OrderModel orderModel;
	private ListView listView;
	private Button okButton;
	private CheckBox checkBox;
	private CommentAddListViewViewtAdapter adapter=new CommentAddListViewViewtAdapter(this,new ArrayList<TOrderItem>());
	private String orderID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!this.checkLogin()){
			return;
		}
		setContentView(R.layout.activity_comment_add);
		this.initView();
		this.initListener();
		commentModel=new CommentModel(this);
		commentModel.addResponseListener(new CommentModelListener());
		orderModel=new OrderModel(this);
		orderModel.addResponseListener(this);
		orderID=getIntent().getStringExtra("orderID");
		progressDialog.show();
		orderModel.getOrderDetail(orderID);
	}
	
	private void initView() {
		progressDialog = new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.comment_title);
		listView=(ListView)this.findViewById(R.id.comment_add_listView);
		listView.setAdapter(adapter);
		okButton=(Button)this.findViewById(R.id.comment_add_button);
		checkBox=(CheckBox)this.findViewById(R.id.comment_add_checkBox);
	}
	
	private void initListener() {
		okButton.setOnClickListener(this);
		listView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
				EditText inputView=(EditText)view.findViewById(R.id.comment_item_comment);
				inputView.requestFocus();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
			}
		});
	}
	@Override
	public void onClick(View v) {
		if(v==okButton){
			//提交评论
			for(TOrderItem item:adapter.getList()){
				if(item.getComment()==null||item.getComment().equals("")){
					this.showShortToast(R.string.comment_input_tip);
					return;
				}
			}
			progressDialog.show();
			commentModel.addMoreComment(MenberUtils.getUserID(this), orderID, checkBox.isChecked(), adapter.getList());
		}
	}
	@Override
	public void onMessageResponse(ReturnBean bean, int result, String message,Throwable error) {
		progressDialog.dismiss();
		if(error!=null){
			this.showShortToast(error.getMessage());
			this.finish();
			return;
		}
		if(result==DVolleyConstans.RESULT_OK){
			if(bean.getType()==DVolleyConstans.METHOD_GET){
				//查询订单详细
				TOrder order=(TOrder)bean.getObject();
				adapter.setList(order.getOrderItemList());
				adapter.notifyDataSetChanged();
				return;
			}
		}
		this.showShortToast(message);
	}
	class CommentModelListener implements DResponseListener{
		@Override
		public void onMessageResponse(ReturnBean bean, int result,String message, Throwable error) {
			progressDialog.dismiss();
			if(error!=null){
				CommentAddActivity.this.showShortToast(error.getMessage());
				return;
			}
			if(result==DVolleyConstans.RESULT_OK){
				if(bean.getType()==DVolleyConstans.METHOD_ADD){
					//评论添加成功
					CommentAddActivity.this.showShortToast(message);
					Intent intent = new Intent();
					CommentAddActivity.this.setResult(Activity.RESULT_OK, intent);  
					CommentAddActivity.this.finish();
					return;
				}
			}
			CommentAddActivity.this.showShortToast(message);
		}
	}
	
}
