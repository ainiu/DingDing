package com.baidu.dingding.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.dingding.MainActivity;
import com.baidu.dingding.R;
import com.baidu.dingding.adapter.ShopingSelectAdapter;
import com.baidu.dingding.code.DResponseListener;
import com.baidu.dingding.code.DVolleyConstans;
import com.baidu.dingding.code.ReturnBean;
import com.baidu.dingding.code.model.ChangShopingModel;
import com.baidu.dingding.code.model.DeleteShopingModel;
import com.baidu.dingding.code.model.GouwuchexiangqingModel;
import com.baidu.dingding.entity.GouwucheBean;
import com.baidu.dingding.entity.Shoping.GouwubeanToQueren;
import com.baidu.dingding.until.GsonTools;
import com.baidu.dingding.until.LogUtil;
import com.baidu.dingding.until.SharedPreferencesUtils;
import com.baidu.dingding.until.Toasttool;
import com.baidu.dingding.view.MyView.dialog.MyProgressDialog;
import com.baidu.dingding.view.QueRenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/15. 全选商品
 */
public class ShopingBianji extends Fragment implements DResponseListener, ShopingSelectAdapter.Listener {

    private View view;
    private GouwuchexiangqingModel gouwuchexiangqingModel;
    private ChangShopingModel changShopingModel;
    private DeleteShopingModel deleteShopingModel;
    private ArrayList<GouwucheBean> list = new ArrayList<GouwucheBean>();
    private ShopingSelectAdapter shopingSelectAdapter;
    private ListView listView;
    private Dialog dialog;
    private CheckBox allCheckBox;
    private TextView tv_sum;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.pullview, null);
        initData();
        initModel();
        initView();
        initLener();
        return view;
    }


    private void islogindialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void isTokennull() {
        if (TextUtils.isEmpty(token)) {
            islogindialog("你还没有登录");
        }
    }
    ArrayList<GouwubeanToQueren> gouwubeanToQuerenArrayList = new ArrayList<GouwubeanToQueren>();

    private void initLener() {
        shopingSelectAdapter.addListener(this);
        //结算
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isTokennull();
                try {
                    for (int i = 0; i < shopingSelectAdapter.getCount(); i++) {
                        GouwubeanToQueren gouwubeanToQueren = new GouwubeanToQueren();
                        GouwucheBean shoping = shopingSelectAdapter.getBeanList().get(i);
                        if(shoping.getId()) {
                            gouwubeanToQueren.setPicPath(shoping.getPicPath());
                            gouwubeanToQueren.setShopName(shoping.getShopName());
                            gouwubeanToQueren.setSort(shoping.getSort());
                            gouwubeanToQueren.setSellerNumber(shoping.getSellerNumber());
                            gouwubeanToQueren.setPrice(shoping.getPrice());
                            Map<String, String> goodsSpecificationContactStr = (Map<String, String>) shoping.getGoodsSpecificationContactStr();
                            if(!goodsSpecificationContactStr.isEmpty()){
                                String goodsSpecificationContactString = listToString(GsonTools.getMapList(goodsSpecificationContactStr), ',');
                                gouwubeanToQueren.setGoodsSpecificationContactStr(goodsSpecificationContactString);
                            }
                            gouwubeanToQueren.setShoppingCarId(shoping.getShoppingCarId());
                            gouwubeanToQueren.setShopId(shoping.getShopId());
                            gouwubeanToQueren.setFreight(shoping.getFreight());
                            gouwubeanToQueren.setGoodsCount(shoping.getGoodsCount());
                            gouwubeanToQueren.setGoodsName(shoping.getGoodsName());
                            gouwubeanToQueren.setGoodsId(shoping.getGoodsId());
                            gouwubeanToQuerenArrayList.add(gouwubeanToQueren);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (gouwubeanToQuerenArrayList.size() == 0) {
                    Toasttool.MyToast(getContext(), "请选择你要购买的商品");
                    return;
                }

                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("gouwuche", gouwubeanToQuerenArrayList);
                intent.setClass(getActivity(), QueRenActivity.class);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = !index;
                GouwucheBean listshoping = list.get(position);
                listshoping.setId(index);
                shopingSelectAdapter.notifyDataSetChanged();
                update();

                // changeCart(list.get(position));
            }
        });

        //全选
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopingSelectAdapter.selectAll(allCheckBox.isChecked());
                update();
            }
        });
    }

    boolean index = false;

    private void update() {
        tv_sum.setText(getActivity().getResources().getString(R.string.shoppingcart_text_order_total) + getActivity().getResources().getString(R.string.shoppingcart_renmingbin)+ shopingSelectAdapter.totalPrice());
        button.setText(getActivity().getResources().getString(R.string.shoppingcart_button_accounts) + "(" + shopingSelectAdapter.getSelectShoopingNumber() + ")");
    }

    private void initView() {
        button = (Button) view.findViewById(R.id.shopping_fragment_button_02);
        tv_sum = (TextView) view.findViewById(R.id.sum);
        allCheckBox = (CheckBox) view.findViewById(R.id.selectAll);
        allCheckBox.setChecked(false);                                   //默认不选中
        dialog = MyProgressDialog.createLoadingDialog(getActivity(), "加载中...");
        listView = (ListView) view.findViewById(R.id.list);
        shopingSelectAdapter = new ShopingSelectAdapter(getActivity(), list);
        listView.setAdapter(shopingSelectAdapter);
    }


    private void initModel() {
        //查询购物车
        gouwuchexiangqingModel = new GouwuchexiangqingModel(getActivity());
        gouwuchexiangqingModel.addResponseListener(this);
        //删除
        deleteShopingModel = new DeleteShopingModel(getActivity());
        gouwuchexiangqingModel.addResponseListener(this);
        //修改
        changShopingModel = new ChangShopingModel(getActivity());
        changShopingModel.addResponseListener(this);
    }

    String userNumber="", token="";

    private void initData() {
        token = (String) SharedPreferencesUtils.get(getActivity(), "token", "");
        isTokennull();
        userNumber = (String) SharedPreferencesUtils.get(getActivity(), "userNumber", "");
    }


    @Override
    public void onResume() {
        super.onResume();
        gouwubeanToQuerenArrayList.clear();
        gouwuchexiangqingModel.findConsultList(userNumber);
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        list.clear();
        tv_sum.setText(getActivity().getResources().getString(R.string.shoppingcart_text_order_total) + getActivity().getResources().getString(R.string.shoppingcart_renmingbin));
        button.setText(getActivity().getResources().getString(R.string.shoppingcart_button_accounts) + "(" + ")");

    }

    @Override
    public void onMessageResponse(ReturnBean bean, int result, String message, Throwable error) {
        dialog.dismiss();
        LogUtil.i("购物车请求回调", "bean=" + bean.getObject() + "result=" + result + "mesage=" + message);
        //错误处理
        if (error != null) {
            Toasttool.MyToast(getActivity(), error.getMessage());
            // shopingSelectAdapter.notifyDataSetChanged();
            return;
        }
        if (result == DVolleyConstans.RESULT_OK) {
            if (bean.getType() == DVolleyConstans.CHANGEGOODSCOUNT) {
                Toasttool.MyToast(getActivity(), bean.getString());
                return;
            }
            if (bean.getType() == DVolleyConstans.DELETESHOPPINGCAR) {
                Toasttool.MyToast(getActivity(), bean.getString());
                return;
            }
            if (bean.getType() == DVolleyConstans.SHOPPINGCARLIST) {
                try {
                    list = (ArrayList<GouwucheBean>) bean.getObject();
                    shopingSelectAdapter.setBeanList(list);
                    shopingSelectAdapter.notifyDataSetChanged();
                    LogUtil.i("通知适配器更新", "ok");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void modifyCartNumber(GouwucheBean shoppingCart, String num) {
        if (!num.equals("-1")) {
            changShopingModel.findConsultList(userNumber, shoppingCart.getShoppingCarId(), num);
            dialog.show();
        }
    }

    @Override
    public void changeCart(GouwucheBean shoppingCart) {
        update();
        allCheckBox.setChecked(shopingSelectAdapter.isAllSelect());
        LogUtil.i("接受返回值", "shoppingCart=" + shoppingCart.toString());
    }

    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Override
    public void deleteCartNumber(int positon) {
        String id = list.get(positon).getShoppingCarId();
        deleteShopingModel.findConsultList(userNumber, token, list.get(positon).getShoppingCarId());
        shopingSelectAdapter.removeCartByID(id);

        /*GouwucheBean cart = shopingSelectAdapter.getItem(positon);
        List<String> lists = new ArrayList<String>();
        lists.add(cart.getShoppingCarId());
        shopingSelectAdapter.removeCartByID(cart.getShoppingCarId());*/
        ShopingBianji.this.update();
    }


}
