package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TNews;
import com.ghsh.code.bean.TNewsCategory;
import com.ghsh.code.bean.TNewsHomeBean;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

/**
 * 新闻
 * */
public class NewsModel extends DVolleyModel {
	private final String find_news_home_list_URL = DVolleyConstans.getServiceHost("/phoneapi/index.php?c=news&m=homePage");
	private final String find_news_list_URL = DVolleyConstans.getServiceHost("/phoneapi/index.php?c=news&m=findNewsByCategoryID");
	private final String get_news_URL = DVolleyConstans.getServiceHost("/phoneapi/index.php?c=news&m=getNewsByID");
	private DResponseService findHomeNewListResponse = null;
	private DResponseService findNewListResponse = null;
	private DResponseService getNewByIDResponse = null;
	public NewsModel(Context context) {
		super(context);
	}

	/** 查询所有最新信息 */
	public void findHomeNewList() {
		Map<String, String> params = NewsModel.this.newParams();
		if (findHomeNewListResponse == null) {
			findHomeNewListResponse = new FindHomeNewListResponse(this);
		}
		DVolley.get(find_news_home_list_URL,params, findHomeNewListResponse);
	}
	
	/** 根据分类ID查询 */
	public void findNewstList(String categoryID,int currentPage) {
		Map<String, String> params = NewsModel.this.newParams();
		params.put("categoryID", categoryID);
		params.put("currentPage", currentPage+"");
		if (findNewListResponse == null) {
			findNewListResponse = new FindNewListResponse(this);
		}
		DVolley.get(find_news_list_URL,params, findNewListResponse);
	}
	/** 根据分类ID查询 */
	public void getNewsByID(String newsID) {
		Map<String, String> params = NewsModel.this.newParams();
		params.put("newsID", newsID);
		if (getNewByIDResponse == null) {
			getNewByIDResponse = new GetNewByIDResponse(this);
		}
		DVolley.get(get_news_URL,params, getNewByIDResponse);
	}
	
	private class GetNewByIDResponse extends DResponseService {
		public GetNewByIDResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean = new ReturnBean();
			JSONObject contentObject = callResult.getContentObject();
			if (contentObject != null && contentObject.length() != 0) {
				String newID = JSONUtil.getString(contentObject,"article_id");
				String title = JSONUtil.getString(contentObject,"title");
				String subTitle = JSONUtil.getString(contentObject,"description");
				String content = JSONUtil.getString(contentObject,"content");
				String addTime = JSONUtil.getString(contentObject,"add_time");
				
				TNews news=new TNews();
				news.setNewID(newID);
				news.setTitle(title);
				news.setSubTitle(subTitle);
				news.setContent(content);
				news.setAddTime(addTime);
				returnBean.setObject(news);
			}
			returnBean.setType(DVolleyConstans.METHOD_GET);
			volleyModel.onMessageResponse(returnBean, callResult.getResult(),callResult.getMessage(), null);
		}
	}
	private class FindNewListResponse extends DResponseService {
		public FindNewListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TNews> newsList=new ArrayList<TNews>();
			JSONArray contentArray = callResult.getContentArray();
			if (contentArray != null && contentArray.length() != 0) {
				if(contentArray!=null&&contentArray.length()!=0){
					for(int i=0;i<contentArray.length();i++){
						JSONObject arrayObject=contentArray.getJSONObject(i);
						String newID = JSONUtil.getString(arrayObject, "article_id");
						String title = JSONUtil.getString(arrayObject, "title");
						String subTitle = JSONUtil.getString(arrayObject,"description");
						String addTime = JSONUtil.getString(arrayObject, "add_time");
						String imageUrl = JSONUtil.getString(arrayObject, "image_url");
						
						TNews news=new TNews();
						news.setNewID(newID);
						news.setTitle(title);
						news.setSubTitle(subTitle);
						news.setAddTime(addTime);
						newsList.add(news);
						news.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
					}
				}
			}
			ReturnBean returnBean = new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(newsList);
			volleyModel.onMessageResponse(returnBean, callResult.getResult(),callResult.getMessage(), null);
		}
	}
	private class FindHomeNewListResponse extends DResponseService {
		public FindHomeNewListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TNewsHomeBean newsHomeBean = new TNewsHomeBean();
			JSONObject contentObject = callResult.getContentObject();
			if (contentObject != null && contentObject.length() != 0) {
				JSONArray categoryArray=JSONUtil.getJSONArray(contentObject, "category_list");
				if(categoryArray!=null&&categoryArray.length()!=0){
					List<TNewsCategory> categoryList=new ArrayList<TNewsCategory>();
					for(int i=0;i<categoryArray.length();i++){
						JSONObject categoryObject=categoryArray.getJSONObject(i);
						String id = JSONUtil.getString(categoryObject, "cat_id");
						String name = JSONUtil.getString(categoryObject, "cat_name");
						String imageUrl = JSONUtil.getString(categoryObject, "cat_img");
						
						TNewsCategory newsCategory=new TNewsCategory();
						newsCategory.setId(id);
						newsCategory.setName(name);
						newsCategory.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
						categoryList.add(newsCategory);
					}
					newsHomeBean.setCategoryList(categoryList);
				}
				JSONArray newArray=JSONUtil.getJSONArray(contentObject, "new_list");
				if(newArray!=null&&newArray.length()!=0){
					List<TNews> newsList=new ArrayList<TNews>();
					for(int i=0;i<newArray.length();i++){
						JSONObject arrayObject=newArray.getJSONObject(i);
						String newID = JSONUtil.getString(arrayObject, "article_id");
						String title = JSONUtil.getString(arrayObject, "title");
						String subTitle = JSONUtil.getString(arrayObject,"description");
						String addTime = JSONUtil.getString(arrayObject, "add_time");
						String imageUrl = JSONUtil.getString(arrayObject, "image_url");
						
						TNews news=new TNews();
						news.setNewID(newID);
						news.setTitle(title);
						news.setSubTitle(subTitle);
						news.setAddTime(addTime);
						news.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
						newsList.add(news);
					}
					newsHomeBean.setNewsList(newsList);
				}
			}
			ReturnBean returnBean = new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(newsHomeBean);
			volleyModel.onMessageResponse(returnBean, callResult.getResult(),callResult.getMessage(), null);
		}
	}
}
