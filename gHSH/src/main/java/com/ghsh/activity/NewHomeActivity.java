package com.ghsh.activity;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghsh.activity.base.BaseActivity;
import com.ghsh.adapter.NewHomeGridViewAdapter;
import com.ghsh.adapter.NewHomeListViewAdapter;
import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TNewsCategory;
import com.ghsh.code.bean.TNewsHomeBean;
import com.ghsh.code.volley.DResponseListener;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.NewsModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.R;
/**
 * 新闻首页
 * */
public class NewHomeActivity extends BaseActivity implements DResponseListener{
	private ListView newListView;
	private GridView newGridView;
	private NewHomeListViewAdapter newsListadapter = new NewHomeListViewAdapter(this, new ArrayList<TNews>());
	private NewHomeGridViewAdapter newsGridadapter = new NewHomeGridViewAdapter(this, new ArrayList<TNewsCategory>());
	private TextView title;
	private NewsModel newsModel;
	private DProgressDialog progressDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);
        this.initView();
        this.initListener();
        newsModel = new NewsModel(this);
        newsModel.addResponseListener(this);
        progressDialog.show();
        newsModel.findHomeNewList();
    }
    
	private void initView() {
		super.setBackButtonVisible(View.GONE);
		super.setBackExit(true);
		progressDialog=new DProgressDialog(this);
		title = (TextView) findViewById(R.id.header_title_view);
		title.setText("百科");
		
		
		newListView = (ListView) findViewById(R.id.content_view);
		newListView.addHeaderView(this.getView(R.layout.activity_news_home_top));
		newListView.setAdapter(newsListadapter);
	
		newGridView = (GridView) newListView.findViewById(R.id.news_gridView);
		newGridView.setAdapter(newsGridadapter);
	}

	private void initListener() {
		newGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(NewHomeActivity.this, NewListActivity.class);
				List<TNewsCategory> list=newsGridadapter.getList();
				Bundle bundle=new Bundle();
				for(int i=0;i<list.size();i++){
					bundle.putSerializable(i+"", list.get(i));
				}
				intent.putExtra("categoryID", newsGridadapter.getItem(position).getId());
				intent.putExtra("categoryList", bundle);
				NewHomeActivity.this.startActivity(intent);
			}
		});
		newListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				position=position-1;
				if(position<0){
					position=0;
				}
				TNews news=newsListadapter.getItem(position);
				Intent intent=new Intent(NewHomeActivity.this,NewDetailsActivity.class);
				intent.putExtra("newsID", news.getNewID());
				NewHomeActivity.this.startActivity(intent);
			}
		});
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
				//最新新闻列表
				TNewsHomeBean newsHomeBean=(TNewsHomeBean)bean.getObject();
				
				newsListadapter.setList(newsHomeBean.getNewsList());
				newsGridadapter.setList(newsHomeBean.getCategoryList());
				
				newsListadapter.notifyDataSetChanged();
				newsGridadapter.notifyDataSetChanged();
			}
		}
	}
}
