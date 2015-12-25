package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.MessageListViewAdapter;
import com.ghsh.broadcast.BroadcastUtils;
import com.ghsh.code.bean.TMessage;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.MessageModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.util.VibratorUtil;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.view.util.DAlertUtil;
import com.ghsh.R;
/**
 * 消息页面
 * */
public class MessageActivity extends BaseActivity{
	private TextView titleView;
	private MessageListViewAdapter adapter=new MessageListViewAdapter(this,new ArrayList<TMessage>());
	private MessageModel messageModel;
	private int currentPage=1;//当前页
	private DProgressDialog progressDialog;
	private DListView refreshableView;
	private int pageCount=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!checkLogin()){
			return;
		}
		setContentView(R.layout.activity_message);
		this.initView();
		messageModel=new MessageModel(this);
		messageModel.addResponseListener(new MessageResponse(this));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		reGetDate();
		BroadcastUtils.sendNewMessageSize(this, 0);
	}
	/**获取数据**/
	private void reGetDate(){
		if (currentPage<=pageCount) {
			messageModel.findMessageList(getUserID(), currentPage);
		}else{
			refreshableView.onStopUpRefresh(false);
		}
	}
	/**刷新获取**/
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		reGetDate();
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		progressDialog.show();
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.message_title);
		refreshableView= (DListView) this.findViewById(R.id.content_view);
		refreshableView.setPullUpEnabled(true);
		refreshableView.setPullDownEnabled(true);
		ListView listView=refreshableView.getRefreshableView();
		listView.setDivider(null);
		listView.setDividerHeight(30);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				final TMessage msg=adapter.getItem(position);
				VibratorUtil.vibrateOne(MessageActivity.this);
				DAlertUtil.alertOKAndCel(MessageActivity.this, R.string.order_list_alter_message_comfire_delete, new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressDialog.show();
						messageModel.deleteMessage(getUserID(), msg.getMeaasgeID());
					}
				}).show();
				return false;
			}
		});
		refreshableView.setOnRefreshListener(new DOnRefreshListener() {
			
			@Override
			public void onPullUpToRefresh() {
				reGetDate();
			}
			
			@Override
			public void onPullDownToRefresh() {
				resetData();
			}
		});
	}

	class MessageResponse extends DResponseAbstract{
		public MessageResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			refreshableView.onStopUpRefresh(isMoreData);
			refreshableView.onStopDownRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//消息查询结果
				List<TMessage> list=(List<TMessage>) bean.getObject();
				pageCount = bean.getPageCount();//获得数据库当前所有页面大小
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}else if(bean.getType()==DVolleyConstans.METHOD_DELETE){
				//删除
				adapter.deleteMsgByID(bean.getObject()+"");
				adapter.notifyDataSetChanged();
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			MessageActivity.this.resetData();
		}
	}
}
