package com.baidu.dingding.biz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.dingding.entity.FenLeiDianPu;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.HttpUtils;

//把数据解析出来
public class FenLeiDian {
	String paht=Consts.GOODSLIST;
	@SuppressWarnings("unused")
	//"usrNumber":"SZhaitao",
	//"logPath":"http://183.234.117.210:9090/hello/upFile/shop_logo/3.jpg",
	//"shopName":"铜锣湾",
	//"shopId":"3",
	//"authentication":"已认证",
	//"country":"法国"
	//把数据解析出来
	private List<FenLeiDianPu> dianfu() throws Exception{
		List<FenLeiDianPu> list=new ArrayList<FenLeiDianPu>();
		String jon=HttpUtils.getContent(paht);
		JSONArray array=new JSONArray(jon);
		for(int i=0;i<array.length();i++){
			JSONObject object=array.getJSONObject(i);
			FenLeiDianPu dian=new FenLeiDianPu();
			dian.setLogPath(object.getString("logPath"));
			dian.setAuthentication(object.getString("authentication"));
			dian.setCountry(object.getString("country"));
			dian.setId(object.getString("shopId"));
			dian.setName(object.getString("shopName"));
			dian.setUsrName(object.getString("usrNumber"));
			list.add(dian);
		}
		return list;
	}
}
