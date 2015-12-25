package com.fanc.mycar.util;

/**
 * Created by Administrator on 2015/11/9. 打车接口
 */
public class MyCarHttp {
    //接口测试地址公共前缀
    public static String HTTP_PATH = "http://192.168.1.116/pinche/index.php";
    //登录接口
    public static String LOGIN_PATH=HTTP_PATH+"/User/Login/login";
    //获取验证码
    public static String VERIFY_PATH=HTTP_PATH+"/User/Login/verify";
    //注册
    public static String REGISTER_PATH=HTTP_PATH+"/User/Login/register";
    //重置密码
    public static String FINDPWD_PATH=HTTP_PATH+"/User/Login/findPassword";
    //修改密码
    public static String RECOMPOSEPWD_PATH=HTTP_PATH+"/User/User/modifyPassword";
    //修改用户信息
    public static String MODIFYUSER_PATH=HTTP_PATH+"/User/User/modifyUser";
    //修改头像
    public static String MODIFYICON_PATH=HTTP_PATH+"/User/User/modifyIcon";
}
