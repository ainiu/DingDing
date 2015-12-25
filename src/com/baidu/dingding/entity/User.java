package com.baidu.dingding.entity;


import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/19.user
 */
public class User implements Serializable{

    String usrNumber;
    String token;
    String status;
    String email;
    String userId;
    String type;
    String kxfJfCunt;
    String usrPic;
    String usrName;

    public User() {
    }

    public User(String usrNumber, String token, String status, String email, String type, String userId, String kxfJfCunt, String usrPic, String usrName) {
        this.usrNumber = usrNumber;
        this.token = token;
        this.status = status;
        this.email = email;
        this.type = type;
        this.userId = userId;
        this.kxfJfCunt = kxfJfCunt;
        this.usrPic = usrPic;
        this.usrName = usrName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsrNumber() {
        return usrNumber;
    }

    public void setUsrNumber(String usrNumber) {
        this.usrNumber = usrNumber;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsrPic() {
        return usrPic;
    }

    public void setUsrPic(String usrPic) {
        this.usrPic = usrPic;
    }

    public String getKxfJfCunt() {
        return kxfJfCunt;
    }

    public void setKxfJfCunt(String kxfJfCunt) {
        this.kxfJfCunt = kxfJfCunt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "usrNumber='" + usrNumber + '\'' +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", kxfJfCunt='" + kxfJfCunt + '\'' +
                ", usrPic='" + usrPic + '\'' +
                ", usrName='" + usrName + '\'' +
                '}';
    }
}
