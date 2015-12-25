package com.baidu.dingding.code;


import java.util.Properties;

public class DVolleyConstans {
    public static final int RESULT_OK = 0;//查询结果 返回 成功
    public static final int RESULT_FAIL = 1;//查询结果 返回 失败
    public static final int RESULT_NOT_NEWWORK = 2;//无网络

    //方法调用
    public static int METHOD_QUERYALL = 1;
    public static int METHOD_ADD = 2;
    public static int METHOD_DELETE = 3;
    public static int METHOD_DELETE_MORE = 4;
    public static int METHOD_MODIFY = 5;
    public static int METHOD_GET = 6;
    public static int METHOD_TEJIA = 7;
    public static int METHOD_NEW = 8;
    public static int METHOD_HAIWAIXQ = 10;
    public static int METHOD_XIANSHI = 9;
    /** user*/
    public static int METHOD_USER_LOGIN=11;
    /**订单*/
    public static int METHOD_GEREN_DINGDAN=12;
    /**代付款*/
    public static int METHOD_GEREN_DAIFUKUAN=13;
    /**已付款*/
    public static int METHOD_GEREN_YIFUKUAN=14;
    /**已发货 */
    public static int METHOD_GEREN_YIFAHUO=15;
    /**已到货 */
    public static int METHOD_GEREN_YIDAOHUO=16;
    /**个人信息*/
    public static int GERENXINXI=17;
    /**个人信息保存*/
    public static int GERENXINXIBAOCUN=18;
    /**收藏夹列表*/
    public static int SHOUCHANGLIEBIAO=19;
    /**收货地址*/
    public static int SHOUHUOADDRESS=20;
    /**收货地址详细*/
    public static int SEARCHRECEIVEADDRESS=21;
    /**查询*/
    public static  int SEARCH=22;
    /**海外留言板*/
    public static int HAIWAILIUYANBAN=23;
    /**海外店铺推荐*/
    public static int HAIWAIXIANGQINGDIANPUTUIJIAN=24;
    /**海外收藏**/
    public static int HAIWAISHOUCANG=25;
    /**注册*/
    public static  int REGIS=26;
    /**找回密码*/
    public static  int CALLBACKPASS=27;
    /**加入购物车*/
    public static  int ADDGOODSCAR=28;
    /**查询购物车*/
    public static  int SHOPPINGCARLIST=29;
    /**删除商品*/
    public static int DELETESHOPPINGCAR=30;
    /**修改购物车数量*/
    public static int CHANGEGOODSCOUNT=31;
    /**创建订单*/
    public static int CREATE_DINDAN=32;
    /**订单支付*/
    public static  int TOPAY=33;
    /**保存收货地址*/
    public static  int SAVERECEIVEADDRESS=34;
    private static Properties pso = new Properties();

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
