package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ghsh.activity.base.DResponseAbstract;
import com.ghsh.code.bean.TMessage;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
/**
 * 消息
 * */
public class MessageModel extends DVolleyModel {
	private final String find_message_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=message_management&m=getMessage");
	private final String delete_message_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=message_management&m=getMessage_delete");
	
	private final String find_new_message_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=message_management&m=getMessage_weidu");
	private final String set_message_status_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=message_management&m=getMessage_up_du");
	private DResponseService findMessageListResponse;
	private DResponseService findNewMessageListResponse;
	private DResponseService delMessageResponse;
	private DResponseService setMessageStauesResponse;
	public MessageModel(Context context) {
		super(context);
	}
	/**查询消息*/ 
	public void findMessageList(String userID,int currentPage){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		params.put("currentPage", currentPage+"");
		if(findMessageListResponse==null){
			findMessageListResponse=new FindMessageListResponse(this);
		}
		DVolley.get(find_message_list_URL, params,findMessageListResponse);
	}
	/**删除消息*/
	public void deleteMessage(String userID,String msgID){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		params.put("msgID", msgID);
		if(delMessageResponse==null){
			delMessageResponse=new DelMessageResponse(this);
		}
		DVolley.get(delete_message_list_URL, params,delMessageResponse);
	}
	/**查询最新消息*/ 
	public void findNewMessageList(String userID){
		Map<String, String> params = this.newParams();
		params.put("userID", userID);
		if(findNewMessageListResponse==null){
			findNewMessageListResponse=new FindNewMessageListResponse(this);
		}
		DVolley.get(find_new_message_list_URL, params,findNewMessageListResponse);
	}
	
	/**改变消息状态**/
	public void setMessageState(int state,String msg_ids,String userID){
		Map<String,String> params = this.newParams();
		params.put("userID", userID);
		params.put("msgIDs", msg_ids);
		if (setMessageStauesResponse==null) {
			setMessageStauesResponse = new SetMessageStauesResponse(this);
		}
		DVolley.get(set_message_status_URL, params,setMessageStauesResponse);
	}
	
	private class FindMessageListResponse extends DResponseService{
		public FindMessageListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		} 
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TMessage> list=new ArrayList<TMessage>();
			JSONObject cententObject=callResult.getContentObject();
			if(cententObject!=null&&cententObject.length()!=0){
				JSONArray messageArray=cententObject.getJSONArray("messageList");
				if(messageArray!=null&&messageArray.length()!=0){
					for(int i=0;i<messageArray.length();i++){
						JSONObject messageObject=messageArray.getJSONObject(i);
						String meaasgeID=JSONUtil.getString(messageObject,"msg_id");
						String title=JSONUtil.getString(messageObject,"title");
						String desc=JSONUtil.getString(messageObject,"content");
						String add_time=JSONUtil.getString(messageObject,"add_time");
						int status=JSONUtil.getInt(messageObject,"status");
						TMessage message=new TMessage();
						message.setMeaasgeID(meaasgeID);
						message.setTitle(title);
						message.setDesc(desc);
						message.setTime(VolleyUtil.formatSecond(add_time));
						message.setStatus(status);
						list.add(message);
					}
				}
				String pageCount=JSONUtil.getString(cententObject, "pageCount");
				returnBean.setPageCount(Integer.parseInt(pageCount));
			}
			returnBean.setObject(list);
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class DelMessageResponse extends DResponseService{
		public DelMessageResponse(DVolleyModel volleyModel) {
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
	
	private class FindNewMessageListResponse extends DResponseService{
		public FindNewMessageListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		} 
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			List<TMessage> list=new ArrayList<TMessage>();
			JSONArray contentArray=callResult.getContentArray();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject messageInfo=contentArray.getJSONObject(i);
					String meaasgeID=JSONUtil.getString(messageInfo,"msg_id");
					String title=JSONUtil.getString(messageInfo,"title");
					String desc=JSONUtil.getString(messageInfo,"content");
					String add_time=JSONUtil.getString(messageInfo,"add_time");
					int status=JSONUtil.getInt(messageInfo,"status");
					TMessage message=new TMessage();
					message.setMeaasgeID(meaasgeID);
					message.setTitle(title);
					message.setDesc(desc);
					message.setTime(VolleyUtil.formatSecond(add_time));
					message.setStatus(status);
					list.add(message);
				}
			}
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class SetMessageStauesResponse extends DResponseService{

		public SetMessageStauesResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}

		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
		}
	}
}
