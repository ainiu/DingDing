package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TCollect;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;

public class MyCollectModel extends DVolleyModel {
	private final String myCollect_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=collect_goods&m=findAll");
	private final String myCollect_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=collect_goods&m=add");
	private final String myCollect_delete_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=collect_goods&m=del");
	private DResponseService collectListResponse=null;
	private DResponseService addCollectResponse=null;
	private DResponseService delCollectResponse=null;
	public MyCollectModel(Context context) {
		super(context);
	}
	/**查询我的收藏列表*/
	public void findCollectList(String userID,int currentPage){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		params.put("currentPage", currentPage+"");
		if(collectListResponse==null){
			collectListResponse=new CollectListResponse(this);
		}
		DVolley.get(myCollect_list_URL, params,collectListResponse);
	}
	
	/**添加收藏*/
	public void addCollect(String userID,String goodsID){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		params.put("goodsID", goodsID);
		if(addCollectResponse==null){
			addCollectResponse=new AddCollectResponse(this);
		}
		DVolley.get(myCollect_add_URL, params,addCollectResponse);
	}
	/**删除收藏*/
	public void deleteCollect(String userID,String goodsID){
		Map<String, String> params =this.newParams();  
		params.put("goodsID", goodsID);
		params.put("userID", userID);
		if(delCollectResponse==null){
			delCollectResponse=new DelCollectResponse(this);
		}
		DVolley.get(myCollect_delete_URL, params,delCollectResponse);
	}
	
	private class CollectListResponse extends DResponseService{
		public CollectListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TCollect> list=new ArrayList<TCollect>();
			JSONArray cententArray=callResult.getContentArray();
			if(cententArray!=null&&cententArray.length()!=0){
				for(int i=0;i<cententArray.length();i++){
					JSONObject goodsObject=cententArray.getJSONObject(i);
					String goodsID=JSONUtil.getString(goodsObject,"goods_id");
					String goodsName=JSONUtil.getString(goodsObject,"goods_name");
					String goodsImage=JSONUtil.getString(goodsObject,"goods_img");
					String goodsPrice=JSONUtil.getString(goodsObject,"shop_price");
					String collectNumber=JSONUtil.getString(goodsObject,"collect_number");
					TCollect goods=new TCollect();
					goods.setGoodsID(goodsID);
					goods.setGoodsName(goodsName);
					goods.setGoodsPrice(goodsPrice);
					goods.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
					goods.setCollectNumber(collectNumber);
					list.add(goods);
				}
			}
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class AddCollectResponse extends DResponseService{
		public AddCollectResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class DelCollectResponse extends DResponseService{
		public DelCollectResponse(DVolleyModel volleyModel) {
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
