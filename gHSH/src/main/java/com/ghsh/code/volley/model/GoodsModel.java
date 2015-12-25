package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TGoods;
import com.ghsh.code.bean.TGoodsAttr;
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

public class GoodsModel extends DVolleyModel{
	private final String goods_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=findAll");//商品查询
	private final String goods_base_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=getDetails");//查询商品信息
	private final String goods_desc_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=getDesc");//查询商品信息
	
	private DResponseService goodsListResponse;
	private DResponseService goodsDetailsResponse;
	private DResponseService goodsDescResponse;
	public GoodsModel(Context context) {
		super(context);
	}
	/**获取商品列表基本信息*/
	public void findGoodsList(String catalagID,String keyword,int currentPage,String sortName,String sort){
		if(catalagID==null){
			catalagID="";
		}
		if(keyword==null){
			keyword="";
		}
		Map<String,String> params = this.newParams();
		params.put("currentPage",currentPage+"");//分页
		params.put("cateID",catalagID);//分类编号
		params.put("keyword",VolleyUtil.encode(keyword));//关键字
		params.put("sortName",sortName);//排序名称
		params.put("order",sort);//排序
		if(goodsListResponse==null){
			goodsListResponse=new GoodsListResponse(this);
		}
		DVolley.get(goods_list_URL, params,goodsListResponse);
	}
	
	/**获取商品基本信息*/
	public void getGoodsDetails(String goodsID,String userID){
		Map<String,String> params = this.newParams();
		if(userID==null){
			userID="";
		}
		params.put("goodsID",goodsID);
		params.put("userID",userID);
		params.put("cityName",VolleyUtil.encode(LocationUtil.getCityName(mContext)));
		if(goodsDetailsResponse==null){
			goodsDetailsResponse=new GoodsDetailsResponse(this);
		}
		DVolley.get(goods_base_URL, params,goodsDetailsResponse);
	}
	/**获取商品描述*/
	public void getGoodsDesc(String goodsID){
		Map<String,String> params = this.newParams();
		params.put("goodsID",goodsID);
		if(goodsDescResponse==null){
			goodsDescResponse=new GoodsDescResponse(this);
		}
		DVolley.get(goods_desc_URL, params,goodsDescResponse);
	}
	
	
	private class GoodsDetailsResponse extends DResponseService{
		public GoodsDetailsResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TGoods goods=new TGoods();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				//商品基本信息
				JSONObject baseObject=contentObject.getJSONObject("goodsBase");
				String goodsID=JSONUtil.getString(baseObject,"goods_id");//商品编号
				String goodsName=JSONUtil.getString(baseObject,"goods_name");//商品名称
				String shopPrice=JSONUtil.getString(baseObject,"shop_price");//价格
				String marketPrice=JSONUtil.getString(baseObject,"market_price");//价格
				String defaultImage=JSONUtil.getString(baseObject,"goods_img");//价格
				
				String commentsNumber=JSONUtil.getString(baseObject,"collect_number");//评论数量
				String consultNumber=JSONUtil.getString(baseObject,"consultNumber");//咨询数量
				boolean isCollect=JSONUtil.getBoolean(baseObject,"isCollect");//是否收藏
				boolean isLevelDiscount=JSONUtil.getBoolean(baseObject,"isLevelDiscount");//是否有会员基本折扣
				boolean isPromote=JSONUtil.getBoolean(baseObject,"is_promote");//是否促销
				
				goods.setGoodsID(goodsID);
				goods.setName(goodsName);
				goods.setShopPrice(shopPrice);
				goods.setMarketPrice(marketPrice);
				goods.setCommentsNumber(commentsNumber);
				goods.setConsultNumber(consultNumber);
				goods.setCollect(isCollect);
				goods.setLevelDiscount(isLevelDiscount);
				goods.setPromote(isPromote);
				goods.setDefaultImage(DVolleyConstans.getServiceHost(defaultImage));
				
				//运费
				JSONObject deliveryObject=JSONUtil.getJSONObject(contentObject, "delivery");
				if(deliveryObject!=null){
					String shopCity=JSONUtil.getString(deliveryObject,"shopCity");//发货城市
					String deliveryFree=JSONUtil.getString(deliveryObject,"deliveryFree");//运费
					
					goods.setShopCity(shopCity);
					goods.setDeliveryFree(deliveryFree);
				}
				
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
						
						goods.getPicList().add(pic);
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
						goods.getGoodsAttrList().add(goodsAttr);
					}
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(goods);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GoodsListResponse extends DResponseService{
		public GoodsListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TGoods> goodsList=new ArrayList<TGoods>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject categoryObject=contentArray.getJSONObject(i);
					String goodsID=JSONUtil.getString(categoryObject,"goods_id");//商品编号
					String goodsName=JSONUtil.getString(categoryObject,"goods_name");//商品名称
					String shopPrice=JSONUtil.getString(categoryObject,"shop_price");//价格
					String marketPrice=JSONUtil.getString(categoryObject,"market_price");//原价
					String defaultImage=JSONUtil.getString(categoryObject,"goods_img");//图片
					String salesNumber=JSONUtil.getString(categoryObject,"salesCount");//销量
					String collectsNumber=JSONUtil.getString(categoryObject,"collectsNumber");//收藏数量
					String commentsNumber=JSONUtil.getString(categoryObject,"commentsNumber");//评论数量
					
					TGoods goods=new TGoods();
					goods.setGoodsID(goodsID);
					goods.setName(goodsName);
					goods.setShopPrice(shopPrice);
					goods.setMarketPrice(marketPrice);
					goods.setSalesNumber(salesNumber);
					goods.setCollectsNumber(collectsNumber);
					goods.setCommentsNumber(commentsNumber);
					if(!defaultImage.equals("")){
						goods.setDefaultImage(DVolleyConstans.getServiceHost(defaultImage));
					}
					goodsList.add(goods);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(goodsList);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GoodsDescResponse extends DResponseService{
		public GoodsDescResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
