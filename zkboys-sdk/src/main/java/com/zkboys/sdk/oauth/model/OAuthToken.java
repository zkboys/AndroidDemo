package com.zkboys.sdk.oauth.model;

public class OAuthToken {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
    private String scopes;           // 权限范围, 支持多个,以逗号分割

    public OAuthToken() {
    }

    public OAuthToken(String accessToken, Long expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scope) {
        this.scopes = scope;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", scope='" + scopes + '\'' +
                '}';
    }
}
