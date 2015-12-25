package com.ghsh.share.qq;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户信息结构体。
 * 
 * @author jason
 * @since 2015-01-13
 */
public class QQUserBean {

	public String is_yellow_year_vip;// "0",
	public int ret;// 0,
	public String figureurl_qq_1;// "http://q.qlogo.cn/qqapp/1103973089/A3D1BFAAC032391CD48DA559DA61B31B/40",
	public String figureurl_qq_2;// "http://q.qlogo.cn/qqapp/1103973089/A3D1BFAAC032391CD48DA559DA61B31B/100",
	public String nickname;// "Asher",
	public String yellow_vip_level;// "0",
	public int is_lost;// 0,
	public String msg;// "",
	public String city;// "深圳",
	public String figureurl_1;// "http://qzapp.qlogo.cn/qzapp/1103973089/A3D1BFAAC032391CD48DA559DA61B31B/50",
	public String vip;// "0",
	public String level;// "0",
	public String figureurl_2;// "http://qzapp.qlogo.cn/qzapp/1103973089/A3D1BFAAC032391CD48DA559DA61B31B/100",
	public String province;// "广东",
	public String is_yellow_vip;// "0",
	public String gender;// "男",
	public String figureurl;// "http://qzapp.qlogo.cn/qzapp/1103973089/A3D1BFAAC032391CD48DA559DA61B31B/30"

	public static QQUserBean parse(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		return QQUserBean.parse(jsonObject);
	}

	public static QQUserBean parse(JSONObject jsonObject) {
		if (null == jsonObject) {
			return null;
		}
		QQUserBean user = new QQUserBean();
		user.is_yellow_year_vip = jsonObject.optString("is_yellow_year_vip", "");
		user.ret = jsonObject.optInt("ret", 0);
		user.figureurl_qq_1 = jsonObject.optString("figureurl_qq_1", "");
		user.figureurl_qq_2 = jsonObject.optString("figureurl_qq_2", "");
		user.nickname = jsonObject.optString("nickname", "");
		user.yellow_vip_level = jsonObject.optString("yellow_vip_level", "");
		user.is_lost = jsonObject.optInt("is_lost",0);
		user.msg = jsonObject.optString("msg", "");
		user.city = jsonObject.optString("city", "");
		user.figureurl_1 = jsonObject.optString("figureurl_1", "");
		user.vip = jsonObject.optString("vip", "");
		user.level = jsonObject.optString("level", "");
		user.figureurl_2 = jsonObject.optString("figureurl_2", "");
		user.province = jsonObject.optString("province", "");
		user.is_yellow_vip = jsonObject.optString("is_yellow_vip", "");
		user.gender = jsonObject.optString("gender",  "");
		user.figureurl = jsonObject.optString("figureurl","");
		return user;
	}
}
