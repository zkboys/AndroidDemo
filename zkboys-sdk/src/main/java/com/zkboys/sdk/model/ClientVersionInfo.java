package com.zkboys.sdk.model;

public class ClientVersionInfo {

    private Short version;              // 版本名称
    private String versionCode;         // 版本号
    private Byte promote;               // 是否升级  1升级  0不升级   2强制升级
    private String appUrl;              // app下载地址
    private String upgradePrompt;       // 升级提示

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Byte getPromote() {
        return promote;
    }

    public void setPromote(Byte promote) {
        this.promote = promote;
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
                "version=" + version +
                ", versionCode='" + versionCode + '\'' +
                ", promote=" + promote +
                ", appUrl='" + appUrl + '\'' +
                ", upgradePrompt='" + upgradePrompt + '\'' +
                '}';
    }
}
