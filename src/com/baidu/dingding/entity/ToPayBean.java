package com.baidu.dingding.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/24.
 */
public class ToPayBean implements Parcelable{


    /**
     * extra_return_param :
     * merchant : 4000000012
     * sign_type : MD5
     * product_name :
     * notify_url : http://www.dindin.com/offlineNotify.go
     * product_desc :
     * orders_info : [{"mer_id":"7777778894","order_monery":"0.01","goods_name":"友高 hogl 单鞋（女）","order_id":"M150930175549739674"}]
     * order_no : M150930175549739674P
     * oreder_amount : .01
     * sign : d2131c63f8b5ce2ea683b874f27009f6
     * product_num :
     * interface_version : V3.0
     * order_time : 2015-10-08 17:30:22
     * product_code :
     * redo_flag : 1
     */

    private String extra_return_param;
    private String merchant;
    private String sign_type;
    private String product_name;
    private String notify_url;
    private String product_desc;
    private String order_no;
    private String oreder_amount;
    private String sign;
    private String product_num;
    private String interface_version;
    private String order_time;
    private String product_code;
    private ArrayList<Orders_Info> orders_info;
    private String redo_flag;
    private String key;

    public ToPayBean(String extra_return_param, String merchant, String sign_type, String product_name, String notify_url, String product_desc, String order_no, String oreder_amount, String sign, String product_num, String interface_version, String order_time, String product_code, ArrayList<Orders_Info> orders_info, String redo_flag, String key) {
        this.extra_return_param = extra_return_param;
        this.merchant = merchant;
        this.sign_type = sign_type;
        this.product_name = product_name;
        this.notify_url = notify_url;
        this.product_desc = product_desc;
        this.order_no = order_no;
        this.oreder_amount = oreder_amount;
        this.sign = sign;
        this.product_num = product_num;
        this.interface_version = interface_version;
        this.order_time = order_time;
        this.product_code = product_code;
        this.orders_info = orders_info;
        this.redo_flag = redo_flag;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ToPayBean() {

    }

    public ArrayList<Orders_Info> getOrders_info() {
        return orders_info;
    }

    public void setOrders_info(ArrayList<Orders_Info> orders_info) {
        this.orders_info = orders_info;
    }

    public static final Creator<ToPayBean> CREATOR = new Creator<ToPayBean>() {
        @Override
        public ToPayBean createFromParcel(Parcel in) {
            return new ToPayBean(in);
        }

        @Override
        public ToPayBean[] newArray(int size) {
            return new ToPayBean[size];
        }
    };

    protected ToPayBean(Parcel in) {
        extra_return_param = in.readString();
        merchant = in.readString();
        sign_type = in.readString();
        product_name = in.readString();
        notify_url = in.readString();
        product_desc = in.readString();
        order_no = in.readString();
        oreder_amount = in.readString();
        sign = in.readString();
        product_num = in.readString();
        interface_version = in.readString();
        order_time = in.readString();
        product_code = in.readString();
        orders_info = in.createTypedArrayList(Orders_Info.CREATOR);
        redo_flag = in.readString();
        key=in.readString();
    }

    @Override
    public String toString() {
        return "ToPayBean{" +
                "extra_return_param='" + extra_return_param + '\'' +
                ", merchant='" + merchant + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", product_name='" + product_name + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", product_desc='" + product_desc + '\'' +
                ", order_no='" + order_no + '\'' +
                ", oreder_amount='" + oreder_amount + '\'' +
                ", sign='" + sign + '\'' +
                ", product_num='" + product_num + '\'' +
                ", interface_version='" + interface_version + '\'' +
                ", order_time='" + order_time + '\'' +
                ", product_code='" + product_code + '\'' +
                ", redo_flag='" + redo_flag + '\'' +
                ", orders_info=" + orders_info +
                '}';
    }

    public void setExtra_return_param(String extra_return_param) {
        this.extra_return_param = extra_return_param;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setOreder_amount(String oreder_amount) {
        this.oreder_amount = oreder_amount;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setProduct_num(String product_num) {
        this.product_num = product_num;
    }

    public void setInterface_version(String interface_version) {
        this.interface_version = interface_version;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setRedo_flag(String redo_flag) {
        this.redo_flag = redo_flag;
    }



    public String getExtra_return_param() {
        return extra_return_param;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getSign_type() {
        return sign_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getOreder_amount() {
        return oreder_amount;
    }

    public String getSign() {
        return sign;
    }

    public String getProduct_num() {
        return product_num;
    }

    public String getInterface_version() {
        return interface_version;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getRedo_flag() {
        return redo_flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(extra_return_param);
        dest.writeString(merchant);
        dest.writeString(sign_type);
        dest.writeString(product_name);
        dest.writeString(notify_url);
        dest.writeString(product_desc);
        dest.writeString(order_no);
        dest.writeString(oreder_amount);
        dest.writeString(sign);
        dest.writeString(product_num);
        dest.writeString(interface_version);
        dest.writeString(order_time);
        dest.writeString(product_code);
        dest.writeTypedList(orders_info);
        dest.writeString(redo_flag);
        dest.writeString(key);
    }


    public static class Orders_Info  implements Parcelable{
        private String mer_id;
        private String order_monery;
        private String goods_name;
        private String order_id;

        public static final Creator<Orders_Info> CREATOR = new Creator<Orders_Info>() {
            @Override
            public Orders_Info createFromParcel(Parcel in) {
                return new Orders_Info(in);
            }

            @Override
            public Orders_Info[] newArray(int size) {
                return new Orders_Info[size];
            }
        };

        public Orders_Info(Parcel in) {
            mer_id=in.readString();
            order_monery=in.readString();
            goods_name=in.readString();
            order_id=in.readString();
        }

        public void setMer_id(String mer_id) {
            this.mer_id = mer_id;
        }

        public void setOrder_monery(String order_monery) {
            this.order_monery = order_monery;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMer_id() {
            return mer_id;
        }

        public String getOrder_monery() {
            return order_monery;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mer_id);
            dest.writeString(order_monery);
            dest.writeString(goods_name);
            dest.writeString(order_id);
        }
    }
}
