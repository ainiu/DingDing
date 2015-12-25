package com.baidu.dingding.zhifu.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * 传递给智付插件参数实体
 */
public class OrderInfo {
	
	/*商家号*/
	private String merchant_code;
	/*服务器异步通知地址*/
	private String notify_url;
	/*接口版本*/
	private String interface_version;
	/*签名方式*/
	private String sign_type;
	/*商户网站唯一订单号*/
	private String order_no;
	/*商户订单时间     格式yyyy-MM-dd HH:mm:ss*/
	private String order_time;
	/*商户订单总金额    以元为单位，精确到小数点后两位*/
	private String order_amount;
	/*商品名称*/
	private String product_name;
	/*订单是否允许重复标识  可选*/
	private String redo_flag;
	/*商品编号   可选*/
	private String product_code;
	/*商品数量   可选*/
	private String product_num;
	/*商品描述   可选*/
	private String product_desc;
	/*公用回传参数    可选*/
	private String extra_return_param;
	/*子订单信息*/
	private String orders_info;
	
	private Map<String, String> map = new TreeMap<String, String>();//将参数组装然后自动进行排序
	

	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
		map.put("merchant_code", merchant_code);
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
		map.put("notify_url", notify_url);
	}
	public String getInterface_version() {
		return interface_version;
	}
	public void setInterface_version(String interface_version) {
		this.interface_version = interface_version;
		map.put("interface_version", interface_version);
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
		map.put("sign_type", sign_type);
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
		map.put("order_no", order_no);
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
		map.put("order_time", order_time);
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
		map.put("order_amount", order_amount);
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
		map.put("product_name", product_name);
	}
	public String getRedo_flag() {
		return redo_flag;
	}
	public void setRedo_flag(String redo_flag) {
		this.redo_flag = redo_flag;
		map.put("redo_flag", redo_flag);
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
		map.put("product_code", product_code);
	}
	public String getProduct_num() {
		return product_num;
	}
	public void setProduct_num(String product_num) {
		this.product_num = product_num;
		map.put("product_num", product_num);
	}
	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
		map.put("product_desc", product_desc);
	}
	public String getExtra_return_param() {
		return extra_return_param;
	}
	public void setExtra_return_param(String extra_return_param) {
		this.extra_return_param = extra_return_param;
		map.put("extra_return_param", extra_return_param);
	}
	public String getOrders_info() {
		return orders_info;
	}
	public void setOrders_info(String orders_info) {
		this.orders_info = orders_info;
		map.put("orders_info", orders_info);
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
}
