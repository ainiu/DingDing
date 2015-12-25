package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.HWXQEntity;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28   海外商品详情
 */
public class HaiwaiXQModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public HaiwaiXQModel(Context context) {

        super(context);
    }

    String parentId = 0 + "";

    /**
     * 查询所有咨询信息
     */
    public void findConsultList(String str) {
        Map<String, String> params = this.newParams();
        params.put("goodsId", str);
        // params.put("type", type+"");
        // params.put("currentPage", currentPage+"");*/
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.GOODSDETAIL, params, consultListResponse);
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

            LogUtil.i("海外详情请求数据", "callResult=" + callResult.toString());
            JSONObject contentObject = callResult.getContentObject();
            LogUtil.i("海外详情请求数据", "callResult=" + contentObject.toString());
            JSONObject arr_obj = contentObject.getJSONObject("productSpecification");
            // returnBean.setJsonObject(arr_obj);
            LogUtil.i("海外详情解析数据", "商品属性" + arr_obj.toString());
            ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            Iterator it = arr_obj.keys();
            try {
                while (it.hasNext()) {
                    String key = (String) it.next();
                    JSONArray value = (JSONArray) arr_obj.get(key);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    ArrayList arrayList=new ArrayList();
                    for(int i=0;i<value.length();i++){
                        String index = (String) value.opt(i);
                        arrayList.add(index);
                    }
                    map.put(key, arrayList);
                    list.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnBean.setMapList(list);
            LogUtil.i("海外商品详情解析数据", "list=" + list.size());
            String result = contentObject.toString();
            HWXQEntity hwxq = GsonTools.getPerson(result, HWXQEntity.class);
            LogUtil.i("Gson解析海外详情", hwxq.toString());
            returnBean.setObject(hwxq);
            returnBean.setType(DVolleyConstans.METHOD_HAIWAIXQ);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
