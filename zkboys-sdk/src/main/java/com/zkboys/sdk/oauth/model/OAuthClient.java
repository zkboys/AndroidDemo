package com.zkboys.sdk.oauth.model;

import java.io.Serializable;

/**
 * 客户端基于OAuth相关的配置
 */
public class OAuthClient implements Serializable {

    private static final long serialVersionUID = -1824911768741082049L;
    public String appKey;
    public String appSecret;
    public String scopes;       // 以逗号隔开的字符串，如："scope1,scope2" TODO 这个是干嘛的？token的使用权限范围？制定token可以访问的资源？

    @Override
    public String toString() {
        return "OAuthClient{" +
                "appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", scopes='" + scopes + '\'' +
                '}';
    }
}
