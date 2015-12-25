package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.AdDomain;
import com.baidu.dingding.entity.Gerenxinxi;
import com.baidu.dingding.entity.SearchSecurityEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class SearchSecurityModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public SearchSecurityModel(Context context) {
        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(){
       // Map<String, String> params = this.newParams();
      /* params.put("goodsID", goodsID);
       params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        DVolley.get(Consts.SHOUYE_TUPIANLUBO, null, consultListResponse);
    }

    public void findConsultList(String userNumber) {
         Map<String, String> params = this.newParams();
       params.put("userNumber", userNumber);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        DVolley.get(Consts.USER_SAFETYIN_SEARCH_TEFACE, params, consultListResponse);
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }
        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();

            JSONObject contentObject = callResult.getContentObject();
            if(contentObject!=null&&contentObject.length()!=0){
                try {
                    SearchSecurityEntity searchSecurityEntity = GsonTools.getPerson(contentObject.toString(), SearchSecurityEntity.class);
                    LogUtil.i("用户安全设置查询接口", searchSecurityEntity.toString());
                    returnBean.setObject(searchSecurityEntity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
