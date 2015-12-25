package com.baidu.dingding.biz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.dingding.entity.PersinalCenterEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.HttpUtils;

public class PesinalCenterBiz {
	String str_path=Consts.COUNT;
	private List<PersinalCenterEntity> getJson(){
		try {
			String str_json=HttpUtils.getContent(str_path);
			JSONObject object=new JSONObject(str_json);
			JSONArray array=object.getJSONArray("content");
			List<PersinalCenterEntity> list=parserJson(array);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}	  
		return null;
	}
	private List<PersinalCenterEntity> parserJson(JSONArray array) {
		// TODO Auto-generated method stub
		List<PersinalCenterEntity> listv=new ArrayList<PersinalCenterEntity>();
		try {
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				PersinalCenterEntity entit=new PersinalCenterEntity();
				entit.setDeliveredCount(object.getString("deliveredCount"));
				entit.setFinishedCount(object.getString("finishedCount"));
				entit.setNonPaymentcount(object.getString("nonPaymentcount"));
				entit.setPaidCount(object.getString("paidCount"));
				entit.setReturnedCount(object.getString("returnedCount"));
				listv.add(entit);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listv;
	}
}
