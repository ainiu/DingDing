package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TKeyword;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 * 搜索
 * */
public class SearchModel extends DVolleyModel{
	private final String keyword_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=search&m=keywordSeach");
	private KeywordResponse keywordResponse;
	public SearchModel(Context context) {
		super(context);
	}
	/**查询关键字*/
	public void queryKeyWork(){
		Map<String, String> params = SearchModel.this.newParams();  
		if(keywordResponse==null){
			keywordResponse=new KeywordResponse(this);
		}
		DVolley.get(keyword_URL, params,keywordResponse);
	}
	
	private class KeywordResponse extends DResponseService{
		public KeywordResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONArray cententArray=callResult.getContentArray();
			List<TKeyword> keywordList=new ArrayList<TKeyword>();
			if(cententArray!=null&&cententArray.length()!=0){
				for(int i=0;i<cententArray.length();i++){
					JSONObject cententObject=cententArray.getJSONObject(i);
					TKeyword keyword=new TKeyword();
					keyword.setKeyID(JSONUtil.getString(cententObject, "id"));
					keyword.setName(JSONUtil.getString(cententObject, "keyword"));
					keyword.setSortOrder(JSONUtil.getInt(cententObject, "sort_order"));
					keywordList.add(keyword);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(keywordList);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
