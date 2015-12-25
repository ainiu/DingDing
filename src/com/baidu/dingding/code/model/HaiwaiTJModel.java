package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.HaiwaituijianBean;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28   海外店铺推荐
 */
public class HaiwaiTJModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public HaiwaiTJModel(Context context) {

        super(context);
    }

    /**
     * 查询所有咨询信息
     */
    public void findConsultList(String str) {
        Map<String, String> params = this.newParams();
        params.put("shopId", str);
        // params.put("type", type+"");
        // params.put("currentPage", currentPage+"");*/
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.HAIWAIDIANPU, params, consultListResponse);
            // DVolley.get(Consts.GOODSCATEGORY, params, consultListResponse);
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
            LogUtil.i("海外店铺请求数据", "callResult=" + callResult.toString());
            JSONArray contentObject = callResult.getContentArray();
            if (contentObject != null && contentObject.length() != 0) {
                List<HaiwaituijianBean> list = new ArrayList<HaiwaituijianBean>();
                for (int i = 0; i < contentObject.length(); i++) {
                    JSONObject jsonobj = (JSONObject) contentObject.opt(i);
                    HaiwaituijianBean haiwaituijianBean = GsonTools.getPerson(jsonobj.toString(), HaiwaituijianBean.class);
                    list.add(haiwaituijianBean);
                    LogUtil.i("海外店铺请求数据", "list=" + list.size());
                }
                returnBean.setObject(list);
                returnBean.setType(DVolleyConstans.HAIWAIXIANGQINGDIANPUTUIJIAN);
                volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
            }
        }
    }
}
