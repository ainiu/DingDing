package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TComment;
import com.ghsh.code.bean.TCommentBean;
import com.ghsh.code.bean.TOrderItem;
import com.ghsh.code.exception.AppViewException;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;
/**
 * 商品评论model
 * */
public class CommentModel extends DVolleyModel{
	private final String comment_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=goods&m=getGoodsComment");
	private final String comment_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=comment&m=addMoreGoodsComment");
	private final String comment_number_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=comment&m=getGoodsCommentNumber");
	
	private DResponseService commentListResponse=null;
	private DResponseService commentAddResponse=null;
	private DResponseService commentGetNumberResponse=null;
	public CommentModel(Context context) {
		super(context);
	}
	
	/**查询所有评论信息*/
	public void findCommentList(String goodsID,int currentPage,int type){
		Map<String, String> params = CommentModel.this.newParams();  
		params.put("goodsID", goodsID);
		params.put("currentPage", currentPage+"");
		params.put("commentType", type+"");//0 所有 1 好评 2 中评 3 差评
		if(commentListResponse==null){
			commentListResponse=new CommentListResponse(this);
		}
		DVolley.get(comment_list_URL, params,commentListResponse);
	}
	
	/**添加评论信息*/
	public void addMoreComment(String userID,String orderID,boolean anonymous,List<TOrderItem> orderItemList){
		Map<String, String> params = CommentModel.this.newParams();  
		params.put("userID", userID);//用户ID
		params.put("orderID", orderID);//订单编号
		params.put("anonymous", (anonymous?1:0)+"");//是否匿名
		params.put("comments",this.getCommentPostParams(orderItemList));
		if(commentAddResponse==null){
			commentAddResponse=new CommentAddResponse(this);
		}
		DVolley.get(comment_add_URL, params,commentAddResponse);
	}
	/**获取评论数量*/
	public void getCommentNumber(String goodsID){
		Map<String, String> params = this.newParams();  
		params.put("goodsID", goodsID);
		if(commentGetNumberResponse==null){
			commentGetNumberResponse=new CommentGetNumberResponse(this);
		}
		DVolley.get(comment_number_URL, params,commentGetNumberResponse);
	}
	private String getCommentPostParams(List<TOrderItem> orderItemList){
		JSONArray jsonArray=new JSONArray();
		try {
			for(TOrderItem item:orderItemList){
				JSONObject object=new JSONObject();
				object.put("goodsID", item.getGoodsID());
				if(item.getComment()==null){
					item.setComment("");
				}
				object.put("desc", VolleyUtil.encode(item.getComment()));
				object.put("evaluation", item.getEvaluation()+"");//综合平分
				jsonArray.put(object);
			}
		} catch (JSONException e) {
			AppViewException.onViewException(e);
			return "";
		}
		return jsonArray.toString();
	}
	private class CommentListResponse extends DResponseService{
		public CommentListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			List<TComment> list=new ArrayList<TComment>();
			JSONArray commentArray=callResult.getContentArray();
			if(commentArray!=null&&commentArray.length()!=0){
				if(commentArray!=null&&commentArray.length()!=0){
					for(int i=0;i<commentArray.length();i++){
						JSONObject commentObject=commentArray.getJSONObject(i);
						String commentID=JSONUtil.getString(commentObject,"comment_id");
						String userName=JSONUtil.getString(commentObject,"user_name");
						String addTime=JSONUtil.getString(commentObject,"add_time");
						String comment=JSONUtil.getString(commentObject,"content");
						String repleContent=JSONUtil.getString(commentObject,"reple_content");
						String repleTime=JSONUtil.getString(commentObject,"reple_time");
						int evaluation=JSONUtil.getInt(commentObject,"comment_rank");
						
						TComment tComment=new TComment();
						tComment.setCommentID(commentID);
						tComment.setUserName(userName);
						tComment.setComment(comment);
						tComment.setAddTime(addTime);
						tComment.setRepleTime(repleTime);
						tComment.setRepleContent(repleContent);
						tComment.setEvaluation(evaluation);
						list.add(tComment);
					}
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class CommentAddResponse extends DResponseService{
		public CommentAddResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		public void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	
	private class CommentGetNumberResponse extends DResponseService{
		public CommentGetNumberResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		public void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_GET);
			JSONObject cententObject=callResult.getContentObject();
			if(cententObject!=null&&cententObject.length()!=0){
				int allCommentNumber=JSONUtil.getInt(cententObject, "allCommentNumber");
				int goodsCommentNumber=JSONUtil.getInt(cententObject, "goodsCommentNumber");
				int middleCommentNumber=JSONUtil.getInt(cententObject, "middleCommentNumber");
				int spreadCommentNumber=JSONUtil.getInt(cententObject, "spreadCommentNumber");
				
				TCommentBean commentBean=new TCommentBean();
				commentBean.setAllCommentNumber(allCommentNumber);
				commentBean.setGoodsCommentNumber(goodsCommentNumber);
				commentBean.setMiddleCommentNumber(middleCommentNumber);
				commentBean.setSpreadCommentNumber(spreadCommentNumber);
				returnBean.setObject(commentBean);
			}
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
