package com.ghsh.code.volley.model;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TGoodsAttr;
import com.ghsh.code.bean.TGroup;
import com.ghsh.code.bean.TPic;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
import com.ghsh.util.LocationUtil;

public class GroupModel extends DVolleyModel{
	private final String group_details_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=group&m=getDetails");//查询商品信息
	
	private DResponseService groupDetailsResponse;
	public GroupModel(Context context) {
		super(context);
	}
	/**获取团购基本信息*/
	public void getGroupDetails(String goodsID,String userID){
		Map<String,String> params = this.newParams();
		if(userID==null){
			userID="";
		}
		params.put("groupID",goodsID);
		params.put("userID",userID);
		params.put("cityName",VolleyUtil.encode(LocationUtil.getCityName(mContext)));
		if(groupDetailsResponse==null){
			groupDetailsResponse=new GroupDetailsResponse(this);
		}
		DVolley.get(group_details_URL, params,groupDetailsResponse);
	}
	
	
	private class GroupDetailsResponse extends DResponseService{
		public GroupDetailsResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TGroup group=new TGroup();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				//团购基本信息
				JSONObject baseObject=contentObject.getJSONObject("groupBase");
				String goodsID=JSONUtil.getString(baseObject,"goods_id");
				String groupID=JSONUtil.getString(baseObject,"group_id");
				String groupName=JSONUtil.getString(baseObject,"group_name");
				String groupDesc=JSONUtil.getString(baseObject,"group_desc");
				String shopPrice=JSONUtil.getString(baseObject,"shop_price");
				String marketPrice=JSONUtil.getString(baseObject,"market_price");
				String goodsImg=JSONUtil.getString(baseObject,"goods_img");
				long countdownTotalTime=JSONUtil.getLong(baseObject,"countdown_time_total");
				
				group.setGoodsID(goodsID);
				group.setGroupID(groupID);
				group.setGroupName(groupName);
				group.setGroupDesc(groupDesc);
				group.setShopPrice(shopPrice);
				group.setMarketPrice(marketPrice);
				group.setGroupImage(DVolleyConstans.getServiceHost(goodsImg));
				group.setCountdownTotalTime(countdownTotalTime);
				//商品图片
				JSONArray imagesArray=JSONUtil.getJSONArray(contentObject, "goodsGallery");
				if(imagesArray!=null&&imagesArray.length()!=0){
					for(int i=0;i<imagesArray.length();i++){
						JSONObject imagesObject=imagesArray.getJSONObject(i);
						String picID=JSONUtil.getString(imagesObject,"img_id");
						String imageUrl=JSONUtil.getString(imagesObject,"img_url");
						String thumbnail=JSONUtil.getString(imagesObject,"img_original");
						
						TPic pic=new TPic();
						pic.setPicID(picID);
						pic.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
						pic.setSourceUrl(DVolleyConstans.getServiceHost(thumbnail));
						
						group.getPicList().add(pic);
					}
				}
				//商品属性
				JSONArray goodsAttrArray=JSONUtil.getJSONArray(contentObject,"goodsAttr");
				if(goodsAttrArray!=null&&goodsAttrArray.length()!=0){
					for(int i=0;i<goodsAttrArray.length();i++){
						JSONObject goodsAttrObject=goodsAttrArray.getJSONObject(i);
						
						int attrType=JSONUtil.getInt(goodsAttrObject,"attr_type");
						String attrName=JSONUtil.getString(goodsAttrObject,"attr_name");
						
						TGoodsAttr goodsAttr=new TGoodsAttr();
						goodsAttr.setAttrType(attrType);
						goodsAttr.setAttrName(attrName);
						
						JSONArray attrItemArray=JSONUtil.getJSONArray(goodsAttrObject,"attr_item_list");
						if(attrItemArray!=null&&attrItemArray.length()!=0){
							for(int j=0;j<attrItemArray.length();j++){
								JSONObject attrItemObject=attrItemArray.getJSONObject(j);
								
								TGoodsAttr.TGoodsAttrItem goodsAttrItem=new TGoodsAttr.TGoodsAttrItem();
								goodsAttrItem.goods_attr_id=JSONUtil.getString(attrItemObject,"goods_attr_id");
								goodsAttrItem.attr_id=JSONUtil.getString(attrItemObject,"attr_id");
								goodsAttrItem.attr_value=JSONUtil.getString(attrItemObject,"attr_value");
								goodsAttrItem.attr_price=JSONUtil.getString(attrItemObject,"attr_price");
								goodsAttrItem.attr_name=JSONUtil.getString(attrItemObject,"attr_name");
								goodsAttrItem.attr_type=JSONUtil.getInt(attrItemObject,"attr_type");
								goodsAttr.getAttrItemList().add(goodsAttrItem);
							}
						}
						group.getGoodsAttrList().add(goodsAttr);
					}
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(group);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
