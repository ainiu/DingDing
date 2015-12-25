package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.XianShiEntity;
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
 * Created by Administrator on 2015/10/28
 */
public class XianshiModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public XianshiModel(Context context) {

        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(){
        Map<String, String> params = this.newParams();
        params.put("type", 4+"");
       // params.put("type", type+"");
       // params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.XIANSHI,params,consultListResponse);
           // DVolley.get(Consts.GOODSCATEGORY, params, consultListResponse);
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
                LogUtil.i("限时抢购请求数据", "callResult=" + callResult.toString());
            JSONObject contentObject = callResult.getContentObject();
            JSONArray contentObjectary = contentObject.getJSONArray("goodsList");
            LogUtil.i("分类数组长度", contentObjectary.length());
            if(contentObjectary!=null&&contentObjectary.length()!=0){
                List<XianShiEntity> list=new ArrayList<XianShiEntity>();
                    for (int i = 0; i < contentObject.length(); i++) {
                        JSONObject jsonobj = (JSONObject) contentObjectary.opt(i);
                        XianShiEntity  tejia=GsonTools.getPerson(jsonobj.toString(),XianShiEntity.class);
                        list.add(tejia);
                    }
                LogUtil.i("特价list", "list=" + list.size());
                returnBean.setObject(list);
            }
            returnBean.setType(DVolleyConstans.METHOD_XIANSHI);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
