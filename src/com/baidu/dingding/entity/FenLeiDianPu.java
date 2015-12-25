package com.baidu.dingding.entity;

public class FenLeiDianPu {
	//"usrNumber":"SZhaitao",
	//"logPath":"http://183.234.117.210:9090/hello/upFile/shop_logo/3.jpg",
	//"shopName":"铜锣湾",
	//"shopId":"3",
	//"authentication":"已认证",
	//"country":"法国"
	private String id;
	private String name;
	private String authentication;
	private String logPath;
	private String country;
	private String usrName;
	public FenLeiDianPu() {
		super();
	}
	public FenLeiDianPu(String id, String name, String authentication,
			String logPath, String country,String usrName) {
		super();
		this.id = id;
		this.name = name;
		this.authentication = authentication;
		this.logPath = logPath;
		this.country = country;
		this.usrName=usrName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	@Override
	public String toString() {
		return "FenLeiDianPu [id=" + id + ", name=" + name
				+ ", authentication=" + authentication + ", logPath=" + logPath
				+ ", country=" + country + ", usrName=" + usrName + "]";
	}

}
