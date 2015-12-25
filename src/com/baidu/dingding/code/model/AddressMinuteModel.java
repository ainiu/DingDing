package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.AddressEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class AddressMinuteModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public AddressMinuteModel(Context context) {

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



    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
            ReturnBean returnBean = new ReturnBean();
            LogUtil.i("收货地址详细信息数据", "callResult=" + callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("收货地址详细信息数据", "reult=" + reult);
            JSONObject jsonObject = callResult.getContentObject();
            try {
                    AddressEntity addressEntity = GsonTools.getPerson(jsonObject.toString(), AddressEntity.class);
                    LogUtil.i("收货地址详细信息", addressEntity.toString());
                returnBean.setObject(addressEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnBean.setType(DVolleyConstans.SEARCHRECEIVEADDRESS);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
