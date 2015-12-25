package com.baidu.dingding.entity.Shoping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class OrderNoBean implements Serializable{

    /**
     * message : 订单创建成功！
     * orderNo : ["M151223193449610898"]
     */

    private String message;
    private ArrayList<String> orderNo;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOrderNo(ArrayList<String> orderNo) {
        this.orderNo = orderNo;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getOrderNo() {
        return orderNo;
    }
}
