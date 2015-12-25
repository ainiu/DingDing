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
	//	" name ":"Ī����",
	//	" province":"�㶫ʡ",
	//	" city ":"������",
	//	" area":"��������",
	//	" receiveAddress ":"���δ��չ�οƼ�����23A06��",
	//	" mobile":"15919848718",
	//	" post ":"518000"
	// "wlInfo":
	//" currentPlace ":"����վ��",
	//"message":"�Ѿ�����",
	//" datetime ":"2015-08-26 11:26:08"
	//" currentPlace ":"����վ��",
	//"message":"��ת",
	//" datetime ":"2015-08-27 11:26:08"
	//  "goodsInfo":
	//	"id":"5726",
	//	"name":"������Ʒ1",
	//	" picPath ":"/pic/xxxx.jpg",
	//	"price":"5726",
	//	"goodsCount":"1",
	//	" freight ":"0",
	//	" totalPrice ":"30.8",
	//	"remark":"����������Ϣ",
	//	" shopName ":"СС����",
	//	" shopId":"5",
	//	" sellerNumber ":"123456",
	//	" goodsSpecificationHTML ":"��ɫ<span class='cols'>[��]</span>�߶�<span class='cols'>[165]</span>�ߴ�<span class='cols'>[XXL]</span>"
	private String url_path=Consts.DIANDAN;
	//�ѵ�ַ��ȡ��
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
	//�ѵ�ַ��������
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
	//������������
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
	//��������Ϣ����������
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
