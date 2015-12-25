package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TFeedback;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
/**
 * 留言model
 * */
public class OpinionModel extends DVolleyModel{
	private final String opinion_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=feedback&m=findAll");
	private final String opinion_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=feedback&m=add");
	
	private DResponseService findOpinionListResponse;
	private DResponseService addOpinionResponse=null;
	
	public OpinionModel(Context context) {
		super(context);
	}
	/**查询*/
	public void findOpinionList(String userID,int currentPage,int msgType){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("msgType", msgType+"");
		params.put("currentPage", currentPage+"");
		if(findOpinionListResponse==null){
			findOpinionListResponse=new FindOpinionListResponse(this);
		}
		DVolley.get(opinion_list_URL, params,findOpinionListResponse);
	}
	
	/**提交信息*/
	public void addOpinion(String userID,String title,String comment,int msgType){
		Map<String, String> params = this.newParams();
		params.put("userID",userID);
		params.put("title", VolleyUtil.encode(title));
		params.put("desc", VolleyUtil.encode(comment));
		params.put("msgType", msgType+"");
		if(addOpinionResponse==null){
			addOpinionResponse=new AddOpinionResponse(this);
		}
		DVolley.get(opinion_add_URL, params,addOpinionResponse);
	}
	
	private class FindOpinionListResponse extends DResponseService{
		public FindOpinionListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TFeedback> list=new ArrayList<TFeedback>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject contentObject=contentArray.getJSONObject(i);
					String msgID=JSONUtil.getString(contentObject,"msg_id");
					String msgTitle=JSONUtil.getString(contentObject,"msg_title");
					String msgContent=JSONUtil.getString(contentObject,"msg_content");
					String userName=JSONUtil.getString(contentObject,"user_name");
					String userEmail=JSONUtil.getString(contentObject,"user_email");
					String msgTime=JSONUtil.getString(contentObject,"msg_time");
					int msgType=JSONUtil.getInt(contentObject,"msg_type");
					
					TFeedback feedback=new TFeedback();
					feedback.setMsgID(msgID);
					feedback.setMsgTitle(msgTitle);
					feedback.setMsgContent(msgContent);
					feedback.setMsgTime(msgTime);
					feedback.setUserName(userName);
					feedback.setUserEmail(userEmail);
					feedback.setMsgType(msgType+"");
					
					JSONArray replyList=JSONUtil.getJSONArray(contentObject, "reply_list");
					if(replyList!=null&&replyList.length()!=0){
						for(int j=0;j<replyList.length();j++){
							JSONObject itemObject=replyList.getJSONObject(j);
							String msgIDItem=JSONUtil.getString(itemObject,"msg_id");
							String msgContentItem=JSONUtil.getString(itemObject,"msg_content");
							String msgTimeItem=JSONUtil.getString(itemObject,"msg_time");
							
							TFeedback feedbackItem=new TFeedback();
							feedbackItem.setMsgID(msgIDItem);
							feedbackItem.setMsgContent(msgContentItem);
							feedbackItem.setMsgTime(msgTimeItem);
							feedback.getFeedbackList().add(feedbackItem);
						}
					}
					list.add(feedback);
				}
			}
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class AddOpinionResponse extends DResponseService{
		public AddOpinionResponse(DVolleyModel volleyModel) {
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
}
