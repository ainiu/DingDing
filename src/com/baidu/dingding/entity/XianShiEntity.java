package com.baidu.dingding.entity;

import java.io.Serializable;

/*{
 "message": "��",
 "content": {
 "goodsList": [
 {
 "goodsId": "7287",
 "storeNumber": "999",
 "flag": "N",
 "logPath": "http://183.234.117.210:9090/hello/upFile/spPic/7287.jpg",
 "qgMax": "0",
 "productCategory": "���а���",
 "name": "KIRKLAND ��ƬCalcium+ά����D3 500��",
 "xsStart": "2015-10-23 18:35:00",
 "freight": "0.0",
 "primalPrice": "106.0",
 "xsPrice": "80.0",
 "xsEnd": "2015-10-23 23:59:00"
 }
 ],
 "serverTime": "2015-10-23 18:13:43"
 },
 "result": "0"
 }*/
public class XianShiEntity implements Serializable{

	private String goodsId; // ��Ʒid
	private String storeNumber; // �������
	private String flag; // �Ƿ��Ѿ���ʼ����(Y:�Ѿ���ʼ,N:û�п�ʼ)
	private String logPath; // ͼƬ·��
	private String qgMax; // �޹�����
	private String productCategory;// ��Ʒ���
	private String name; // ��Ʒ����
	private String xsStart; // �޹���ʼʱ��
	private String freight; // �˷�
	private String primalPrice; // ��Ʒԭ��
	private String xsPrice; // ��ʱ�۸�
	private String xsEnd; // ��ʱ����
	private String serverTime; // ����ʱ��

	public XianShiEntity(String goodsId, String storeNumber, String flag,
			String logPath, String qgMax, String productCategory, String name,
			String xsStart, String freight, String primalPrice, String xsPrice,
			String xsEnd, String serverTime) {
		super();
		this.goodsId = goodsId;
		this.storeNumber = storeNumber;
		this.flag = flag;
		this.logPath = logPath;
		this.qgMax = qgMax;
		this.productCategory = productCategory;
		this.name = name;
		this.xsStart = xsStart;
		this.freight = freight;
		this.primalPrice = primalPrice;
		this.xsPrice = xsPrice;
		this.xsEnd = xsEnd;
		this.serverTime = serverTime;
	}

	public XianShiEntity() {
		super();
	}

	@Override
	public String toString() {
		return "XianShiEntity [goodsId=" + goodsId + ", storeNumber="
				+ storeNumber + ", flag=" + flag + ", logPath=" + logPath
				+ ", qgMax=" + qgMax + ", productCategory=" + productCategory
				+ ", name=" + name + ", xsStart=" + xsStart + ", freight="
				+ freight + ", primalPrice=" + primalPrice + ", xsPrice="
				+ xsPrice + ", xsEnd=" + xsEnd + ", serverTime=" + serverTime
				+ "]";
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getQgMax() {
		return qgMax;
	}

	public void setQgMax(String qgMax) {
		this.qgMax = qgMax;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXsStart() {
		return xsStart;
	}

	public void setXsStart(String xsStart) {
		this.xsStart = xsStart;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getPrimalPrice() {
		return primalPrice;
	}

	public void setPrimalPrice(String primalPrice) {
		this.primalPrice = primalPrice;
	}

	public String getXsPrice() {
		return xsPrice;
	}

	public void setXsPrice(String xsPrice) {
		this.xsPrice = xsPrice;
	}

	public String getXsEnd() {
		return xsEnd;
	}

	public void setXsEnd(String xsEnd) {
		this.xsEnd = xsEnd;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
}
