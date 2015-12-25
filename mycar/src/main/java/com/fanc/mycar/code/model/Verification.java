package com.fanc.mycar.code.model;

import android.content.Context;


import com.fanc.mycar.code.DCallResult;
import com.fanc.mycar.code.DResponseService;
import com.fanc.mycar.code.DVolley;
import com.fanc.mycar.code.DVolleyConstans;
import com.fanc.mycar.code.DVolleyModel;
import com.fanc.mycar.code.ReturnBean;
import com.fanc.mycar.util.LogUtil;
import com.fanc.mycar.util.MyCarHttp;

import org.json.JSONObject;


import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class Verification extends DVolleyModel {

    private DResponseService consultListResponse;

    public Verification(Context context) {
        super(context);
    }


    /**查询所有咨询信息*/
    public void findConsultList(String str){
        Map<String, String> params = this.newParams();
        params.put("mobile", str);
       /*  params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(MyCarHttp.VERIFY_PATH, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("请求出错了", "");
        }
    }



    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }
        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();

            JSONObject contentObject = callResult.getContentObject();
            //获取token
            String token = contentObject.getString("access_token");
            LogUtil.i("验证码access_token","access_token="+token);
            returnBean.setObject(token);

        returnBean.setType(DVolleyConstans.METHOD_VERIFICATION);
        volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
