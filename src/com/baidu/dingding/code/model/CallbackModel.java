package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class CallbackModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public CallbackModel(Context context) {

        super(context);
    }

    public void findConsultList(String shopId, String userNumber, String concent, String token) {
        Map<String, String> params = this.newParams();
        params.put("goodsId", shopId);
         params.put("userNumber", userNumber);
         params.put("concent", concent);
        params.put("token",token);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVELEAVEMESSAGE,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String userNumber, String password, String passed) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("password", password);
        params.put("repassword", passed);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.REGIS,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String userName) {
        Map<String, String> params = this.newParams();
        params.put("senderContent", userName);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.ZHAOHUI,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {

            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();
                LogUtil.i("找回请求数据","callResult="+callResult.toString());
            returnBean.setType(DVolleyConstans.CALLBACKPASS);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
