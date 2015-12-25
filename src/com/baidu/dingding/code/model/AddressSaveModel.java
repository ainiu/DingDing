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
public class AddressSaveModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public AddressSaveModel(Context context) {

        super(context);
    }

    /**
     * 查询所有咨询信息
     */
    public void findConsultList(String userNumber) {
        Map<String, String> params = this.newParams();
        params.put("receiveId", userNumber);
       /* params.put("token", token);
        params.put("orderStatus", orderStatus+"");
        params.put("currentPage", currentPage+"");*/

        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SEARCHRECEIVEADDRESS, params, consultListResponse);
        } catch (Exception e) {
            LogUtil.i("查询所有信息错误", "");
            ExceptionUtil.handleException(e);
        }
    }



    public void findConsultList(String token, String userNumber, String shouhuorenname, String shouhuorendianhua, String shouhuorenmoble, String shouhuorenyoubian, String receiveAddress, String xiugaiquyuid, String receiveId, String shengshiqu,String istrue) {
        Map<String, String> params = this.newParams();
        params.put("token", token);
        params.put("userNumber", userNumber);
        params.put("receiveAddress",receiveAddress);
        params.put("receiveName", shouhuorenname);
        params.put("receiveTel", shouhuorendianhua);
        params.put("receiveMobile",shouhuorenmoble);
        params.put("receivePost", shouhuorenyoubian);
        params.put("regionId", xiugaiquyuid);
        params.put("isDefault", istrue);
        params.put("receiveId", receiveId);
        params.put("province_city_area",shengshiqu);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVERECEIVEADDRESS, params, consultListResponse);
        } catch (Exception e) {
            LogUtil.i("查询所有信息错误", "");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String token, String userNumber, String receiveName, String receiveTel, String receiveMobile, String receivePost, String receiveAddress, String xiugaiquyuid, String istrue, String province_city_area) {
        Map<String, String> params = this.newParams();
        params.put("token", token);
        params.put("userNumber", userNumber);
        params.put("receiveAddress",receiveAddress);
        params.put("receiveName", receiveName);
        params.put("receiveTel", receiveTel);
        params.put("receiveMobile",receiveMobile);
        params.put("receivePost", receivePost);
        params.put("regionId", xiugaiquyuid);
        params.put("isDefault", istrue);
        params.put("province_city_area",province_city_area);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SAVERECEIVEADDRESS, params, consultListResponse);
        } catch (Exception e) {
            LogUtil.i("查询所有信息错误", "");
            ExceptionUtil.handleException(e);
        }
    }


    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
            ReturnBean returnBean = new ReturnBean();
            LogUtil.i("收货地址详保存数据", "callResult=" + callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("收货地址保存数据", "reult=" + reult);
            returnBean.setString(callResult.getContentString());
            returnBean.setType(DVolleyConstans.SAVERECEIVEADDRESS);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
