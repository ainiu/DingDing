package com.ghsh.code.volley.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.ghsh.code.bean.TRegion;
import com.ghsh.code.volley.DCallResult;
import com.ghsh.code.volley.DResponseService;
import com.ghsh.code.volley.DVolley;
import com.ghsh.code.volley.DVolleyConstans;
import com.ghsh.code.volley.DVolleyModel;
import com.ghsh.code.volley.bean.ReturnBean;
import com.ghsh.code.volley.util.JSONUtil;
/**
 * 区域
 * */
public class RegionModel extends DVolleyModel{
	private final String region_URL=DVolleyConstans.getServiceHost("/phoneapi/index.php?c=user_address&m=findRegion");
	private final String defaultPid="1";//默认区域ID
	private DResponseService regionListByIDResponse=new RegionListByIDResponse(this);
	public RegionModel(Context context) {
		super(context);
	}
	/**根据ID 获取区域信息*/
	public void getRegionListByID(String pid){
		Map<String, String> params = RegionModel.this.newParams();
		if(pid==null){
			params.put("parentID", defaultPid);
		}else{
			params.put("parentID", pid);
		}
		DVolley.get(region_URL, params,regionListByIDResponse);
	}
	
	private class RegionListByIDResponse extends DResponseService{
		public RegionListByIDResponse(DVolleyModel volleyModel) {
			super(volleyModel);
		}
		@Override
		protected void myOnResponse(DCallResult callResult) throws Exception {
			JSONArray contentArray=callResult.getContentArray();
			List<TRegion> list=new ArrayList<TRegion>();
			for(int i=0;i<contentArray.length();i++){
				JSONObject retval=contentArray.getJSONObject(i);
				String regioID=JSONUtil.getString(retval,"region_id");
				String regionName=JSONUtil.getString(retval,"region_name");
				String parentID=JSONUtil.getString(retval,"parent_id");
				
				TRegion regio=new TRegion();
				regio.setRegioID(regioID);
				regio.setRegionName(regionName);
				regio.setParentID(parentID);
				list.add(regio);
			}
			ReturnBean returnBean=new ReturnBean();
			returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
			returnBean.setObject(list);
			volleyModel.onMessageResponse(returnBean,DVolleyConstans.RESULT_OK,"查询成功",null);
		}
	}
}
