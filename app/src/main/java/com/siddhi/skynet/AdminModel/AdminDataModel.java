package com.siddhi.skynet.AdminModel;

/**
 * Created by Prakash on 6/28/2021.
 */
public class AdminDataModel {
    String userName, password, createdAt;

    public AdminDataModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
