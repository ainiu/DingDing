package com.baidu.dingding.biz;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.dingding.entity.YanZhengMaEntity;

public class YanZhengMaBiz {
	public static YanZhengMaEntity getYanZhengMa(String key, String name){
		YanZhengMaEntity entity=new YanZhengMaEntity();
		try {
			JSONObject object = new JSONObject(name);
			JSONObject object2=object.getJSONObject("content");
			entity.setSessionId(object2.getString("sessionId"));
			entity.setLogPath(object2.getString("logPath"));
			entity.setCode(object2.getString("code"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

}
