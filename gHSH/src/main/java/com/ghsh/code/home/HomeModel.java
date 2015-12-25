package com.ghsh.code.home;

import java.util.Map;

import android.content.Context;

import com.ghsh.Constants;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.util.FileUtils;

public class HomeModel extends DVolleyModel{
	
	private static final String query_home_page_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=home&m=getHome");
	
	private DResponseService queryHomePageResponse;
	
	private String cacheDataPath;//首页本地缓存数据
	
	public HomeModel(Context context) {
		super(context);
		cacheDataPath=Constants.getCachePath(context)+"/"+"homeData.data";
	}
	
	public void findHomePage(boolean isReadCache){
		Map<String, String> params =this.newParams();
		if(queryHomePageResponse==null){
			queryHomePageResponse=new QueryHomePageResponse(this);
		}
		DVolley.get(query_home_page_URL, params,queryHomePageResponse,isReadCache,cacheDataPath);
	}
	
	private class QueryHomePageResponse extends DResponseService{
		public QueryHomePageResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override 
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERY_HOME_PAGE);
			returnBean.setObject(callResult.getContentArray());
			if(!callResult.isCache()){
				//不是缓存，保存在本地
				FileUtils.writeStr(cacheDataPath, callResult.getResponse()+"");
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
