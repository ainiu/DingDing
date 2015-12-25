package com.ghsh.adapter;

public interface GoodsListener {
	// 单击商品
	public void clickGoods(String isfixed, String href, String text);
	// 单击商品
	public void clickGoodsCategory(String categoryID);
}
