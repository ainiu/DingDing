package com.ghsh.activity;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.NewListListViewAdapter;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TNewsCategory;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.NewsModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;
import com.ghsh.R;
/**
 * 新闻列表页面
 * */
public class NewListActivity extends BaseActivity{
	private DListView refreshListView;
	private ListView listView;
	private NewListListViewAdapter adapter = new NewListListViewAdapter(this, new ArrayList<TNews>());
	private TextView title;
	private LinearLayout newScrollLayout;
	private NewsModel newsModel; 
	private DProgressDialog progressDialog;
	private String categoryID;
	private int currentPage=1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        this.initView();
        this.initListener();
        this.initData();
        newsModel = new NewsModel(this);
        newsModel.addResponseListener(new NewsResponse(this));
        progressDialog.show();
        this.resetData();
    }
    
	private void initView() {
		progressDialog=new DProgressDialog(this);
		
		title = (TextView) findViewById(R.id.header_title_view);
		title.setText("新闻列表");
		
		newScrollLayout=(LinearLayout) findViewById(R.id.new_scroll_layout);
		
		refreshListView= (DListView) this.findViewById(R.id.content_view);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		listView.setAdapter(adapter);
		
	}

	private void initListener(){
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				TNews news=adapter.getItem(position);
				Intent intent=new Intent(NewListActivity.this,NewDetailsActivity.class);
				intent.putExtra("newsID", news.getNewID());
				NewListActivity.this.startActivity(intent);
			}
		});
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	newsModel.findNewstList(categoryID,currentPage);
	        }
        });
	}
	
	private void initData(){
		Intent intent=this.getIntent();
        categoryID=intent.getStringExtra("categoryID");
        Bundle categoryBudle=intent.getBundleExtra("categoryList");
        if(categoryBudle!=null&&categoryBudle.size()!=0){
			for(String key:categoryBudle.keySet()){
				TNewsCategory newsCategory=(TNewsCategory)categoryBudle.getSerializable(key);
				TextView textView=(TextView)this.getView(R.layout.activity_news_list_scroll_item);
				textView.setText(newsCategory.getName());
				textView.setTag(newsCategory.getId());
				if(newsCategory.getId().equals(categoryID)){
					textView.setSelected(true);
				}
				newScrollLayout.addView(textView);
				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						int count=newScrollLayout.getChildCount();
						for(int i=0;i<count;i++){
							TextView textView=(TextView)newScrollLayout.getChildAt(i);
							textView.setSelected(false);
						}
						TextView textView=(TextView)v;
						textView.setSelected(true);
						categoryID=textView.getTag()+"";
						progressDialog.show();
						NewListActivity.this.resetData();
//						newScrollLayout.scrollBy(100, 0);
					}
				});
			}
		}
	}
	
	class NewsResponse extends DResponseAbstract{
		public NewsResponse(Activity activity) {
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
				//返回查询所有收藏
				List<TNews> list=(List<TNews>) bean.getObject();
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
			NewListActivity.this.resetData();
		}
	}
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		newsModel.findNewstList(categoryID,currentPage);
	}
}
