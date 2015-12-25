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
public class DeleteShopingModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public DeleteShopingModel(Context context) {

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
    /**删除商品*/
    public void findConsultList(String userNumber, String token, String  shoppingCarId) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("shoppingCarId", shoppingCarId);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.DELETESHOPPINGCAR,params,consultListResponse);
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
            LogUtil.i("删除商品数据数据","callResult="+callResult.toString());
            returnBean.setString("删除成功");
            returnBean.setType(DVolleyConstans.DELETESHOPPINGCAR);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
