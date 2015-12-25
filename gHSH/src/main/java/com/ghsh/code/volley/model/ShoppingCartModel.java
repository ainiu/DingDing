package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TShoppingCart;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

public class ShoppingCartModel extends DVolleyModel{
	private final String shoppingCart_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cart&m=addCart");
	private final String shoppingCart_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cart&m=findAll");
	private final String shoppingCart_modify_number_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cart&m=modifyNumber");
	private final String shoppingCart_delete_more_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=cart&m=delMore");
	
	private DResponseService cartListResponse=null;
	private DResponseService addCartResponse=null;
	private DResponseService modifyNumberResponse=null;
	private DResponseService delMoreCartResponse=null;
	public ShoppingCartModel(Context context) {
		super(context);
	}
	
	/**获取购物车列表*/
	public void findCartList(String userID){
		Map<String, String> params = this.newParams();
		params.put("sessionID", VolleyUtil.getIdentity(mContext));
		params.put("userID", userID);
		if(cartListResponse==null){
			cartListResponse=new CartListResponse(this);
		}
		DVolley.get(shoppingCart_list_URL, params,cartListResponse);
	}
	/**加入购物车*/
	public void addCart(String userID,String goodsID,String groupID,String number,List<String> goodsAttrIDs){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("sessionID", VolleyUtil.getIdentity(mContext));
		params.put("goodsID", goodsID);
		params.put("groupID", groupID==null?"":groupID);
		params.put("goodsNumber", number);
		JSONArray cardArray=new JSONArray();
		for(int i=0;i<goodsAttrIDs.size();i++){
			cardArray.put(goodsAttrIDs.get(i));
		}
		params.put("goodsAttrIDs", cardArray.toString());
		if(addCartResponse==null){
			addCartResponse=new AddCartResponse(this);
		}
		DVolley.get(shoppingCart_add_URL, params,addCartResponse);
	}
	/**修改数量*/
	public void modifyCartNumber(String shoppingID,String quantity){
		Map<String, String> params = this.newParams();
		params.put("cartID", shoppingID);
		params.put("number", quantity);
		if(modifyNumberResponse==null){
			modifyNumberResponse=new ModifyNumberResponse(this);
		}
		DVolley.get(shoppingCart_modify_number_URL, params,modifyNumberResponse);
	}
	
	/**批量删除购物车商品*/
	public void delMoreCard(String userID,List<String> list){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		JSONArray cardArray=new JSONArray();
		for(int i=0;i<list.size();i++){
			cardArray.put(list.get(i));
		}
		params.put("cartIDs", cardArray.toString());
		if(delMoreCartResponse==null){
			delMoreCartResponse=new DelMoreCartResponse(this);
		}
		DVolley.get(shoppingCart_delete_more_URL, params,delMoreCartResponse);
	}
	
	private class CartListResponse extends DResponseService{
		public CartListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TShoppingCart> list=new ArrayList<TShoppingCart>();
			JSONArray shoppingCartArray=callResult.getContentArray();
			if(shoppingCartArray!=null&&shoppingCartArray.length()!=0){
				for(int i=0;i<shoppingCartArray.length();i++){
					JSONObject shoppingCartObject=shoppingCartArray.getJSONObject(i);
					
					String shoppingID=JSONUtil.getString(shoppingCartObject,"rec_id");
					String goodsID=JSONUtil.getString(shoppingCartObject,"goods_id");
					String goodsName=JSONUtil.getString(shoppingCartObject,"goods_name");
					String goodsPrice=JSONUtil.getString(shoppingCartObject,"goods_price");
					String sourcePrice=JSONUtil.getString(shoppingCartObject,"market_price");
					String goodsNumber=JSONUtil.getString(shoppingCartObject,"goods_number");
					String goodsImage=JSONUtil.getString(shoppingCartObject,"goods_img");
					String goodsAttr=JSONUtil.getString(shoppingCartObject,"goods_attr");
					int stock=JSONUtil.getInt(shoppingCartObject,"stockNumber");
					
					
					TShoppingCart shoppingCart=new TShoppingCart();
					shoppingCart.setShoppingID(shoppingID);
					shoppingCart.setGoodsID(goodsID);
					shoppingCart.setGoodsName(goodsName);
					shoppingCart.setGoodsPrice(goodsPrice);
					shoppingCart.setGoodsNumber(goodsNumber);
					shoppingCart.setStock(stock);
					shoppingCart.setGoodsAttr(goodsAttr);
					shoppingCart.setSelected(true);
					shoppingCart.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
					list.add(shoppingCart);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_SHOOPINGCART_QUERY_LIST);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class AddCartResponse extends DResponseService{
		public AddCartResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_SHOOPINGCART_ADD);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class ModifyNumberResponse extends DResponseService{
		public ModifyNumberResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_SHOOPINGCART_MODIFY_NUMBER);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null){
				String shoppingID=JSONUtil.getString(contentObject,"cartID");
				String quantity=JSONUtil.getString(contentObject,"goods_number");
				TShoppingCart shoppingCart=new TShoppingCart();
				shoppingCart.setShoppingID(shoppingID);
				shoppingCart.setGoodsNumber(quantity);
				returnBean.setObject(shoppingCart);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class DelMoreCartResponse extends DResponseService{
		public DelMoreCartResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONArray array=callResult.getContentArray();
			List<String> list=new ArrayList<String>();
			if(array!=null&&array.length()!=0){
				for(int i=0;i<array.length();i++){
					list.add(array.getString(i));
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_SHOOPINGCART_DELETE_MORE);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
