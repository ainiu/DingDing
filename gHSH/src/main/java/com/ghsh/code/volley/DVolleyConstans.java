package com.ghsh.code.volley;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;

public class DVolleyConstans {
	public static final int RESULT_OK=1;//查询结果 返回 成功
	public static final int RESULT_FAIL=0;//查询结果 返回 失败
	public static final int RESULT_NOT_NEWWORK=2;//无网络
	
	//方法调用
	public static int METHOD_QUERYALL=1;
	public static int METHOD_ADD=2;
	public static int METHOD_DELETE=3;
	public static int METHOD_DELETE_MORE=4;
	public static int METHOD_MODIFY=5;
	public static int METHOD_GET=6;
	/**样式*/
	public static int METHOD_GET_STYLE_LOCALHOST=7;
	public static int METHOD_GET_STYLE_NETWORD=8;
	/**用户*/
	public static int METHOD_USER_MODIFY_PWD=11;//修改密码
	public static int METHOD_USER_LOGIN=12;//登录
	public static int METHOD_USER_REGISTER=13;//注册
	public static int METHOD_USER_REGISTER_VERIFY=14;//发送注册验证码
	public static int METHOD_USER_REGISTER_CONFIG=16;//注册配置
	public static int METHOD_USER_GET_BASEINFO=14;//获取用户基本信息
	public static int METHOD_USER_MODIFY_USERINFO=15;//修改用户信息
	public static int METHOD_USER_FORGETPWD_VERIFY_SEND=16;//找回密码发送验证码
	public static int METHOD_USER_FORGETPWD_VERIFY=17;//找回密码验证码 验证
	public static int METHOD_USER_FORGETPWD_NEW=18;//找回密码 设置新密码
	public static int METHOD_USER_MONEY_AND_POINT=19;//余额积分
	/**购物车*/
	public static int METHOD_SHOOPINGCART_QUERY_LIST=21;//获取购物车列表
	public static int METHOD_SHOOPINGCART_ADD=22;//添加购物车
	public static int METHOD_SHOOPINGCART_MODIFY_NUMBER=23;//修改购物车数量
	public static int METHOD_SHOOPINGCART_DELETE_MORE=24;//批量删除购物车
	/**咨询*/
	public static int METHOD_CONSULT_QUERY_LIST=31;//查询咨询列表
	public static int METHOD_CONSULT_ADD=32;//添加咨询
	public static int METHOD_CONSULT_ADD_SATISFIED=33;//添加咨询满意 不满意
	/**地址*/
	public static int METHOD_ADDRESS_MODIFY_DEFAULT=41;//设置默认地址
	/**订单*/
	public static int METHOD_ORDER_CANCE=101;//取消订单
	public static int METHOD_ORDER_RECEIPT=102;//确认收货
	public static int METHOD_ORDER_GET_CONFIRM_INFO=103;//查询确认订单信息
	public static int METHOD_ORDER_ADD=104;//添加订单
	public static int METHOD_ORDER_QUERY_NOT_COMMENT=105;//未评论的订单项列表
	public static int METHOD_ORDER_QUERY_EXPRESS_INFO=106;//获取运费信息
	public static int METHOD_ORDER_RETURN=107;//订单退款
	/**查询首页数据*/
	public static int METHOD_QUERY_HOME_PAGE=109;
	/**第三方*/
	public static int METHOD_USER_LOGIN_AUTH=110;//登录授权
	public static int METHOD_USER_LOGIN_SHARE=111;//第三方登录
	public static int METHOD_USER_LOGIN_BINDACCOUNT=112;//绑定帐号
	public static int METHOD_USER_LOGIN_WEIXIN_GET_ACCESSTOKEN=113;//微信获取AccessToken
	public static int METHOD_USER_LOGIN_WEIXIN_GET_USERINFO=114;//微信获取用户信息
	public static int METHOD_USER_LOGIN_SINA_GET_USERINFO=115;//新浪获取用户信息
	
	public static int METHOD_GET_STYLE_VERSION=100;//获取样式版本号
	public static int METHOD_GET_APK_VERSION=200;//获取安装包版本号
	
	private static Properties pso = new Properties();

	public static void initConfige(Context context) {
		try {
			AssetManager assetManager = context.getResources().getAssets();
			InputStream is = assetManager.open("config.properties");
			pso.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getServiceHost(String url) {
		if(url==null||url.equals("")){
			return "";
		}
		if(url.contains("dtouch.so")&&url.contains("?")){
			url=url+"&header=false";
		}
		//如果是http开头，则返回
		if(url.startsWith("http://")){
			return url;
		}
		//如果是www开头，则添加http
		if(url.startsWith("ww.")){
			return "http://"+url;
		}
		//如果开头不存在/,则添加
		if(!url.startsWith("/")){
			url="/"+url;
		}
		return pso.getProperty("serviceHost")+url;
	}
}
