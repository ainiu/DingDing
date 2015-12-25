package com.ghsh.code.volley.model;

import java.math.BigDecimal;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TPersonalBean;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 * 商品评论model
 * */
public class PageDataModel extends DVolleyModel{
	private final String get_persion_page_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user&m=getPersion");

	private DResponseService getPersionPageResponse=null;
	public PageDataModel(Context context) {
		super(context);
	}
	/**查询个人中心页面所需的数据*/
	public void getPersionPage(String userID){
		Map<String, String> params = this.newParams();
		if(userID==null){
			userID="";
		}
		params.put("userID", userID);
		if(getPersionPageResponse==null){
			getPersionPageResponse=new GetPersionPageResponse(this);
		}
		DVolley.get(get_persion_page_URL, params,getPersionPageResponse);
	}
	
	private class GetPersionPageResponse extends DResponseService{
		public GetPersionPageResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONObject allObject=callResult.getContentObject();
			ReturnBean returnBean=new ReturnBean();
			if(allObject!=null){
				String numberWaitPay=JSONUtil.getString(allObject, "number_wait_pay");//待付款
				String numberDeliveryPay=JSONUtil.getString(allObject, "number_delivery_pay");//待发货
				String numberReceiptPay=JSONUtil.getString(allObject, "number_receipt_pay");//待收货
				String numberCommentPay=JSONUtil.getString(allObject, "number_comment_pay");//待评论
				String portraitURL=JSONUtil.getString(allObject,"portrait");
				int growthPoint=JSONUtil.getInt(allObject, "growthPoint");//成长值
				String levleName=JSONUtil.getString(allObject,"levleName");//会员级别名称
				BigDecimal money=JSONUtil.getBigDecimal(allObject,"user_money");//会员级别名称
				
				
				TPersonalBean personalBean=new TPersonalBean();
				personalBean.setNumberWaitPay(numberWaitPay);
				personalBean.setNumberDeliveryPay(numberDeliveryPay);
				personalBean.setNumberReceiptPay(numberReceiptPay);
				personalBean.setNumberCommentPay(numberCommentPay);
				personalBean.setPortraitURL(DVolleyConstans.getServiceHost(portraitURL));
				personalBean.setGrowthPoint(growthPoint);
				personalBean.setLevleName(levleName);
				personalBean.setMoney(money);
				returnBean.setObject(personalBean);
			}
			returnBean.setType(DVolleyConstans.METHOD_GET);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
