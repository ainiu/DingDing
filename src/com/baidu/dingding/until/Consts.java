package com.baidu.dingding.until;

public class Consts {
	public static String HTTP_PATH = "http://183.234.117.210:9090/AppServer/";
	// token???
	public static String HTTPURL = HTTP_PATH + "security/createToken?";
	//用户安全设置接口
	public static String USER_SAFETYINTEFACE = HTTP_PATH + "user/createSecurity?";
	//用户安全设置查询接口
	public static String USER_SAFETYIN_SEARCH_TEFACE = HTTP_PATH + "user/searchSecurity";
	//会员修改密码接口
	public static String CHECK_PASSWORD=HTTP_PATH + "user/updateUserPassword?";
	//头像接口
	public static String TOUXIANGJIEKOU_PATH = HTTP_PATH + "userInfo/userheaderImage?uploadType=upload";
	//点单列表：用于查看用户的所有订单以及根据状态条件筛选.
	public static String GEREN_ORDERLIST = HTTP_PATH+"orderManage /orderList?";
	//收藏夹列表接口
	public static String SHOUCHANGJIA=HTTP_PATH+"collectionManage/goodsCollectionList?pageSize=100&currentPage=1";
	//删除收藏夹商品接口
	public static String DELETESHOUCHANGJIA=HTTP_PATH+"collectionManage/deleteGoodsCollection?";
	// ??????????
	public static String YZM_IMAGVIEW_PATH = HTTP_PATH + "security/createVerifyCodeImage";
	// ???????
	public static String YANZHENG = HTTP_PATH + "security/createVerifyCode?";
	// 注册
	public static String REGIS = HTTP_PATH + "user/regiestAccount?";
	//搜索
	public static String SOUYE_SOUSHUO=HTTP_PATH+"goods/goodsList?pageSize=10";
	//海外精品
	public static String HAIWAI_JINGPIN=HTTP_PATH+"goods/goodsHotSale?currentPage=1&pageSize=20";
	//海外首页轮播
	public static String HAIWAILUNBO=HTTP_PATH+"overseas/overseasRollPic?";
	//海外地址查询接口
	public static String HAIWAIDIZHI=HTTP_PATH+"overseas/overseasCategory?";
	//登录
	public static String LOGIN = HTTP_PATH + "user/login?";
	// ????????????????
	public static String ZHAOHUI = HTTP_PATH + "user/sendVerifyCode?";
	// ??????
	public static String CHONGZHI = HTTP_PATH + "user/resetPassword?";
	// type=4限时抢购和type=2特价接口
	public static String XIANSHI = HTTP_PATH + "goods/searchGoodsByType?";
	// 新品上市
	public static String NEWGOODS = HTTP_PATH + "goods/searchNewGoods?";
	// 商品分类一二级查询URL
	public static String GOODSCATEGORY = HTTP_PATH + "category/goodsCategory?";
	// 商品分类三级查询URL
	public static String GOODSLIST = HTTP_PATH + "goods/goodsList?operationType=2&pageSize=10";
	// 店铺查询商品
	public static String GOODSLIST_SHOING = HTTP_PATH + "goods/goodsList?operationType=3&pageSize=10";
	// 店铺接口
	public static String SHOPLIST = HTTP_PATH + "shop/shopList";
	// 海外原产地接口
	public static String YCDLIST = HTTP_PATH + "ycd/ycdList";
	//海外详情
	public static String GOODSDETAIL = HTTP_PATH + "goods/goodsDetail?";
	//海外店铺推荐
	public static String HAIWAIDIANPU = HTTP_PATH + "goods/goodsList?operationType=5";
	// ??????????
	public static String LEAVEMESSAGELIST = HTTP_PATH+ "goodsMessage/leaveMessageList?";
	//给卖家留言
	public static String SAVELEAVEMESSAGE = HTTP_PATH+ "goodsMessage /saveleaveMessage?";
	//商品加入收藏夹接口
	public static String ADDGOODSCOLLECTION=HTTP_PATH+"collectionManage/addGoodsCollection?";
	// 个人信息查询接口
	public static String SEARCHPERIONINFO = HTTP_PATH+ "userInfo/searchPersionInfo?";
	//个人信息保存接口
	public static String SAVEPERSIONINFO = HTTP_PATH+ "userInfo/savePersionInfo?";
	// 收货地址查询
	public static String SEARCHRECEIVEADDRESSLIST = HTTP_PATH+ "receveAddress/searchReceiveaddressList?";
	// 收货地址明细查询
	public static String SEARCHRECEIVEADDRESS = HTTP_PATH+ "receveAddress/searchReceiveaddress?";
	//删除收货地址
	public static String DELETERECEIVEADDRESS = HTTP_PATH+ "receveAddress/deleteReceiveaddress?";
	//保存收货地址
	public static  String SAVERECEIVEADDRESS=HTTP_PATH+"receveAddress/saveReceiveaddress?";
	// ???????????
	public static String COUNT = HTTP_PATH + "orderInfo/orderStatusCount?";
	//加入购物车接口
	public static String ADDGOODSCAR=HTTP_PATH+"shoppingCar/addGoodsCar?";
	//删除购物车接口
	public static String DELETESHOPPINGCAR=HTTP_PATH+"shoppingCar/deleteShoppingCar?";
	//修改购物车数量数量
	public static  String CHANGEGOODSCOUNT =HTTP_PATH+"shoppingCar/changeGoodsCount?";
	//查看购物车
	public static  String SHOPPINGCARLIST=HTTP_PATH+"shoppingCar/shoppingCarList?";
	//生成订单
	public static  String CREATE_DINDAN=HTTP_PATH+"orderManage/createOrders";
	//订单支付
	public static String TOPAY=HTTP_PATH+"orderManage/toPay?payType=1";
	//订单列表接口
	public static String DIANDAN = HTTP_PATH + "orderManage/orderList?pageSize=2";
	//????????
	public static String SHOUYE_TUPIANLUBO=HTTP_PATH+"goods/rollPic?";
	//??????
	public static String GONGGAO_WANGYE=HTTP_PATH+"bulletin.html";
	//????help
	public static String SHOPING_HELP_WANGYE=HTTP_PATH+"help.html";

	public static final int ZHAOHUIMIMA_RESULT_CODE = 99;
	public static final String TYPE_GBK_CHARSET = "charset=GBK";
	public static final String VOLLEY_SHOUYEFRAGMENT_XINPINPARME_TAG="VOLLEY_SHOUYEFRAGMENT_XINPINPARME_TAG";
	public static final String VOLLEY_SHOUYEFRAGMENT_LUNBOPARME_TAG="VOLLEY_SHOUYEFRAGMENT_LUNBOPARME_TAG";
	public static final int FENLEI_SHIPEIQI_GENGXIN=0;
	public static final int FENLEI_SHIPEIQI_GENGXIN_UNSUCCESS=1;
	public static final int PANDUAN_LOGIN_CODE=200;
	public static final int FRAGMENT_PANDUAN_LOGIN_CODE=201;
}
