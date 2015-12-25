package com.baidu.dingding.entity;

/**
 * 锟斤拷锟绞碉拷锟�
 *
 * @author wwj_748
 */
public class AdDomain{
    String id; // 广告id
    String date; // 日期
    String title; // 标题
    String topicFrom; //选题来自
    String topic; // 选题
    String PicLogpath; // 图片url
    boolean isAd; // 是否为广告
    String startTime; // 广告开始时间
    String endTime; // 广告结束时间
    String targetUrl; // 目标url
    int width; // 宽
    int height; // 高
    boolean available; // 是否可用

    public AdDomain(String id, String PicLogpath, boolean isAd) {
        this.id = id;
        this.PicLogpath = PicLogpath;
        this.isAd = isAd;
    }

    public AdDomain(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPicLogpath() {
        return PicLogpath;
    }

    public void setPicLogpath(String getPicLogpath) {
        this.PicLogpath = getPicLogpath;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean isAd) {
        this.isAd = isAd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicFrom() {
        return topicFrom;
    }

    public void setTopicFrom(String topicFrom) {
        this.topicFrom = topicFrom;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }










































}
