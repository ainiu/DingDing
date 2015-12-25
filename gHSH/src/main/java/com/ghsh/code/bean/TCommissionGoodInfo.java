package com.ghsh.code.bean;

/**分佣商品***/
public class TCommissionGoodInfo {
	private String goods_name;
	private String price;
	private String quantity;
	private String goodsImgURL;
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getGoodsImgURL() {
		return goodsImgURL;
	}
	public void setGoodsImgURL(String goodsImgURL) {
		this.goodsImgURL = goodsImgURL;
	}
	public TCommissionGoodInfo(String goods_name, String price, String quantity) {
		super();
		this.goods_name = goods_name;
		this.price = price;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CommissionGoodInfo [goods_name=" + goods_name + ", price="
				+ price + ", quantity=" + quantity + ", goodsImgURL="
				+ goodsImgURL + "]";
	}
}
