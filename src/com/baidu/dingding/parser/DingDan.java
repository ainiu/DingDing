package com.baidu.dingding.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.dingding.entity.AdressEntity;
import com.baidu.dingding.entity.WuLiuEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.HttpUtils;

public class DingDan {
	//" orderId ":"123456789",
	//" orderNo ":"M15082611171612312",
	//" createTime ":"2015-08-26 11:18:03",
	//" transTime ":"2015-08-26 11:20:03",
	//" deliveryTime ":"2015-08-26 17:18:03",
	//" receiverInfo ":
	//	" name ":"莫正巧",
	//	" province":"广东省",
	//	" city ":"深圳市",
	//	" area":"龙华新区",
	//	" receiveAddress ":"民治大道展涛科技大厦23A06室",
	//	" mobile":"15919848718",
	//	" post ":"518000"
	// "wlInfo":
	//" currentPlace ":"深圳站点",
	//"message":"已经揽货",
	//" datetime ":"2015-08-26 11:26:08"
	//" currentPlace ":"广州站点",
	//"message":"中转",
	//" datetime ":"2015-08-27 11:26:08"
	//  "goodsInfo":
	//	"id":"5726",
	//	"name":"测试商品1",
	//	" picPath ":"/pic/xxxx.jpg",
	//	"price":"5726",
	//	"goodsCount":"1",
	//	" freight ":"0",
	//	" totalPrice ":"30.8",
	//	"remark":"购买描述信息",
	//	" shopName ":"小小店铺",
	//	" shopId":"5",
	//	" sellerNumber ":"123456",
	//	" goodsSpecificationHTML ":"颜色<span class='cols'>[黄]</span>高度<span class='cols'>[165]</span>尺寸<span class='cols'>[XXL]</span>"
	private String url_path=Consts.DIANDAN;
	//把地址获取到
	private List<AdressEntity> getJson(){
		try {
			String str_json=HttpUtils.getContent(url_path);
			JSONObject object=new JSONObject(str_json);
			JSONArray array=object.getJSONArray("content");
			List<AdressEntity> list=parser(array);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	//把地址解析出来
	private List<AdressEntity> parser(JSONArray array) {
		// TODO Auto-generated method stub
		List<AdressEntity> adress=new ArrayList<AdressEntity>();
		try {
			for(int i=0;i<array.length();i++){
				JSONObject object=array.getJSONObject(i);
				AdressEntity entity=new AdressEntity();
				entity.setCity(object.getString("city"));
				entity.setCreateTime(object.getString("createTime"));
				entity.setMobile(object.getString("mobile"));
				entity.setName(object.getString("name"));
				entity.setOrderld(object.getString("orderId"));
				entity.setOrderNo(object.getString("orderNo"));
				entity.setPost(object.getString("post"));
				entity.setProvince(object.getString("province"));
				entity.setReceiveAddress(object.getString("receiveAddress"));
				entity.setReceiverInfo(object.getString("receiverInfo"));
				entity.setTransTime(object.getString("transTime"));
				adress.add(entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return adress;
	}
	//解析物流出来
	private List<WuLiuEntity> getjson(){
		String str_str=HttpUtils.getContent(url_path);
		try {
			JSONObject object=new JSONObject(str_str);
			JSONArray array=object.getJSONArray("wlInfo");
			List<WuLiuEntity> list=parserJson(array);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return null;
	}
	//把物流信息解析出了来
	private List<WuLiuEntity> parserJson(JSONArray array) {
		// TODO Auto-generated method stub
		List<WuLiuEntity> list=new ArrayList<WuLiuEntity>();
		for(int i=0;i<array.length();i++){
			try {
				JSONObject object=array.getJSONObject(i);
				WuLiuEntity entit=new WuLiuEntity();
				entit.setCurrentPlace(object.getString("currentPlace"));
				entit.setDetetime(object.getString("detetime"));
				entit.setMessage(object.getString("message"));
				list.add(entit);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return list;
	}

}
