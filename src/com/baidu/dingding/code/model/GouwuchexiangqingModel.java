package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class GouwuchexiangqingModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public GouwuchexiangqingModel(Context context) {

        super(context);
    }


    public void findConsultList(String userNumber) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SHOPPINGCARLIST, params, consultListResponse);
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
            String result = callResult.getContentString();
            returnBean.setString(result);
            LogUtil.i("请求购物车数据", "callResult=" + callResult);
            JSONArray jsonarry = callResult.getContentArray();
            ArrayList<GouwucheBean> list=new ArrayList<GouwucheBean>();
            if(jsonarry.length()>0){
                for(int i=0;i<jsonarry.length();i++){
                    JSONObject content = (JSONObject) jsonarry.opt(i);
                    GouwucheBean gouwucheBean= GsonTools.getPerson(content.toString(),GouwucheBean.class);
                    list.add(gouwucheBean);
                }
            }
            returnBean.setObject(list);
            returnBean.setType(DVolleyConstans.SHOPPINGCARLIST);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
