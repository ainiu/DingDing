package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TConsult;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
/**
 * 商品咨询model
 * */
public class ConsultModel extends DVolleyModel{
	private final String consult_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=qa&m=select");
	private final String consult_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=qa&m=add");
	private final String consult_satisfied_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=qa&m=allsatisfied");
	
	private DResponseService consultListResponse;
	private DResponseService consultAddResponse;
	private DResponseService consultSatisfiedResponse;
	
	public ConsultModel(Context context) {
		super(context);
	}
	/**查询所有咨询信息*/
	public void findConsultList(String goodsID,int currentPage,int type){
		Map<String, String> params = this.newParams();
		params.put("goodsID", goodsID);
		params.put("type", type+"");
		params.put("currentPage", currentPage+"");
		if(consultListResponse==null){
			consultListResponse=new ConsultListResponse(this);
		}
		DVolley.get(consult_list_URL, params,consultListResponse);
	}
	
	/**添加咨询信息*/
	public void addConsult(String userID,String type,String goodsID,String goodsName,String comment,boolean isAnonymous){
		Map<String, String> params = this.newParams();
		params.put("type", type);
		params.put("userID", userID);
		params.put("anonymous", (isAnonymous?1:0)+"");
		params.put("goodsID", goodsID);
		params.put("goodsName", VolleyUtil.encode(goodsName));
		params.put("content", VolleyUtil.encode(comment));
		if(consultAddResponse==null){
			consultAddResponse=new ConsultAddResponse(this);
		}
		DVolley.get(consult_add_URL, params,consultAddResponse);
	}
	
	/**添加 满意 不满意*/
	public void addSatisfied(String userID,String consultID,int type){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("type", type+"");
		params.put("consultID", consultID);
		if(consultSatisfiedResponse==null){
			consultSatisfiedResponse=new ConsultSatisfiedResponse(this);
		}
		DVolley.get(consult_satisfied_URL, params,consultSatisfiedResponse);
	}
	
	private class ConsultListResponse extends DResponseService{
		public ConsultListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TConsult> list=new ArrayList<TConsult>();
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				JSONArray consultArray=JSONUtil.getJSONArray(contentObject, "consultList");
				if(consultArray!=null&&consultArray.length()!=0){
					for(int i=0;i<consultArray.length();i++){
						JSONObject consultObject=consultArray.getJSONObject(i);
						String consultID=JSONUtil.getString(consultObject,"consultID");
						String questionContent=JSONUtil.getString(consultObject,"questionContent");
						String addTime=JSONUtil.getString(consultObject,"questionTime");
						String replyContent=JSONUtil.getString(consultObject,"replyContent");
						String replyTime=JSONUtil.getString(consultObject,"replyTime");
						int satisfied=JSONUtil.getInt(consultObject,"satisfied");
						int satisfiedNot=JSONUtil.getInt(consultObject,"satisfiedNot");
						String userName=JSONUtil.getString(consultObject,"userName");
						boolean anonymous=JSONUtil.getBoolean(consultObject,"anonymous");
						
						
						TConsult consult=new TConsult();
						consult.setConsultID(consultID);
						consult.setQuestionContent(questionContent);
						consult.setReplyTime(VolleyUtil.formatSecond(replyTime));
						consult.setReplyContent(replyContent);
						consult.setAddTime(VolleyUtil.formatSecond(addTime));
						consult.setUserName(userName);
						consult.setSatisfied(satisfied);
						consult.setSatisfiedNot(satisfiedNot);
						consult.setAnonymous(anonymous);
						list.add(consult);
					}
				}
			}
			returnBean.setType(DVolleyConstans.METHOD_CONSULT_QUERY_LIST);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class ConsultAddResponse extends DResponseService{
		public ConsultAddResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_CONSULT_ADD);
			returnBean.setObject(null);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class ConsultSatisfiedResponse extends DResponseService{
		public ConsultSatisfiedResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_CONSULT_ADD_SATISFIED);
			JSONObject contentObject=callResult.getContentObject();
			if(contentObject!=null&&contentObject.length()!=0){
				String consultID=JSONUtil.getString(contentObject,"consultID");
				boolean type=JSONUtil.getBoolean(contentObject,"type");
				TConsult consult=new TConsult();
				consult.setConsultID(consultID);
				if(type){
					consult.setSatisfied(1);
				}else{
					consult.setSatisfiedNot(1);
				}
				returnBean.setObject(consult);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
