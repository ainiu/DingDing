package com.ghsh.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghsh.Constants;
import com.ghsh.R;
import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.adapter.GoodsDetailsEvluateListViewtAdapter;
import com.ghsh.code.bean.TComment;
import com.ghsh.code.bean.TCommentBean;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.model.CommentModel;
import com.ghsh.dialog.DProgressDialog;
import com.ghsh.view.pullrefresh.DListView;
import com.ghsh.view.pullrefresh.base.DOnRefreshListener;

/**
 * 商品评价
 * */
public class GoodsDetailsEvaluateActivity extends Fragment implements OnClickListener{
	private DListView refreshListView;
	private ListView listView;
	private GoodsDetailsEvluateListViewtAdapter adapter;
	private CommentModel commentModel;
	private DProgressDialog progressDialog;
	private String goodsID;
	private int currentPage=1;
	private TextView funTextView1,funTextView2,funTextView3,funTextView4;
	private TextView funNumberView1,funNumberView2,funNumberView3,funNumberView4;
	private RelativeLayout funLayout1,funLayout2,funLayout3,funLayout4;
	private int commentType=Constants.COMMENT_TYPE0;
	private View view;
	public GoodsDetailsEvaluateActivity(String goodsID){
		this.goodsID= goodsID;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_goodsdetails_evaluate, null);
		this.initView();
		this.initListener();
		commentModel=new CommentModel(getActivity());
		commentModel.addResponseListener(new CommentResponse(this.getActivity(),view));
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		commentModel.getCommentNumber(goodsID);
		this.resetData();
	}
	
	private void initView() {
		progressDialog=new DProgressDialog(getActivity());
		
		funLayout1=(RelativeLayout)view.findViewById(R.id.goodsdetails_evaluate_fun_layout1);
		funLayout2=(RelativeLayout)view.findViewById(R.id.goodsdetails_evaluate_fun_layout2);
		funLayout3=(RelativeLayout)view.findViewById(R.id.goodsdetails_evaluate_fun_layout3);
		funLayout4=(RelativeLayout)view.findViewById(R.id.goodsdetails_evaluate_fun_layout4);
		
		funTextView1=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_text1);
		funTextView2=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_text2);
		funTextView3=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_text3);
		funTextView4=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_text4);
		
		funNumberView1=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_number1);
		funNumberView2=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_number2);
		funNumberView3=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_number3);
		funNumberView4=(TextView)view.findViewById(R.id.goodsdetails_evaluate_fun_number4);
		
		refreshListView=(DListView)view.findViewById(R.id.content_view);
		refreshListView.setPullDownEnabled(false);
		refreshListView.setPullUpEnabled(true);
		listView=refreshListView.getRefreshableView();
		listView.setDivider(this.getResources().getDrawable(R.drawable.line_dotted));
		listView.setDividerHeight(2);
		
		adapter=new GoodsDetailsEvluateListViewtAdapter(getActivity(),new ArrayList<TComment>());
		listView.setAdapter(adapter);
		
		this.setFunSelected(funTextView1, funNumberView1);
	}

	private void initListener() {
		funLayout1.setOnClickListener(this);
		funLayout2.setOnClickListener(this);
		funLayout3.setOnClickListener(this);
		funLayout4.setOnClickListener(this);
		refreshListView.setOnRefreshListener(new DOnRefreshListener() {
	        @Override
	        public void onPullDownToRefresh() {
	        }
	        @Override
	        public void onPullUpToRefresh() {
	        	commentModel.findCommentList(goodsID, currentPage,commentType);
	        }
        });
	}
	@Override
	public void onClick(View v) {
		if(v==funLayout1){
			this.setFunSelected(funTextView1, funNumberView1);
			commentType=Constants.COMMENT_TYPE0;
			this.resetData();
		}else if(v==funLayout2){
			this.setFunSelected(funTextView2, funNumberView2);
			commentType=Constants.COMMENT_TYPE3;
			this.resetData();
		}else if(v==funLayout3){
			this.setFunSelected(funTextView3, funNumberView3);
			commentType=Constants.COMMENT_TYPE2;
			this.resetData();
		}else if(v==funLayout4){
			this.setFunSelected(funTextView4, funNumberView4);
			commentType=Constants.COMMENT_TYPE1;
			this.resetData();
		}
	}
	private void setFunSelected(TextView funTextView,TextView funNumberView){
		funTextView1.setTextColor(this.getResources().getColor(R.color.font_color_333333));
		funTextView2.setTextColor(this.getResources().getColor(R.color.font_color_333333));
		funTextView3.setTextColor(this.getResources().getColor(R.color.font_color_333333));
		funTextView4.setTextColor(this.getResources().getColor(R.color.font_color_333333));
		
		funNumberView1.setTextColor(this.getResources().getColor(R.color.font_color_999999));
		funNumberView2.setTextColor(this.getResources().getColor(R.color.font_color_999999));
		funNumberView3.setTextColor(this.getResources().getColor(R.color.font_color_999999));
		funNumberView4.setTextColor(this.getResources().getColor(R.color.font_color_999999));
	}
	
	private void resetData(){
		progressDialog.show();
		currentPage=1;
		adapter.clearAllList();
		commentModel.findCommentList(goodsID, currentPage,commentType);
	}
	class CommentResponse extends DResponseAbstract{
		public CommentResponse(Activity activity,View globalView) {
			super(activity,globalView);
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
				//商品评价列表
				List<TComment> list=(List<TComment>)bean.getObject();
				if(list.size()==0&&adapter.getList().size()!=0){
					this.setMoreData(false);
					return;
				}
				adapter.addAllList(list);
				currentPage++;
			}else if(bean.getType()==DVolleyConstans.METHOD_GET){
				//获取数量
				TCommentBean commentBean=(TCommentBean)bean.getObject();
				if(commentBean!=null){
					funNumberView1.setText("("+commentBean.getAllCommentNumber()+")");
					funNumberView2.setText("("+commentBean.getGoodsCommentNumber()+")");
					funNumberView3.setText("("+commentBean.getMiddleCommentNumber()+")");
					funNumberView4.setText("("+commentBean.getSpreadCommentNumber()+")");
				}
			}
		}
		@Override
		protected boolean isShowEmptyPageView() {
			return adapter.getList().size()==0;
		}
		@Override
		protected void onRefresh() {
			commentModel.getCommentNumber(goodsID);
			GoodsDetailsEvaluateActivity.this.resetData();
		}
	}
}
