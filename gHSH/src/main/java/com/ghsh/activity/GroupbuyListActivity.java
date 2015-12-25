package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.GroupbuyListViewAdapter;
import com.ghsh.code.bean.TGroupbuy;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.GroupbuyModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;
/**
 * 团购列表
 * */
public class GroupbuyListActivity extends BaseActivity implements OnClickListener{
	private TextView titleView;
	private ImageView topButton;//置顶按钮
	private GroupbuyListViewAdapter adapter=new GroupbuyListViewAdapter(this, new ArrayList<TGroupbuy>());
	//布局
	private GroupbuyModel groupbuyModel;
	
	private DProgressDialog progressDialog;
	private int currentPage=1;//当前页
	private DListView refreshListView;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_groupbuylist);
		this.initView();
		this.initListener();
		groupbuyModel=new GroupbuyModel(this);
		groupbuyModel.addResponseListener(new GroupbuyResponse(this));
		this.resetData();
	}

	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		groupbuyModel.findGroupbuyList(currentPage);
	}
	private void initView() {
		progressDialog=new DProgressDialog(this);
		titleView = (TextView) this.findViewById(R.id.header_title_view);
		titleView.setText(R.string.groupbuy_liet_title);
		topButton=(ImageView)this.findViewById(R.id.good_list_top_button);
		
		refreshListView= (DListView) this.findViewById(R.id.groupbuy_refresh_listView);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		listView.setDivider(this.getResources().getDrawable(R.drawable.line_dotted));
		listView.setDividerHeight(2);
		listView.setAdapter(adapter);
	}

	private void initListener() {
		topButton.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//单击
				Intent intent=new Intent(GroupbuyListActivity.this,GoodsDetailsActivity.class);
				intent.putExtra("groupID", adapter.getItem(position).getGroupID());
				intent.putExtra("goodsID", adapter.getItem(position).getGoodsID());
				GroupbuyListActivity.this.startActivity(intent);
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					topButton.setVisibility(View.VISIBLE);
				} else {
					topButton.setVisibility(View.GONE);
				}
			}
		});
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        	
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	groupbuyModel.findGroupbuyList(currentPage);
	        }
        });
	}

	
	@Override
	public void onClick(View v) {
		if(v==topButton){
			//返回顶部
			if (!listView.isStackFromBottom()) {
				listView.setStackFromBottom(true);
			}
			listView.setStackFromBottom(false);
		}
	}
	
	class GroupbuyResponse extends DResponseAbstract{
		public GroupbuyResponse(Activity activity) {
			super(activity);
		}
		@Override
		public void onResponseStart() {
			progressDialog.dismiss();
		}
		@Override
		protected void onResponseEnd(boolean isMoreData) {
			refreshListView.onStopUpRefresh(isMoreData);
		}
		@Override
		protected void onResponseSuccess(ReturnBean bean, String message) {
			if(bean.getType()==DVolleyConstans.METHOD_QUERYALL){
				//商品列表查询结果
				List<TGroupbuy> list=(List<TGroupbuy>) bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			GroupbuyListActivity.this.resetData();
		}
	}
}
