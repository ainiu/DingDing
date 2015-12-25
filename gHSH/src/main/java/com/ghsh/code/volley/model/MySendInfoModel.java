package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TSendInfo;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
import com.ghsh.code.volley.util.VolleyUtil;

public class MySendInfoModel extends DVolleyModel {
	private final String mysendinfo_list_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=findAll");//我的收获地址
	private final String mysendinfo_add_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=add");
	private final String mysendinfo_modify_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=modify");
	private final String mysendinfo_delete_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=del");
	
	private final String modify_default_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=modifyDefaultAddress");
	
	private DResponseService sendInfoListResponse;
	private DResponseService addSendInfoResponse;
	private DResponseService modifySendInfoResponse;
	private DResponseService delSendInfoResponse;
	private DResponseService modifyDefaultResponse;
	public MySendInfoModel(Context context) {
		super(context);
	}
	/**查询收货地址*/
	public void findSendInfoList(String userID){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		if(sendInfoListResponse==null){
			sendInfoListResponse=new SendInfoListResponse(this);
		}
		DVolley.get(mysendinfo_list_URL, params,sendInfoListResponse);
	}
	
	
	/**添加收货地址*/
	public void addSendInfo(String userID,TSendInfo sendInfo){
		Map<String, String> params =this.newParams();
		params.put("userID", userID);
		params.put("consignee", VolleyUtil.encode(sendInfo.getConsignee()));
		params.put("province", sendInfo.getProvince());
		params.put("city", sendInfo.getCity());
		params.put("district", sendInfo.getDistrict());
		params.put("address", VolleyUtil.encode(sendInfo.getAddress()));
		params.put("zipcode", sendInfo.getZipcode());
		params.put("mobile", sendInfo.getMobile());
		params.put("isDefault", sendInfo.isDefault()?"1":"0");
		if(sendInfo.getTel()!=null){
			params.put("tel", sendInfo.getTel());
		}
		if(addSendInfoResponse==null){
			addSendInfoResponse=new AddSendInfoResponse(this);
		}
		DVolley.get(mysendinfo_add_URL, params,addSendInfoResponse);
	}
	/**修改收货地址*/
	public void modifySendInfo(String userID,TSendInfo sendInfo){
		Map<String, String> params =this.newParams();
		params.put("addressID", sendInfo.getSendInfoID());
		params.put("userID", userID);
		params.put("consignee", VolleyUtil.encode(sendInfo.getConsignee()));
		params.put("province", sendInfo.getProvince());
		params.put("city", sendInfo.getCity());
		params.put("district", sendInfo.getDistrict());
		params.put("address", VolleyUtil.encode(sendInfo.getAddress()));
		params.put("zipcode", sendInfo.getZipcode());
		params.put("mobile", sendInfo.getMobile());
		
		if(sendInfo.getTel()!=null){
			params.put("tel", sendInfo.getTel());
		}
		if(modifySendInfoResponse==null){
			modifySendInfoResponse=new ModifySendInfoResponse(this);
		}
		DVolley.get(mysendinfo_modify_URL, params,modifySendInfoResponse);
	}
	/**删除收货地址*/
	public void delSendInfo(String sendInfoID,String userID){
		Map<String, String> params =this.newParams();  
		params.put("addressID", sendInfoID);
		params.put("userID", userID);
		if(delSendInfoResponse==null){
			delSendInfoResponse=new DelSendInfoResponse(this);
		}
		DVolley.get(mysendinfo_delete_URL, params,delSendInfoResponse);
	}
	
	/**设置默认地址*/
	public void modifyDefault(String userID,String addressID){
		Map<String, String> params =this.newParams();  
		params.put("userID", userID);
		params.put("addressID", addressID);
		if(modifyDefaultResponse==null){
			modifyDefaultResponse=new ModifyDefaultResponse(this);
		}
		DVolley.get(modify_default_URL, params,modifyDefaultResponse);
	}
	private class SendInfoListResponse extends DResponseService{
		public SendInfoListResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONArray contentArray=callResult.getContentArray();
			List<TSendInfo> list=new ArrayList<TSendInfo>();
			if(contentArray!=null&&contentArray.length()!=0){
				for(int i=0;i<contentArray.length();i++){
					JSONObject sendInfoObject=contentArray.getJSONObject(i);
					
					String sendInfoID=JSONUtil.getString(sendInfoObject,"address_id");
					String consignee=JSONUtil.getString(sendInfoObject,"consignee");
					String country=JSONUtil.getString(sendInfoObject,"country");
					String province=JSONUtil.getString(sendInfoObject,"province");
					String city=JSONUtil.getString(sendInfoObject,"city");
					String district=JSONUtil.getString(sendInfoObject,"district");
					String regionName=JSONUtil.getString(sendInfoObject,"region_name");
					String address=JSONUtil.getString(sendInfoObject,"address");
					String zipcode=JSONUtil.getString(sendInfoObject,"zipcode");
					String tel=JSONUtil.getString(sendInfoObject,"tel");
					String mobile=JSONUtil.getString(sendInfoObject,"mobile");
					boolean isDefault=JSONUtil.getBoolean(sendInfoObject,"isDefault");
					
					TSendInfo sendInfo=new TSendInfo();
					sendInfo.setSendInfoID(sendInfoID);
					sendInfo.setConsignee(consignee);
					sendInfo.setRegionName(regionName);
					sendInfo.setAddress(address);
					sendInfo.setMobile(mobile);
					sendInfo.setTel(tel);
					sendInfo.setZipcode(zipcode);
					sendInfo.setDefault(isDefault);
					sendInfo.setCountry(country);
					sendInfo.setProvince(province);
					sendInfo.setCity(city);
					sendInfo.setDistrict(district);
					list.add(sendInfo);
				}
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class AddSendInfoResponse extends DResponseService{
		public AddSendInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setObject(callResult.getContentString());
			returnBean.setType(DVolleyConstans.METHOD_ADD);
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class ModifySendInfoResponse extends DResponseService{
		public ModifySendInfoResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_MODIFY);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
	private class DelSendInfoResponse extends DResponseService{
		public DelSendInfoResponse(DVolleyModel volleyModel) {
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
	private class ModifyDefaultResponse extends DResponseService{
		public ModifyDefaultResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_ADDRESS_MODIFY_DEFAULT);
			returnBean.setObject(callResult.getContentString());
			volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
		}
	}
}
