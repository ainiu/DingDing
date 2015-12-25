package com.baidu.dingding.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/2.用户安全设置查询接口
 */
public class SearchSecurityEntity implements Serializable {
    String userNumber;
    String passwordQuestion;
    String passwordAnswer;
    String userEmail;

    public SearchSecurityEntity() {
    }

    public SearchSecurityEntity(String userNumber, String passwordQuestion, String passwordAnswer, String userEmail) {
        this.userNumber = userNumber;
        this.passwordQuestion = passwordQuestion;
        this.passwordAnswer = passwordAnswer;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "SearchSecurityEntity{" +
                "userNumber='" + userNumber + '\'' +
                ", passwordQuestion='" + passwordQuestion + '\'' +
                ", passwordAnswer='" + passwordAnswer + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPasswordQuestion() {
        return passwordQuestion;
    }

    public void setPasswordQuestion(String passwordQuestion) {
        this.passwordQuestion = passwordQuestion;
    }

    public String getPasswordAnswer() {
        return passwordAnswer;
    }

    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
