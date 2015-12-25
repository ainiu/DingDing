package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.Dianpu;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28
 */
public class ShopModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public ShopModel(Context context) {
        super(context);
    }

    /**
     * 查询所有咨询信息
     */
    public void findConsultList() {
        // Map<String, String> params = this.newParams();
      /* params.put("goodsID", goodsID);
       params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        DVolley.post(Consts.SHOPLIST, null, consultListResponse);
        //DVolley.get(Consts.SHOPLIST, null, consultListResponse);
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
            ReturnBean returnBean = new ReturnBean();
            LogUtil.i("店铺列表", "callResult=" + callResult.toString());
            JSONArray contentObject = callResult.getContentArray();
            if (contentObject != null && contentObject.length() != 0) {
                List<Dianpu> list = new ArrayList<Dianpu>();
                for (int i = 0; i < contentObject.length(); i++) {
                    JSONObject jsonobj = (JSONObject) contentObject.opt(i);
                    Dianpu dianpu = GsonTools.getPerson(jsonobj.toString(), Dianpu.class);
                    list.add(dianpu);
                }
                // LogUtil.i("轮播数组的长度","list="+list.size());
                returnBean.setObject(list);
            }
            returnBean.setType(DVolleyConstans.SEARCH);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);
        }
    }
}
