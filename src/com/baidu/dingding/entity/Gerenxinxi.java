package com.baidu.dingding.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/30. 个人信息
 */
public class Gerenxinxi implements Serializable{
    String userId;
    String userName;
    String realityName;
    String idCard;
    String birthday;
    String sex;
    String logpath;
    String qq;
    String telPhone;
    String mobile;
    String address;
    String post;

    public Gerenxinxi() {
    }

    public Gerenxinxi(String userId, String userName, String realityName, String idCard, String birthday, String sex, String logpath, String qq, String telPhone, String mobile, String address, String post) {
        this.userId = userId;
        this.userName = userName;
        this.realityName = realityName;
        this.idCard = idCard;
        this.birthday = birthday;
        this.sex = sex;
        this.logpath = logpath;
        this.qq = qq;
        this.telPhone = telPhone;
        this.mobile = mobile;
        this.address = address;
        this.post = post;
    }

    @Override
    public String toString() {
        return "Gerenxinxi{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", realityName='" + realityName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", logpath='" + logpath + '\'' +
                ", qq='" + qq + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", post='" + post + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealityName() {
        return realityName;
    }

    public void setRealityName(String realityName) {
        this.realityName = realityName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
