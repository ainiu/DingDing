package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TExpressinfo;
import com.ghsh.code.bean.TMyBonus;
import com.ghsh.code.bean.TOrder;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.bean.TPayment;
import com.ghsh.code.bean.TSendInfo;
import com.ghsh.code.bean.TShipping;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

public class OrderModel extends DVolleyModel {
	private final String confirm_order_info_URL = DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=getConfirmOrderInfo");
	private final String add_order_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=addOrder");
	private final String find_order_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=findOrderList");
	private final String cancel_order_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=cancelOrder");
	private final String receipt_order_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=receiptOrder");
	private final String get_order_detail_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=getOrderDetail");
	private final String get_express_info_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=getExpressInfo");
	private final String order_return_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=order&m=returnOrder");
	
	private DResponseService confirmOrderInfoResponse ;
	private DResponseService addOrderResponse;
	private DResponseService findOrderListResponse;
	private DResponseService canceOrderResponse;
	private DResponseService receiptOrderResponse;
	private DResponseService getOrderResponse;
	private DResponseService getExpressInfoResponse;
	private DResponseService orderReturnResponse;
	public OrderModel(Context context) {
		super(context);
	}
	/**查询订单详细信息 */
	public void getOrderDetail(String orderID) {
		Map<String, String> params = this.newParams();
		params.put("orderID", orderID);
		if(getOrderResponse==null){
			getOrderResponse=new GetOrderResponse(this);
		}
		DVolley.get(get_order_detail_URL, params, getOrderResponse);
	}
	/**查询确认订单信息 */
	public void getConfirmOrderInfo(String userID,String addressID,String shippingID,String paymentID,List<String> cartIDs) {
		Map<String, String> params = this.newParams();
		if(addressID==null){
			addressID="";
		}
		if(shippingID==null){
			shippingID="";
		}
		if(paymentID==null){
			paymentID="";
		}
		params.put("userID", userID);
		params.put("addressID", addressID);
		params.put("shippingID", shippingID);
		params.put("paymentID", paymentID);
		JSONArray cartIDsArray=new JSONArray();
		for(int i=0;i<cartIDs.size();i++){
			cartIDsArray.put(cartIDs.get(i));
		}
		params.put("cartIDs", cartIDsArray.toString());
		if(confirmOrderInfoResponse==null){
			confirmOrderInfoResponse=new ConfirmOrderInfoResponse(this);
		}
		DVolley.get(confirm_order_info_URL, params, confirmOrderInfoResponse);
	}
	
	/**订单提交*/
	public void addOrder(String userID,String paymentID,String shippingID,String addressID,String bonusID,String goodsAmount,String orderAmount,String shippingFee,String invPayee,String orderComment,List<String> cardIDList){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);//用户ID
		params.put("addressID", addressID);
		params.put("bonusID", bonusID);
		params.put("paymentID", paymentID);
		params.put("shippingID", shippingID);
		params.put("goodsAmount", goodsAmount);
		params.put("orderAmount", orderAmount);
		params.put("shippingFee", shippingFee);
		params.put("invPayee", VolleyUtil.encode(invPayee));//发票抬头
		params.put("invContent", VolleyUtil.encode(invPayee));//发票内容
		params.put("orderComment", VolleyUtil.encode(orderComment));//留言
		
		JSONArray cartIDArray=new JSONArray();
		for(String cartID:cardIDList){
			cartIDArray.put(cartID);
		}
		params.put("cartIDs", cartIDArray.toString());
		if(addOrderResponse==null){
			addOrderResponse=new AddOrderResponse(this);
		}
		DVolley.get(add_order_URL, params,addOrderResponse);
	}
	/**订单查询*/
	public void findOrderList(String userID,int orderType,int currentPage){
		Map<String, String> params = this.newParams();  
		params.put("userID", userID);//用户ID
		params.put("currentPage", currentPage+"");//当前页
		params.put("orderType",orderType+"");//所有订单
		if(findOrderListResponse==null){
			findOrderListResponse=new FindOrderListResponse(this);
		}
		DVolley.get(find_order_list_URL, params,findOrderListResponse);
	}
	
	/**取消订单*/
	public void cancelOrder(String orderID){
		Map<String, String> params = this.newParams();  
		params.put("orderID",orderID);
		if(canceOrderResponse==null){
			canceOrderResponse=new CanceOrderResponse(this);
		}
		DVolley.get(cancel_order_URL, params,canceOrderResponse);
	}
	/**确认收货*/
	public void receiptOrder(String orderID){
		Map<String, String> params = this.newParams();  
		params.put("orderID",orderID);
		if(receiptOrderResponse==null){
			receiptOrderResponse=new ReceiptOrderResponse(this);
		}
		DVolley.get(receipt_order_URL, params,receiptOrderResponse);
	}
	/**获取运费信息*/
	public void getExpressInfo(String orderID){
		Map<String, String> params = this.newParams();  
		params.put("orderID",orderID);
		if(getExpressInfoResponse==null){
			getExpressInfoResponse=new GetExpressInfoResponse(this);
		}
		DVolley.get(get_express_info_URL, params,getExpressInfoResponse);
	}
	/**订单退款 */
	public void orderReturn(String orderID,String desc) {
		Map<String, String> params = this.newParams();
		params.put("orderID", orderID);
		params.put("desc", desc);
		if(orderReturnResponse==null){
			orderReturnResponse=new OrderReturnResponse(this);
		}
		DVolley.get(order_return_URL, params, orderReturnResponse);
	}
	
	private class AddOrderResponse extends DResponseService{
		public AddOrderResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String orderID=JSONUtil.getString(contentObject, "order_id");
				String paymentID=JSONUtil.getString(contentObject, "payment_id");
				boolean isOnline=JSONUtil.getBoolean(contentObject, "is_online");
				TOrder order=new TOrder();
				order.setOrderID(orderID);
				TPayment payment=new TPayment();
				payment.setPaymentID(paymentID);
				payment.setOnline(isOnline);
				order.setPayment(payment);
				returnBean.setObject(order);
			}
			returnBean.setType(DVolleyConstans.METHOD_ORDER_ADD);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class GetOrderResponse extends DResponseService{
		public GetOrderResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TOrder order=new TOrder();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String orderID=JSONUtil.getString(contentObject, "order_id");
				String orderSN=JSONUtil.getString(contentObject, "order_sn");
				String orderStatus=JSONUtil.getString(contentObject,"order_status");
				String shippingStatus=JSONUtil.getString(contentObject,"shipping_status");
				String payStatus=JSONUtil.getString(contentObject,"pay_status");
				String orderType=JSONUtil.getString(contentObject,"order_type");
				String shippingFee=JSONUtil.getString(contentObject,"shipping_fee");
				String orderAmount=JSONUtil.getString(contentObject, "order_amount");
				String goodsAmount=JSONUtil.getString(contentObject, "goods_amount");
				String goodsTotalNumber=JSONUtil.getString(contentObject, "goods_number");
				String addTime=JSONUtil.getString(contentObject, "add_time");
				String confirmTime=JSONUtil.getString(contentObject, "confirm_time");
				String payTime=JSONUtil.getString(contentObject, "pay_time");
				String shipTime=JSONUtil.getString(contentObject, "shipping_time");
				String invType=JSONUtil.getString(contentObject, "inv_type");
				String invPayee=JSONUtil.getString(contentObject, "inv_payee");
				String invContent=JSONUtil.getString(contentObject, "inv_content");
				
				order.setOrderID(orderID);
				order.setOrderSN(orderSN);
				order.setOrderStatus(orderStatus);
				order.setShippingStatus(shippingStatus);
				order.setPayStatus(payStatus);
				order.setOrderType(orderType);
				order.setShippingFee(shippingFee);
				order.setGoodsAmount(goodsAmount);
				order.setOrderAmount(orderAmount);
				order.setGoodsNumber(goodsTotalNumber);
				order.setAddTime(addTime);
				order.setConfirmTime(confirmTime);
				order.setPayTime(payTime);
				order.setShipTime(shipTime);
				order.setInvType(invType);
				order.setInvPayee(invPayee);
				order.setInvContent(invContent);
				
				//订单明细
				JSONArray orderItemArray=JSONUtil.getJSONArray(contentObject, "orderGoodsList");
				if(orderItemArray!=null&&orderItemArray.length()!=0){
					for(int i=0;i<orderItemArray.length();i++){
						JSONObject orderItemObject=orderItemArray.getJSONObject(i);
						String cart_id = JSONUtil.getString(orderItemObject,"rec_id");
						String goodsNumber = JSONUtil.getString(orderItemObject,"goods_number");
						String goodsAttr = JSONUtil.getString(orderItemObject, "goods_attr");
						String market_price = JSONUtil.getString(orderItemObject, "market_price");
						String goodsPrice = JSONUtil.getString(orderItemObject, "goods_price");
						String goodsID = JSONUtil.getString(orderItemObject, "goods_id");
						String goodsName = JSONUtil.getString(orderItemObject, "goods_name");
						String goodsImage = JSONUtil.getString(orderItemObject, "goods_img");
						String goods_weight = JSONUtil.getString(orderItemObject, "goods_weight");
						
						TOrderItem orderItem=new TOrderItem();
						orderItem.setOrderItemID(cart_id);
						orderItem.setGoodsNumber(goodsNumber);
						orderItem.setGoodsName(goodsName);
						orderItem.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
						orderItem.setGoodsID(goodsID);
						orderItem.setGoodsPrice(goodsPrice);
						orderItem.setGoodsAttr(goodsAttr);
						order.getOrderItemList().add(orderItem);
					}
				}
				
				//默认配送地址
				JSONObject sendInfoObject=JSONUtil.getJSONObject(contentObject, "userAddress");
				if(sendInfoObject!=null&&sendInfoObject.length()!=0){
					String sendInfoID = JSONUtil.getString(sendInfoObject,"address_id");
					String consignee = JSONUtil.getString(sendInfoObject,"consignee");
					String address = JSONUtil.getString(sendInfoObject, "address");
					String zipcode = JSONUtil.getString(sendInfoObject, "zipcode");
					String tel = JSONUtil.getString(sendInfoObject, "tel");
					String mobile = JSONUtil.getString(sendInfoObject, "mobile");
					String regionName = JSONUtil.getString(sendInfoObject, "region_name");
					String country = JSONUtil.getString(sendInfoObject, "country");
					String province = JSONUtil.getString(sendInfoObject, "province");
					String city = JSONUtil.getString(sendInfoObject, "city");
					String district = JSONUtil.getString(sendInfoObject, "district");

					TSendInfo sendInfo = new TSendInfo();
					sendInfo.setSendInfoID(sendInfoID);
					sendInfo.setConsignee(consignee);
					sendInfo.setRegionName(regionName);
					sendInfo.setAddress(address);
					sendInfo.setMobile(mobile);
					sendInfo.setTel(tel);
					sendInfo.setZipcode(zipcode);
					sendInfo.setCountry(country);
					sendInfo.setProvince(province);
					sendInfo.setCity(city);
					sendInfo.setDistrict(district);
					order.setSendInfo(sendInfo);
				}
				//支付方式
				JSONObject paymentObject=JSONUtil.getJSONObject(contentObject, "payment");
				if(paymentObject!=null&&paymentObject.length()!=0){
					String paymentID=JSONUtil.getString(paymentObject, "pay_id");
					String paymentName=JSONUtil.getString(paymentObject, "pay_name");
					String paymentCode=JSONUtil.getString(paymentObject, "pay_code");
					TPayment payment=new TPayment();
					payment.setPaymentID(paymentID);
					payment.setPaymentName(paymentName);
					payment.setPaymentCode(paymentCode);
					order.setPayment(payment);
				}
				//配送方式
				JSONObject shippingObject=JSONUtil.getJSONObject(contentObject, "shipping");
				if(shippingObject!=null&&shippingObject.length()!=0){
					String shippingID=JSONUtil.getString(shippingObject, "shipping_id");
					String shippingName=JSONUtil.getString(shippingObject, "shipping_name");
					TShipping shipping=new TShipping();
					shipping.setShippingID(shippingID);
					shipping.setShippingName(shippingName);
					order.setShipping(shipping);
				}
				
				//红包
				JSONObject bonusObject=JSONUtil.getJSONObject(contentObject, "bonus");
				if(bonusObject!=null&&bonusObject.length()!=0){
					String bonusID=JSONUtil.getString(bonusObject, "bonus_id");
					String typeMoney=JSONUtil.getString(bonusObject, "type_money");
					String typeName=JSONUtil.getString(bonusObject, "type_name");
					TMyBonus myBonus=new TMyBonus();
					myBonus.setBonusID(bonusID);
					myBonus.setTypeName(typeName);
					myBonus.setTypeMoney(typeMoney);
					order.setMyBonus(myBonus);
				}
				JSONObject shopObject=JSONUtil.getJSONObject(contentObject, "shop");
				if(shopObject!=null&&shopObject.length()!=0){
					String servicePhone=JSONUtil.getString(shopObject, "service_phone");
					order.setShopPhone(servicePhone);
				}
				//店铺信息
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			returnBean.setObject(order);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class ConfirmOrderInfoResponse extends DResponseService {
		public ConfirmOrderInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TOrder order = new TOrder();
			JSONObject cententObject = callResult.getContentObject();
			if (cententObject != null && cententObject.length()!=0) {
				//订单
				String goodsTotalAmount = JSONUtil.getString(cententObject,"goods_amount");
				String goodsTotalWeight = JSONUtil.getString(cententObject,"goods_weight");
				String goodsTotalNumber = JSONUtil.getString(cententObject, "goods_number");
				String shipping_fee = JSONUtil.getString(cententObject, "shipping_fee");
				String order_amount = JSONUtil.getString(cententObject, "order_amount");
				
				order.setGoodsAmount(goodsTotalAmount);
				order.setOrderAmount(order_amount);
				order.setGoodsNumber(goodsTotalNumber);
				order.setShippingFee(shipping_fee);
				
				//订单明细
				JSONArray orderItemArray=JSONUtil.getJSONArray(cententObject, "orderItemList");
				if(orderItemArray!=null&&orderItemArray.length()!=0){
					for(int i=0;i<orderItemArray.length();i++){
						JSONObject orderItemObject=orderItemArray.getJSONObject(i);
						String cart_id = JSONUtil.getString(orderItemObject,"rec_id");
						String goodsNumber = JSONUtil.getString(orderItemObject,"goods_number");
						String goodsAttr = JSONUtil.getString(orderItemObject, "goods_attr");
						String market_price = JSONUtil.getString(orderItemObject, "market_price");
						String goodsPrice = JSONUtil.getString(orderItemObject, "goods_price");
						String goodsID = JSONUtil.getString(orderItemObject, "goods_id");
						String goodsName = JSONUtil.getString(orderItemObject, "goods_name");
						String goodsImage = JSONUtil.getString(orderItemObject, "goods_img");
						String goods_weight = JSONUtil.getString(orderItemObject, "goods_weight");
						
						TOrderItem orderItem=new TOrderItem();
						orderItem.setOrderItemID(cart_id);
						orderItem.setGoodsNumber(goodsNumber);
						orderItem.setGoodsName(goodsName);
						orderItem.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
						orderItem.setGoodsID(goodsID);
						orderItem.setGoodsPrice(goodsPrice);
						orderItem.setGoodsAttr(goodsAttr);
						order.getOrderItemList().add(orderItem);
					}
				}
				
				//默认配送地址
				JSONObject sendInfoObject=JSONUtil.getJSONObject(cententObject, "userAddress");
				if(sendInfoObject!=null&&sendInfoObject.length()!=0){
					String sendInfoID = JSONUtil.getString(sendInfoObject,"address_id");
					String consignee = JSONUtil.getString(sendInfoObject,"consignee");
					String address = JSONUtil.getString(sendInfoObject, "address");
					String zipcode = JSONUtil.getString(sendInfoObject, "zipcode");
					String tel = JSONUtil.getString(sendInfoObject, "tel");
					String mobile = JSONUtil.getString(sendInfoObject, "mobile");
					String regionName = JSONUtil.getString(sendInfoObject, "region_name");
					String country = JSONUtil.getString(sendInfoObject, "country");
					String province = JSONUtil.getString(sendInfoObject, "province");
					String city = JSONUtil.getString(sendInfoObject, "city");
					String district = JSONUtil.getString(sendInfoObject, "district");

					TSendInfo sendInfo = new TSendInfo();
					sendInfo.setSendInfoID(sendInfoID);
					sendInfo.setConsignee(consignee);
					sendInfo.setRegionName(regionName);
					sendInfo.setAddress(address);
					sendInfo.setMobile(mobile);
					sendInfo.setTel(tel);
					sendInfo.setZipcode(zipcode);
					sendInfo.setCountry(country);
					sendInfo.setProvince(province);
					sendInfo.setCity(city);
					sendInfo.setDistrict(district);
					order.setSendInfo(sendInfo);
				}
				//支付方式
				JSONObject paymentObject=JSONUtil.getJSONObject(cententObject, "payment");
				if(paymentObject!=null&&paymentObject.length()!=0){
					String paymentID=JSONUtil.getString(paymentObject, "pay_id");
					String paymentName=JSONUtil.getString(paymentObject, "pay_name");
					String paymentCode=JSONUtil.getString(paymentObject, "pay_code");
					TPayment payment=new TPayment();
					payment.setPaymentID(paymentID);
					payment.setPaymentName(paymentName);
					payment.setPaymentCode(paymentCode);
					order.setPayment(payment);
				}
				//配送方式
				JSONObject shippingObject=JSONUtil.getJSONObject(cententObject, "shipping");
				if(shippingObject!=null&&shippingObject.length()!=0){
					String shippingID=JSONUtil.getString(shippingObject, "shipping_id");
					String shippingName=JSONUtil.getString(shippingObject, "shipping_name");
					TShipping shipping=new TShipping();
					shipping.setShippingID(shippingID);
					shipping.setShippingName(shippingName);
					order.setShipping(shipping);
				}
			}
			ReturnBean returnBean = new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ORDER_GET_CONFIRM_INFO);
			returnBean.setObject(order);
			volleyModel.onMessageResponse(returnBean, callResult.getResult(),callResult.getMessage(), null);
		}
	}
	private class ReceiptOrderResponse extends DResponseService{
		public ReceiptOrderResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ORDER_RECEIPT);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class CanceOrderResponse extends DResponseService{
		public CanceOrderResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ORDER_CANCE);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class GetExpressInfoResponse extends DResponseService{
		public GetExpressInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			TExpressinfo expressinfo=new TExpressinfo();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String stateText=JSONUtil.getString(contentObject, "state_text");
				String shippingName=JSONUtil.getString(contentObject, "shipping_name");
				String invoiceNO=JSONUtil.getString(contentObject, "invoice_no");
				
				expressinfo.setStateText(stateText);
				expressinfo.setInvoiceNO(invoiceNO);
				expressinfo.setShippingName(shippingName);
				
				JSONArray dataArray=JSONUtil.getJSONArray(contentObject, "data");
				if(dataArray!=null&&dataArray.length()!=0){
					for (int i = 0; i < dataArray.length(); i++) {
						JSONObject dataObject=dataArray.getJSONObject(i);
						String time=JSONUtil.getString(dataObject, "time");
						String context=JSONUtil.getString(dataObject, "context");
						
						TExpressinfo.Info info =new TExpressinfo.Info();
						info.time=time;
						info.context=context;
						expressinfo.getInfoList().add(info);
					}
				}
			}
			
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ORDER_QUERY_EXPRESS_INFO);
			returnBean.setObject(expressinfo);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class OrderReturnResponse extends DResponseService{
		public OrderReturnResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ORDER_RETURN);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	
	private class FindOrderListResponse extends DResponseService{
		public FindOrderListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TOrder> list=new ArrayList<TOrder>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject orderObject=contentArray.getJSONObject(i);
					String orderID=JSONUtil.getString(orderObject,"order_id");
					String orderSN=JSONUtil.getString(orderObject,"order_sn");
					String goodsAmount=JSONUtil.getString(orderObject,"goods_amount");
					String orderAmount=JSONUtil.getString(orderObject,"order_amount");
					String shippingFee=JSONUtil.getString(orderObject,"shipping_fee");
					String orderStatus=JSONUtil.getString(orderObject,"order_status");
					String shippingStatus=JSONUtil.getString(orderObject,"shipping_status");
					String payStatus=JSONUtil.getString(orderObject,"pay_status");
					String orderType=JSONUtil.getString(orderObject,"order_type");
					
					TOrder order=new TOrder();
					order.setOrderID(orderID);
					order.setOrderSN(orderSN);
					order.setGoodsAmount(goodsAmount);
					order.setOrderAmount(orderAmount);
					order.setShippingFee(shippingFee);
					order.setOrderStatus(orderStatus);
					order.setShippingStatus(shippingStatus);
					order.setPayStatus(payStatus);
					order.setOrderType(orderType);
					
					JSONArray goodsArray=orderObject.getJSONArray("orderGoodsList");
					if(goodsArray!=null&&goodsArray.length()!=0){
						for(int j=0;j<goodsArray.length();j++){
							JSONObject goodsObject=goodsArray.getJSONObject(j);
							String orderItemID=JSONUtil.getString(goodsObject,"rec_id");
							String goodsID=JSONUtil.getString(goodsObject,"goods_id");
							String goodsName=JSONUtil.getString(goodsObject,"goods_name");
							String goodsPrice=JSONUtil.getString(goodsObject,"goods_price");
							String marketPrice=JSONUtil.getString(goodsObject,"market_price");
							String goodsNumber=JSONUtil.getString(goodsObject,"goods_number");
							String goodsAttr=JSONUtil.getString(goodsObject,"goods_attr");
							String goodsImage=JSONUtil.getString(goodsObject,"goods_img");
							String rank_integral=JSONUtil.getString(goodsObject,"rank_integral");
							
							TOrderItem orderItem=new TOrderItem();
							orderItem.setOrderItemID(orderItemID);
							orderItem.setGoodsID(goodsID);
							orderItem.setGoodsName(goodsName);
							orderItem.setGoodsPrice(goodsPrice);
							orderItem.setMarketPrice(marketPrice);
							orderItem.setGoodsAttr(goodsAttr);
							orderItem.setGoodsNumber(goodsNumber);
							orderItem.setGoodsImage(DVolleyConstans.getServiceHost(goodsImage));
							order.getOrderItemList().add(orderItem);
						}
					}
					list.add(order);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	
}
