package com.baidu.dingding.entity;

public class AdressEntity {
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
	private String orderld;
	private String orderNo;
	private String createTime;	
	private String transTime;
	private String receiverInfo;
	private String name;
	private String province;
	private String city;
	private String area;
	private String receiveAddress;
	private String mobile;
	private String post;

	public AdressEntity() {
		super();
	}

	public AdressEntity(String orderld, String orderNo, String createTime,
			String transTime, String receiverInfo, String name,
			String province, String city, String area, String receiveAddress,
			String mobile, String post) {
		super();
		this.orderld = orderld;
		this.orderNo = orderNo;
		this.createTime = createTime;
		this.transTime = transTime;
		this.receiverInfo = receiverInfo;
		this.name = name;
		this.province = province;
		this.city = city;
		this.area = area;
		this.receiveAddress = receiveAddress;
		this.mobile = mobile;
		this.post = post;
	}

	public String getOrderld() {
		return orderld;
	}

	public void setOrderld(String orderld) {
		this.orderld = orderld;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getReceiverInfo() {
		return receiverInfo;
	}

	public void setReceiverInfo(String receiverInfo) {
		this.receiverInfo = receiverInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "AdressEntity [orderld=" + orderld + ", orderNo=" + orderNo
				+ ", createTime=" + createTime + ", transTime=" + transTime
				+ ", receiverInfo=" + receiverInfo + ", name=" + name
				+ ", province=" + province + ", city=" + city + ", area="
				+ area + ", receiveAddress=" + receiveAddress + ", mobile="
				+ mobile + ", post=" + post + "]";
	}
}
