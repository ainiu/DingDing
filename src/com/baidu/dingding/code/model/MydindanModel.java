package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.DingdanEntity;
import com.baidu.dingding.entity.FenLei;
import com.baidu.dingding.entity.HWXQEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class MydindanModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public MydindanModel(Context context) {

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

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();
                LogUtil.i("订单信息请求数据","callResult="+callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("订单信息请求数据","reult="+reult);
            JSONArray contentArray = callResult.getContentArray();
            LogUtil.i("订单信息请求数据","contentArray="+contentArray.length()+"contentArray="+contentArray.toString());
            try {
                List<DingdanEntity> list = new ArrayList<DingdanEntity>();
                for (int i = 0; i < contentArray.length(); i++) {
                    JSONObject jsonobj = (JSONObject) contentArray.opt(i);
                    DingdanEntity dingdanEntity = GsonTools.getPerson(jsonobj.toString(), DingdanEntity.class);
                    LogUtil.i("订单信息" + i, dingdanEntity.toString());
                    list.add(dingdanEntity);
                }
                LogUtil.i("订单信息", list.toArray());
                returnBean.setObject(list);
            }catch (Exception e){
                e.printStackTrace();
            }
            returnBean.setType(DVolleyConstans.METHOD_GEREN_DINGDAN);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
