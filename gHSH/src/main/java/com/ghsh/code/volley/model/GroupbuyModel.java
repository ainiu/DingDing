package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TGoods;
import com.ghsh.code.bean.TGroupbuy;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;

public class GroupbuyModel extends DVolleyModel{
	private final String groupbuy_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=seller_groupbuy&m=getGroupbuy_list");//商品查询
	private final String groupbuy_base_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=getgoodsinfo");//查询商品信息

	private DResponseService groupbuyListResponse=null;
	private DResponseService groupbuyDetailsResponse=null;
	public GroupbuyModel(Context context) {
		super(context);
	}
	/**查询团购列表*/
	public void findGroupbuyList(int currentPage){
		Map<String,String> params = this.newParams();
		params.put("currentPage",currentPage+"");//分页
		if(groupbuyListResponse==null){
			groupbuyListResponse=new GroupbuyListResponse(this);
		}
		DVolley.get(groupbuy_list_URL, params,groupbuyListResponse);
	}
	
	/**查询团购详情*/
	public void getGroupbuyDetails(String groupbuyID,String userID){
		Map<String,String> params = this.newParams();
		params.put("groupbuyID",groupbuyID);
		params.put("userID",userID);
		if(groupbuyDetailsResponse==null){
			groupbuyDetailsResponse=new GroupbuyDetailsResponse(this);
		}
		DVolley.get(groupbuy_base_URL, params,groupbuyDetailsResponse);
	}
	
	
	private class GroupbuyDetailsResponse extends DResponseService{
		public GroupbuyDetailsResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TGoods goods=new TGoods();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				//商品基本信息
				JSONObject baseObject=contentObject.getJSONObject("goodsBase");
				String goodsID=JSONUtil.getString(baseObject,"goodsID");//商品编号
				String goodsName=JSONUtil.getString(baseObject,"goodsName");//商品名称
				String goodsPrice=JSONUtil.getString(baseObject,"price");//价格
				String defaultImage=JSONUtil.getString(baseObject,"defaultImage");//价格
				String specName1=JSONUtil.getString(baseObject,"specName1");//属性1
				String specName2=JSONUtil.getString(baseObject,"specName2");//属性1
				String commentsNumber=JSONUtil.getString(baseObject,"commentsNumber");//评论数量
				String consultNumber=JSONUtil.getString(baseObject,"consultNumber");//咨询数量
				boolean isCollect=JSONUtil.getBoolean(baseObject,"isCollect");//是否收藏
				boolean isLevelDiscount=JSONUtil.getBoolean(baseObject,"isLevelDiscount");//是否有会员基本折扣
				boolean isPromoteDiscount=JSONUtil.getBoolean(baseObject,"isPromoteDiscount");//是否有促销折扣
				
//				goods.setGoodsID(goodsID);
//				goods.setName(goodsName);
//				goods.setPrice(goodsPrice);
//				goods.setSpecName1(specName1);
//				goods.setSpecName2(specName2);
//				goods.setCommentsNumber(commentsNumber);
//				goods.setConsultNumber(consultNumber);
//				goods.setCollect(isCollect);
//				goods.setLevelDiscount(isLevelDiscount);
//				goods.setPromoteDiscount(isPromoteDiscount);
//				if(!defaultImage.equals("")){
//					goods.setDefaultImage(DVolleyConstans.getServiceHost(defaultImage));
//				}
//				//商品图片
//				JSONArray imagesArray=JSONUtil.getJSONArray(contentObject, "images");
//				if(imagesArray!=null&&imagesArray.length()!=0){
//					for(int i=0;i<imagesArray.length();i++){
//						JSONObject imagesObject=imagesArray.getJSONObject(i);
//						String picID=JSONUtil.getString(imagesObject,"imageID");
//						String imageUrl=JSONUtil.getString(imagesObject,"imageUrl");
//						String thumbnail=JSONUtil.getString(imagesObject,"thumbnail");
//						String sortOrder=JSONUtil.getString(imagesObject,"sortOrder");
//						
//						TPic pic=new TPic();
//						pic.setPicID(picID);
//						if(!imageUrl.equals("")){
//							pic.setImageUrl(DVolleyConstans.getServiceHost(imageUrl));
//						}
//						if(!thumbnail.equals("")){
//							pic.setThumbnail(DVolleyConstans.getServiceHost(thumbnail));
//						}
//						goods.getPicList().add(pic);
//					}
//				}
//				
//				//商品规格参数
//				JSONArray specArray=JSONUtil.getJSONArray(contentObject,"specs");
//				if(specArray!=null&&specArray.length()!=0){
//					for(int i=0;i<specArray.length();i++){
//						JSONObject specObject=specArray.getJSONObject(i);
//						String specID=JSONUtil.getString(specObject,"specID");
//						String spec1=JSONUtil.getString(specObject,"spec1");
//						String spec2=JSONUtil.getString(specObject,"spec2");
//						String colorRGB=JSONUtil.getString(specObject,"colorRgb");
//						String price=JSONUtil.getString(specObject,"price");
//						String sourcePrice=JSONUtil.getString(specObject,"sourcePrice");
//						int stock=JSONUtil.getInt(specObject,"stock");
//						
//						TGoodsSpec goodsSpec=new TGoodsSpec();
//						goodsSpec.setSpecID(specID);
//						goodsSpec.setSpec1(spec1);
//						goodsSpec.setSpec2(spec2);
//						goodsSpec.setColorRGB(colorRGB);
//						goodsSpec.setPrice(price);
//						goodsSpec.setStock(stock);
//						goodsSpec.setSourcePrice(sourcePrice);
//						goods.getGoodsSpecList().add(goodsSpec);
//					}
//				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(goods);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GroupbuyListResponse extends DResponseService{
		public GroupbuyListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TGroupbuy> groupbuyList=new ArrayList<TGroupbuy>();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				JSONArray groupbuyArray=JSONUtil.getJSONArray(contentObject, "groupbuyList");
				if(groupbuyArray!=null&&groupbuyArray.length()!=0){
					for(int i=0;i<groupbuyArray.length();i++){
						JSONObject categoryObject=groupbuyArray.getJSONObject(i);
						String groupID=JSONUtil.getString(categoryObject,"groupID");
						String groupName=JSONUtil.getString(categoryObject,"groupName");
						String groupImage=JSONUtil.getString(categoryObject,"groupImage");
						String groupDesc=JSONUtil.getString(categoryObject,"groupDesc");
						String startTime=JSONUtil.getString(categoryObject,"startTime");
						String endTime=JSONUtil.getString(categoryObject,"endTime");
						String goodsID=JSONUtil.getString(categoryObject,"goodsID");
						String minQuantity=JSONUtil.getString(categoryObject,"minQuantity");
						String maxPerUser=JSONUtil.getString(categoryObject,"maxPerUser");
						String state=JSONUtil.getString(categoryObject,"state");
						boolean isRecommended=JSONUtil.getBoolean(categoryObject,"recommended");
						String views=JSONUtil.getString(categoryObject,"views");
						String sourcePrice=JSONUtil.getString(categoryObject,"sourcePrice");
						String goupPrice=JSONUtil.getString(categoryObject,"goupPrice");
						int saleNumber=JSONUtil.getInt(categoryObject,"saleNumber");
						
						TGroupbuy groupbuy=new TGroupbuy();
						groupbuy.setGroupID(groupID);
						groupbuy.setGroupName(groupName);
						groupbuy.setGroupDesc(groupDesc);
						groupbuy.setStartTime(startTime);
						groupbuy.setEndTime(endTime);
						groupbuy.setGoodsID(goodsID);
						groupbuy.setMinQuantity(minQuantity);
						groupbuy.setMaxPerUser(maxPerUser);
						groupbuy.setState(state);
						groupbuy.setRecommended(isRecommended);
						groupbuy.setViews(views);
						groupbuy.setGoupPrice(goupPrice);
						groupbuy.setSourcePrice(sourcePrice);
						groupbuy.setSaleNumber(saleNumber);
						if(!groupImage.equals("")){
							groupbuy.setGroupImage(DVolleyConstans.getServiceHost(groupImage));
						}
						groupbuyList.add(groupbuy);
					}
				}
				String pageCount=JSONUtil.getString(contentObject, "pageCount");
				returnBean.setPageCount(Integer.parseInt(pageCount));
			}
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(groupbuyList);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
