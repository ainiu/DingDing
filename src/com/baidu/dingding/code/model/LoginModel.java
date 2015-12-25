package com.baidu.dingding.code.model;

import android.content.Context;

import com.baidu.dingding.TApplication;
import com.baidu.dingding.code.DCallResult;
import com.baidu.dingding.code.DResponseService;
import com.baidu.dingding.code.DVolley;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.DVolleyModel;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.entity.User;
import com.baidu.dingding.until.Consts;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/28
 */
public class LoginModel extends DVolleyModel {

    private DResponseService consultListResponse;

    public LoginModel(Context context) {
        super(context);
    }


    /**
     * 查询所有咨询信息
     */
    public void findConsultList(String mobile, String password) {
        Map<String, String> params = this.newParams();
        params.put("userNumber", mobile);
        params.put("password", password);
        if (consultListResponse == null) {
            consultListResponse = new ConsultListResponse(this);
        }
        DVolley.post(Consts.LOGIN, params, consultListResponse);
    }

    private class ConsultListResponse extends DResponseService {
        public ConsultListResponse(DVolleyModel volleyModel) {
            super(volleyModel);
        }

        @Override
        protected void myOnResponse(DCallResult callResult) throws Exception {
            ReturnBean returnBean = new ReturnBean();
            JSONObject content_string = callResult.getContentObject();
            LogUtil.i("用户登录的数据", "callResult=" + callResult.toString());
            if (callResult != null && content_string.length() != 0) {
                User user = GsonTools.getPerson(content_string.toString(), User.class);
                SharedPreferencesUtils.clear(TApplication.getContext());
                SharedPreferencesUtils.put(TApplication.getContext(), "userNumber", user.getUsrNumber());
                SharedPreferencesUtils.put(TApplication.getContext(), "token", user.getToken());
                returnBean.setObject(user);
                LogUtil.i("用户登录的数据", user.toString());
            }
            returnBean.setType(DVolleyConstans.METHOD_USER_LOGIN);
            volleyModel.onMessageResponse(returnBean, callResult.getResult(), callResult.getMessage(), null);

        }
    }

}
