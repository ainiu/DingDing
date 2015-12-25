package com.baidu.dingding.entity;

import java.io.Serializable;

/**新品上市*/
public class NewGoods implements Serializable{
	//"goodsId":"7260",
	//"storeNumber":"500",
	//"logPath":"http://183.234.117.210:9090/hello/upFile/spPic/7260.jpg",
	//"name":"hjkl",
	//"freight":"0.0",
	//"primalPrice":"3333.0"
	 String goodsId;
	 String storeNumber;
	 String logPath;
	 String name;
	 String freight;
	 String primalPrice;


	public NewGoods() {
		super();
	}

	public NewGoods(String goodsId, String storeNumber, String logPath,
			String name, String freight, String primalPrice) {
		super();
		this.goodsId = goodsId;
		this.storeNumber = storeNumber;           //库存数量
		this.logPath = logPath;                   //图片路径
		this.name = name;                       //商品名
		this.freight = freight;              //运费
		this.primalPrice = primalPrice;          //原价
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

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "NewGoods [goodsId=" + goodsId + ", storeNumber=" + storeNumber
				+ ", logPath=" + logPath + ", name=" + name + ", freight="
				+ freight + ", primalPrice=" + primalPrice + "]";
	}
}
