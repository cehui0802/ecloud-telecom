package com.telecom.ecloudframework.module.bamboo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 竹云 SSO配置
 *
 * @author 谢石
 * @date 2020-7-30
 */
@Configuration
public class BambooConfig {

    /**
     * code key
     */
    @Value("${ecloud.sso.bamboo.code-key:code}")
    String codeKey;
    /**
     * access token
     */
    @Value("${ecloud.sso.bamboo.access-token:access_token}")
    String accessToken;
    /**
     * refresh token
     */
    @Value("${ecloud.sso.bamboo.refresh-token:refresh_token}")
    String refreshToken;

    /**
     * sso地址
     */
    @Value("${ecloud.sso.bamboo.url:}")
    String ssoUrl;
    /**
     * sso地址
     */
    @Value("${ecloud.sso.bamboo.client-id:}")
    String clientId;
    /**
     * sso地址
     */
    @Value("${ecloud.sso.bamboo.client-secret:}")
    String clientSecret;
    /**
     * app id
     */
    @Value("${ecloud.sso.bamboo.app-id:}")
    String appId;
    /**
     * 获取授权地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.get-token-url:}")
    String getTokenUrl;
    /**
     * 刷新授权地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.refresh-token-url:}")
    String refreshTokenUrl;
    /**
     * 查询授权地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.get-token-info-url:}")
    String getTokenInfoUrl;
    /**
     * 回收授权地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.revoke-token-url:}")
    String revokeTokenUrl;
    /**
     * 检查授权是否有效地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.check-token-valid-url:}")
    String checkTokenValidUrl;
    /**
     * 获取用户信息地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.get-user-info-url:}")
    String getUserInfoUrl;
    /**
     * 获取code信息地址
     */
    @Value("${ecloud.sso.bamboo.oauth2.get-code-url:}")
    String getCodeUrl;
    /**
     * 获取用户信息地址
     */
    @Value("${ecloud.sso.bamboo.restful.login-url:}")
    String getLoginUrl;

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }

    public String getGetTokenInfoUrl() {
        return getTokenInfoUrl;
    }

    public void setGetTokenInfoUrl(String getTokenInfoUrl) {
        this.getTokenInfoUrl = getTokenInfoUrl;
    }

    public String getRevokeTokenUrl() {
        return revokeTokenUrl;
    }

    public void setRevokeTokenUrl(String revokeTokenUrl) {
        this.revokeTokenUrl = revokeTokenUrl;
    }

    public String getCheckTokenValidUrl() {
        return checkTokenValidUrl;
    }

    public void setCheckTokenValidUrl(String checkTokenValidUrl) {
        this.checkTokenValidUrl = checkTokenValidUrl;
    }

    public String getGetUserInfoUrl() {
        return getUserInfoUrl;
    }

    public void setGetUserInfoUrl(String getUserInfoUrl) {
        this.getUserInfoUrl = getUserInfoUrl;
    }

    public String getGetCodeUrl() {
        return getCodeUrl;
    }

    public void setGetCodeUrl(String getCodeUrl) {
        this.getCodeUrl = getCodeUrl;
    }

    public String getGetLoginUrl() {
        return getLoginUrl;
    }

    public void setGetLoginUrl(String getLoginUrl) {
        this.getLoginUrl = getLoginUrl;
    }
}
