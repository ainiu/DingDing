package com.baidu.dingding.biz;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.dingding.entity.ChanPinLie;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.HttpUtils;

public class ChanPinLieBiz {
	private String str_path=Consts.NEWGOODS;
	//��������ȡ����
	private ArrayList<ChanPinLie> getJson(String...params){
		String str_json=HttpUtils.getContent(str_path);
		try {
			JSONObject object=new JSONObject(str_json);
			JSONArray array=object.getJSONArray("content");
			ArrayList<ChanPinLie>list=paserJson(array);
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//�����ݽ�������
	private ArrayList<ChanPinLie> paserJson(JSONArray array) {
		// TODO Auto-generated method stub
		ArrayList<ChanPinLie> chan=new ArrayList<ChanPinLie>();
		for(int i=0;i<array.length();i++){
			try {
				JSONObject object=array.getJSONObject(i);
				ChanPinLie lie=new ChanPinLie();
				lie.setContury(object.getString("contury"));
				lie.setImage(object.getInt(""));
				lie.setImagePath(object.getString(""));
				lie.setLaogPath(object.getString(""));
				lie.setPice(object.getString(""));
				lie.setTextMiao(object.getString(""));
				lie.setXiaoImage(object.getString(""));
				chan.add(lie);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return chan;
	}

}
