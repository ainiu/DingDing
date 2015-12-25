package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.FenLei;
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
public class YcdModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public YcdModel(Context context) {
        super(context);
    }

    /**查询所有咨询信息*/
    public void findConsultList(){
        /*Map<String, String> params = this.newParams();
       params.put("ycdId", ycdId);
         params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/

        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        DVolley.get(Consts.SHOUYE_TUPIANLUBO, null, consultListResponse);
    }


    /**查询所有咨询信息*/
    public void findConsultList(String ycdId){
        Map<String, String> params = this.newParams();
        params.put("ycdId", ycdId);
        params.put("parentId",""+0);
       /*  params.put("type", type+"");
        params.put("currentPage", currentPage+"");*/
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.HAIWAIDIZHI, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("请求出错了","");
        }
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }
        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
                      ReturnBean returnBean=new ReturnBean();

            JSONArray contentObject=callResult.getContentArray();
            LogUtil.i("海外原产地","contentObject="+contentObject.toString());
            if(contentObject!=null&&contentObject.length()!=0){
                List<FenLei> list=new ArrayList<FenLei>();
                for(int i=0;i<contentObject.length();i++){
                    JSONObject jsonobj = (JSONObject)contentObject.opt(i);
                    FenLei fenlei=GsonTools.getPerson(jsonobj.toString(),FenLei.class);
                    list.add(fenlei);
                }
                returnBean.setObject(list);
            }
            returnBean.setType(DVolleyConstans.METHOD_GET);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}
