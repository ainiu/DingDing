package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TGoodsHistory;
import com.ghsh.code.bean.TSetting;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

public class PersonalModel extends DVolleyModel {
	
	private final String get_setting_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=store&m=getSetting");
	private final String my_goods_history_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=goods_history");
	private final String my_goods_history_del_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=del_history");
	
	private DResponseService getSettingResponse=null;
	private DResponseService goodsHistoryListResponse=null;
	private DResponseService goodsHistoryDelResponse=null;
	public PersonalModel(Context context) {
		super(context);
	}
	
	/**删除收藏*/
	public void deleteGoodsHistory(String id){
		Map<String, String> params =this.newParams();  
		params.put("id", id);
		if(goodsHistoryDelResponse==null){
			goodsHistoryDelResponse=new GoodsHistoryDelResponse(this);
		}
		DVolley.get(my_goods_history_del_URL, params,goodsHistoryDelResponse);
	}
	
	/**查询我的浏览记录*/
	public void findGoodsHistoryList(String userID,int currentPage){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		params.put("currentPage", currentPage+"");
		if(goodsHistoryListResponse==null){
			goodsHistoryListResponse=new GoodsHistoryListResponse(this);
		}
		DVolley.get(my_goods_history_list_URL, params,goodsHistoryListResponse);
	}
	
	/**查询设置信息*/
	public void getSetting(){
		Map<String, String> params = this.newParams();  
		if(getSettingResponse==null){
			getSettingResponse=new GetSettingResponse(this);
		}
		DVolley.get(get_setting_URL, params,getSettingResponse);
	}
	
	
	private class GetSettingResponse extends DResponseService{
		public GetSettingResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONObject contentObject=callResult.getContentObject();
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			if(contentObject!=null&&contentObject.length()!=0){
				String shopTel=JSONUtil.getString(contentObject, "tel");
				String shopWeb=JSONUtil.getString(contentObject, "http");
				TSetting setting=new TSetting();
				setting.setShopTel(shopTel);
				setting.setShopWeb(shopWeb);
				returnBean.setObject(setting);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GoodsHistoryListResponse extends DResponseService{
		public GoodsHistoryListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TGoodsHistory> list=new ArrayList<TGoodsHistory>();
			JSONObject cententObject=callResult.getContentObject();
			if(cententObject!=null&&cententObject.length()!=0){
				JSONArray goodsArray=JSONUtil.getJSONArray(cententObject, "historyList");
				if(goodsArray!=null&&goodsArray.length()!=0){
					for(int i=0;i<goodsArray.length();i++){
						JSONObject goodsObject=goodsArray.getJSONObject(i);
						String id=JSONUtil.getString(goodsObject,"id");
						String goodsID=JSONUtil.getString(goodsObject,"goodsID");
						String goodsName=JSONUtil.getString(goodsObject,"goodsName");
						String goodsImage=JSONUtil.getString(goodsObject,"goodsImage");
						String goodsPrice=JSONUtil.getString(goodsObject,"goodsPrice");
						TGoodsHistory goods=new TGoodsHistory();
						goods.setId(id);
						goods.setGoodsID(goodsID);
						goods.setGoodsName(goodsName);
						goods.setGoodsPrice(goodsPrice);
						if(!goodsImage.equals("")){
							goods.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
						}
						list.add(goods);
					}
				}
				String pageCount=JSONUtil.getString(cententObject, "pageCount");
				returnBean.setPageCount(Integer.parseInt(pageCount));
			}
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GoodsHistoryDelResponse extends DResponseService{
		public GoodsHistoryDelResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_DELETE);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
