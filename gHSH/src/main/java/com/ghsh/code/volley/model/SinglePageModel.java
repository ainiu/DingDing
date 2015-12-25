package com.ghsh.code.volley.model;

import java.util.Map;

import android.content.Context;

import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
/**
 * 商品评论model
 * */
public class SinglePageModel extends DVolleyModel{
	private final String singlePage_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=store&m=getSinglePage");
	private DResponseService singlePageResponse=null;
	public SinglePageModel(Context context) {
		super(context);
	}
	/**查询所有评论信息*/
	public void getSinglePage(String singlePageID){
		Map<String, String> params = SinglePageModel.this.newParams();  
		params.put("singlePageID", singlePageID);
		if(singlePageResponse==null){
			singlePageResponse=new SinglePageResponse(this);
		}
		DVolley.get(singlePage_URL, params,singlePageResponse);
	}
	
	
	private class SinglePageResponse extends DResponseService{
		public SinglePageResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			String htmlData =callResult.getContentString();
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(htmlData);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
}
