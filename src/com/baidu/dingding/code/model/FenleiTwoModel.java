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
import com.baidu.dingding.until.ExceptionUtil;
import com.baidu.dingding.until.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class FenleiTwoModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public FenleiTwoModel(Context context) {

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
            DVolley.get(Consts.GOODSCATEGORY, params, consultListResponse);
        }catch (Exception e){
            LogUtil.i("查询所有信息错误","");
            ExceptionUtil.handleException(e);
        }
    }

    /**查询所有咨询信息*/
    public void findConsultList(String str){
        Map<String, String> params = this.newParams();
        params.put("parentId", str);
        if(consultListResponse==null){
            consultListResponse=new ConsultListResponse(this);
        }
        try {
            DVolley.post(Consts.GOODSCATEGORY, params, consultListResponse);
            //DVolley.get(Consts.GOODSCATEGORY, params, consultListResponse);
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
                LogUtil.i("分类请求数据","callResult="+callResult.toString());
            JSONArray contentObject=callResult.getContentArray();
            LogUtil.i("分类数组长度",contentObject.length());
            if(contentObject!=null&&contentObject.length()!=0){

                List<FenLei> list=new ArrayList<FenLei>();
                try {
                    for (int i = 0; i < contentObject.length(); i++) {
                        JSONObject jsonobj = (JSONObject) contentObject.opt(i);
                        FenLei fenlei = new FenLei();
                        fenlei.setParentId(jsonobj
                                .getString("parentId"));
                        fenlei.setLogPath(jsonobj.getString("logPath"));
                        fenlei.setName(jsonobj.getString("name"));
                        fenlei.setCategoryId(jsonobj.getString("categoryId"));
                        fenlei.setCtgCode(jsonobj.getString("ctgCode"));
                        list.add(fenlei);
                    }
                }catch (Exception e){
                    LogUtil.i("商品分类解析出错","");
                    ExceptionUtil.handleException(e);
                }
               LogUtil.i("商品分类list", "list=" + list.size());
                returnBean.setObject(list);
            }
            returnBean.setType(DVolleyConstans.METHOD_QUERYALL);
            volleyModel.onMessageResponse(returnBean,callResult.getResult(),callResult.getMessage(),null);
        }
    }
}