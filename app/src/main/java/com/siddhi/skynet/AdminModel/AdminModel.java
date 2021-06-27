package com.siddhi.skynet.AdminModel;

/**
 * Created by Prakash on 6/28/2021.
 */
public class AdminModel {
    String userName, password, createdAt, tokenID;
    Boolean status;

    public AdminModel(String userName, String password, String createdAt, String tokenID, Boolean status) {
        this.userName = userName;
        this.password = password;
        this.createdAt = createdAt;
        this.tokenID = tokenID;
        this.status = status;
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

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

