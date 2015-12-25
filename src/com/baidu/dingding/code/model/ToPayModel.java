package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.ToPayBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class ToPayModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public ToPayModel(Context context) {

        super(context);
    }

    /**
     * 查询所有咨询信息
     */

    public void findConsultList(String userNumber, String token, String ordernolist) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        params.put("token", token);
        params.put("orderNo", ordernolist);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.TOPAY, params, consultListResponse);
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
            LogUtil.i("订单支付数据", "callResult=" + callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("订单支付数据", "reult=" + reult);
            ToPayBean toPayBean = GsonTools.getPerson(callResult.getContentString(), ToPayBean.class);
            returnBean.setObject(toPayBean);
            returnBean.setType(DVolleyConstans.TOPAY);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
