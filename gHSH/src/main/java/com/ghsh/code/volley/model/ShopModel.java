package com.ghsh.code.volley.model;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TShop;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;

public class ShopModel extends DVolleyModel{
	private final String get_coordinate_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=store&m=getCoordinate");
	private DResponseService getCoordinateResponse;
	
	public ShopModel(Context context) {
		super(context);
	}
	/**获取经纬度信息*/
	public void getCoordinate(){
		Map<String, String> params = this.newParams();  
		if(getCoordinateResponse==null){
			getCoordinateResponse=new GetCoordinateResponse(this);
		}
		DVolley.get(get_coordinate_URL, params,getCoordinateResponse);
	}
	
	private class GetCoordinateResponse extends DResponseService{
		public GetCoordinateResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String longitude=JSONUtil.getString(contentObject, "longitude");//经度
				String latitude=JSONUtil.getString(contentObject, "latitude");//纬度
				String desc=JSONUtil.getString(contentObject, "desc");
				String shopName=JSONUtil.getString(contentObject, "shopName");
				TShop shop=new TShop();
				shop.setShopName(shopName);
				shop.setDesc(desc);
				shop.setLongitude(longitude);
				shop.setLatitude(latitude);
				returnBean.setObject(shop);
			}
			returnBean.setType(DVolleyConstans.METHOD_GET);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
