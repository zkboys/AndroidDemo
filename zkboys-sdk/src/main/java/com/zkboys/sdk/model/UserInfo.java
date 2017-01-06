package com.zkboys.sdk.model;

public class UserInfo {
    private String userName;
    private String loginName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                '}';
    }
}
