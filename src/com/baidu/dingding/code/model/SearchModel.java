package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.EachBaen;
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
public class SearchModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public SearchModel(Context context) {
        super(context);
    }


    public void findConsultList(String operationType, int currentPage, String query) {
        Map<String, String> params = this.newParams();
        params.put("operationType", operationType);
        params.put("currentPage", currentPage+"");
        params.put("searchCondition", query);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        DVolley.post(Consts.SOUYE_SOUSHUO, params, consultListResponse);
    }

    public void findConsultList(String shopId, int currentPage) {
        Map<String, String> params = this.newParams();
        params.put("shopId", shopId);
        params.put("currentPage", currentPage+"");
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        DVolley.post(Consts.GOODSLIST_SHOING, params, consultListResponse);
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
            ReturnBean returnBean = new ReturnBean();
            JSONArray contentArray = callResult.getContentArray();
            LogUtil.i("搜索","callResult="+callResult.toString());
            if (callResult!=null&&contentArray.length()!=0) {
                List<EachBaen> search_list=new ArrayList<EachBaen>();
                for(int i=0;i<contentArray.length();i++){
                    JSONObject contentobject = (JSONObject) contentArray.opt(i);
                    EachBaen eachBean = GsonTools.getPerson(contentobject.toString(), EachBaen.class);
                    search_list.add(eachBean);
                }
                returnBean.setObject(search_list);
        }
            returnBean.setType(DVolleyConstans.SEARCH);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
