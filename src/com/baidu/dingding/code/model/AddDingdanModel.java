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
public class AddDingdanModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public AddDingdanModel(Context context) {

        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(String userNumber, String token, int orderStatus, int currentPage) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("orderStatus", orderStatus+"");
        params.put("currentPage", currentPage+"");

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.DIANDAN,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String userNumber, String token, String goodsId, String goodsName, String tgjg, int goodsCount, String freight, String string) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("goodsId", goodsId);
        params.put("goodsName", goodsName);
        params.put("goodsCount",goodsCount+"");
        params.put("goodsPrice",tgjg);
        params.put("freight",freight);
        params.put("goodsSpecificationContactStr",string);

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.ADDGOODSCAR,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

   /* public void findConsultList(String userNumber, String token,String goodsName,String goodsPrice,String mygoods, int goodsCount, String freight, String string) {

        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("goodsId", mygoods);
        params.put("goodsName", goodsName);
        params.put("goodsCount",goodsCount+"");
        params.put("goodsPrice",goodsPrice);
        params.put("freight",freight);
        params.put("goodsSpecificationContactStr",string);

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.ADDGOODSCAR,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }*/

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();
            LogUtil.i("加入购物车请求数据","callResult="+callResult.toString());
            String result = callResult.getContentString();
            returnBean.setString(result);
            returnBean.setType(DVolleyConstans.ADDGOODSCAR);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
