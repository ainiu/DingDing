package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.CollectionEntity;
import com.baidu.dingding.entity.Gerenxinxi;
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
public class MyShoucangjiaModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public MyShoucangjiaModel(Context context) {

        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(String userNumber) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", userNumber);
       /* params.put("token", token);
        params.put("orderStatus", orderStatus+"");
        params.put("currentPage", currentPage+"");*/

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.SHOUCHANGJIA,params,consultListResponse);
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
                LogUtil.i("个人收藏请求数据","callResult="+callResult);
            String reult = callResult.getResponse().toString();
            LogUtil.i("个人收藏数据","reult="+reult);
            JSONArray contentArray = callResult.getContentArray();
            List<CollectionEntity> list=new ArrayList<CollectionEntity>();
            try {
                for(int i=0;i<contentArray.length();i++){
                    JSONObject contentObject = (JSONObject) contentArray.opt(i);
                CollectionEntity collectionEntity = GsonTools.getPerson(contentObject.toString(), CollectionEntity.class);
                    list.add(collectionEntity);
                LogUtil.i("个人收藏", collectionEntity.toString());
                }
                returnBean.setObject(list);
            }catch (Exception e){
                e.printStackTrace();
            }
            returnBean.setType(DVolleyConstans.SHOUCHANGLIEBIAO);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
