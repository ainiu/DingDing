package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.Gerenxinxi;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.Toasttool;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class MyGerenxinxibaocuunModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public MyGerenxinxibaocuunModel(Context context) {

        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(String userNumber) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
       /* params.put("token", token);
        params.put("orderStatus", orderStatus+"");
        params.put("currentPage", currentPage+"");*/

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVEPERSIONINFO,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String userNumber, String token, String opertionType, String save_sex, String name, String truename, String shenfenzhenghaoma) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("opertionType", opertionType);
        params.put("sex", save_sex);
        params.put("userName",name);
        params.put("realityName",truename);
        params.put("idCard",shenfenzhenghaoma);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVEPERSIONINFO,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }

    }
    public void findConsultList(String token, String userNumber, String goodsCollectionId) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("goodsCollectionId", goodsCollectionId);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.DELETESHOUCHANGJIA,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String jiumima, String newpass, String querenpassword, String userNumber, String token) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("oldPassword", jiumima);
        params.put("newPassword", newpass);
        params.put("rePassword", querenpassword);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.CHECK_PASSWORD,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String wenti, String daan, String userNumber,String token) {
        Map<String, String> params = this.newParams();
        params.put("token", token);
        params.put("passwordQuestion", wenti);
        params.put("passwordAnswer", daan);
        params.put("userNumber", userNumber);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.USER_SAFETYINTEFACE,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String token, String userNumber, String phone, String adress, String qq, String youbian, int opertionType) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("opertionType", opertionType+"");
        params.put("address", adress);
        params.put("mobile",phone);
        params.put("qq",qq);
        params.put("Post",youbian);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVEPERSIONINFO,params,consultListResponse);
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
                LogUtil.i("个人信息数据","callResult="+callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("个人信息数据","reult="+reult);

            returnBean.setType(DVolleyConstans.GERENXINXIBAOCUN);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
