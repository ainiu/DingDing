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
public class AdressdeleteModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public AdressdeleteModel(Context context) {

        super(context);
    }
    String parentId=0+"";

    /**查询所有咨询信息*/
    public void findConsultList(){
        Map<String, String> params = this.newParams();
        params.put("parentId", parentId);
       // params.put("type", type+"");
       // params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.GOODSCATEGORY,params,consultListResponse);
           // DVolley.get(Consts.GOODSCATEGORY, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    public void findConsultList(String userNumber, String token, String receiveId) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
         params.put("token", token);
         params.put("receiveId", receiveId);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.DELETERECEIVEADDRESS,params,consultListResponse);
        }catch (Exception e){
            LogUtil.i("删除请求错误,信息错误","");
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
                LogUtil.i("删除收货地址请求数据","callResult="+callResult.toString());
            returnBean.setString("删除成功");
            returnBean.setType(DVolleyConstans.METHOD_DELETE);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
