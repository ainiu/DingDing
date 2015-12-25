package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.Shoping.OrderNoBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class CreateDingdanModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public CreateDingdanModel(Context context) {

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

    public void findConsultList(String userNumber, String token, String goodsId , String count, String goodsSpecificationContactStr,
                                String consigneeName, String mobile, String receiveddress, String postCode, String province,
                                String city, String area, String remark) throws UnsupportedEncodingException {
        Map<String, String> params = this.newParams();
        params.put("userNumber",userNumber);
        params.put("token",token);
        params.put("goodsId",goodsId);
        params.put("goodsCount",count);
        params.put("goodsSpecificationContactStr",goodsSpecificationContactStr);
        params.put("consigneeName",consigneeName);
        params.put("mobile",mobile);
        params.put("receiveddress",receiveddress);
        params.put("postCode",postCode);
        params.put("province",province);
        params.put("city",city);
        params.put("area",area);
        params.put("remark",remark);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.CREATE_DINDAN,params,consultListResponse);
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
            LogUtil.i("下单请求数据", "callResult=" + callResult.toString());
            JSONObject content = callResult.getContentObject();
            OrderNoBean orderNoBean = GsonTools.getPerson(content.toString(), OrderNoBean.class);
            returnBean.setObject(orderNoBean);
            returnBean.setType(DVolleyConstans.CREATE_DINDAN);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
