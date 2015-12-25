package com.baidu.dingding.biz;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.HttpUtils;


public class FenLeiBiz {
	private String str_path=Consts.GOODSCATEGORY;
	public ArrayList<FenLei> getJson(String...params){
		String str_josn=HttpUtils.getContent(str_path);
		try {
			JSONObject object=new JSONObject(str_josn);
			JSONArray array=object.getJSONArray("content");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

}
