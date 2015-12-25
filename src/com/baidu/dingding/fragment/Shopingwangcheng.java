package com.baidu.dingding.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.baidu.dingding.R;
import com.baidu.dingding.adapter.ShopingCarWangchengAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.DeleteShopingModel;
import com.baidu.dingding.code.model.GouwuchexiangqingModel;
import com.baidu.dingding.code.model.HwshoucangModel;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class Shopingwangcheng extends Fragment implements DResponseListener,ShopingCarWangchengAdapter.Listener{

    private View view;
    private GouwuchexiangqingModel gouwuchexiangqingModel;
    private DeleteShopingModel deleteShopingModel;
    private HwshoucangModel hwshoucangModel;
    private ShopingCarWangchengAdapter shopingCarWangcheng;
    private ArrayList<GouwucheBean> list = new ArrayList<GouwucheBean>();
    private ListView listView;
    private Dialog dialog;
    private CheckBox allCheckBox;
    private Button delete, yidaoshouchangjia;
    private boolean index=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.shopingwangcheng, null);
        initData();
        initModel();
        initView();
        initLener();
        return view;
    }



    public String listToString (List list,char separator){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private void initLener() {
        shopingCarWangcheng.addListener(this);
        //单选
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = !index;
                GouwucheBean listshoping = list.get(position);
                listshoping.setId(index);
                shopingCarWangcheng.notifyDataSetChanged();
                //update();
            }
        });
        //全选
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopingCarWangcheng.selectAll(allCheckBox.isChecked());
            }
        });
        //删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shopingCarWangcheng.getSelectCartIDs().isEmpty()) {
                    deleteShopingModel.findConsultList(userNumber, token, listToString(shopingCarWangcheng.getSelectCartIDs(), ','));
                    dialog.show();
                }else{
                    Toasttool.MyToast(getActivity(),getResources().getString(R.string.shopingcar_wangcheng_deleteshoping));
                }
            }
        });

        //移到收藏夹
        yidaoshouchangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shopingCarWangcheng.getSelectCartIDs().isEmpty()) {
                    deleteShopingModel.findConsultList(userNumber, token, listToString(shopingCarWangcheng.getSelectCartIDs(), ','));
                    dialog.show();
                }else{
                    Toasttool.MyToast(getActivity(),getResources().getString(R.string.shopingcar_wangcheng_shouchang));
                }
            }
        });

    }

    private void initView() {
        yidaoshouchangjia = (Button) view.findViewById(R.id.yidaoshouchang);
        delete = (Button) view.findViewById(R.id.delete);
        allCheckBox = (CheckBox) view.findViewById(R.id.selectAll);
        allCheckBox.setChecked(false);                                   //默认不选中
        dialog = MyProgressDialog.createLoadingDialog(getActivity(), "官人稍等...");
        shopingCarWangcheng = new ShopingCarWangchengAdapter(getActivity(), list);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(shopingCarWangcheng);
    }




    private void initModel() {
        gouwuchexiangqingModel = new GouwuchexiangqingModel(getActivity());
        gouwuchexiangqingModel.addResponseListener(this);
        //删除
        deleteShopingModel = new DeleteShopingModel(getActivity());
        deleteShopingModel.addResponseListener(this);
        //添加到收藏夹
        hwshoucangModel = new HwshoucangModel(getActivity());
        hwshoucangModel.addResponseListener(this);
    }

    String userNumber, token;

    private void initData() {
        token = (String) SharedPreferencesUtils.get(getActivity(), "token", "");
        userNumber = (String) SharedPreferencesUtils.get(getActivity(), "userNumber", "");
    }


    @Override
    public void onResume() {
        super.onResume();
        gouwuchexiangqingModel.findConsultList(userNumber);
        dialog.show();
    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        dialog.dismiss();
        LogUtil.i("购物车请求回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        if (result == DVolleyConstans.RESULT_FAIL) {
            Toasttool.MyToastLong(getActivity(), message);
            return;
        }
        //错误处理
        if (error != null) {
            Toasttool.MyToast(getActivity(), error.getMessage());
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.DELETESHOPPINGCAR) {
                Toasttool.MyToast(getActivity(), bean.getString());
                shopingCarWangcheng.removeCartByIDs(shopingCarWangcheng.getSelectCartIDs());
                shopingCarWangcheng.notifyDataSetChanged();
            }
            if (bean.getType() == DVolleyConstans.HAIWAISHOUCANG) {
                Toasttool.MyToast(getActivity(), bean.getString());
            }
            if (bean.getType() == DVolleyConstans.SHOPPINGCARLIST) {
                try {
                   // list.clear();
                    list = (ArrayList<GouwucheBean>) bean.getObject();
                    shopingCarWangcheng.setBeanList(list);
                    shopingCarWangcheng.notifyDataSetChanged();
                    LogUtil.i("2通知适配器更新", "2ok");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void modifyCartNumber(GouwucheBean shoppingCart, String number) {

    }

    @Override
    public void changeCart(GouwucheBean shoppingCart) {

    }

    @Override
    public void deleteCartNumber(int positon) {

    }
}
