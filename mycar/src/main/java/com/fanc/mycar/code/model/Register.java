package com.fanc.mycar.code.model;

import android.content.Context;

import com.fanc.mycar.code.DCallResult;
import com.fanc.mycar.code.DResponseService;
import com.fanc.mycar.code.DVolley;
import com.fanc.mycar.code.DVolleyConstans;
import com.fanc.mycar.code.DVolleyModel;
import com.fanc.mycar.code.ReturnBean;
import com.fanc.mycar.util.LogUtil;
import com.fanc.mycar.util.MyCarHttp;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class Register extends DVolleyModel {

    private DResponseService consultListResponse;

    public Register(Context context) {
        super(context);
    }


    /**查询所有咨询信息*/
    public void findConsultList(String str){
        Map<String, String> params = this.newParams();
        params.put("mobile", str);
       /*  params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(MyCarHttp.VERIFY_PATH, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("请求出错了", "");
        }
    }

    public void findConsultList(String user_name, String user_pwd, String verifition_str, String token) {
        Map<String, String> params = this.newParams();
        params.put("mobile", user_name);
        params.put("type", user_pwd);
        params.put("currentPage", verifition_str);
        params.put("access_token", token);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(MyCarHttp.REGISTER_PATH, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("请求出错了", "");
        }
    }


    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }
        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();
            LogUtil.i("注册返回数据","callResult="+callResult);
        returnBean.setType(DVolleyConstans.METHOD_VERIFICATION);
        volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
