package com.zkboys.sdk.model;

public class ClientVersionInfo {

    private String versionName;              // 版本名称
    private Short versionCode;         // 版本号
    private Byte promote;               // 是否升级  1升级  0不升级   2强制升级
    private String appUrl;              // app下载地址
    private String upgradePrompt;       // 升级提示

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Short getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Short versionCode) {
        this.versionCode = versionCode;
    }

    public Byte getPromote() {
        return promote;
    }

    public void setPromote(Byte promote) {
        this.promote = promote;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getUpgradePrompt() {
        return upgradePrompt;
    }

    public void setUpgradePrompt(String upgradePrompt) {
        this.upgradePrompt = upgradePrompt;
    }

    @Override
    public String toString() {
        return "ClientVersionInfo{" +
                "versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", promote=" + promote +
                ", appUrl='" + appUrl + '\'' +
                ", upgradePrompt='" + upgradePrompt + '\'' +
                '}';
    }
}
