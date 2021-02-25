package com.telecom.ecloudframework.module.bamboo.client;

import com.telecom.ecloudframework.module.bamboo.BambooConfig;
import com.telecom.ecloudframework.module.bamboo.model.BambooMobileTokenInfo;
import com.telecom.ecloudframework.module.bamboo.model.BambooSSOCode;
import com.telecom.ecloudframework.module.bamboo.model.BambooSSOToken;
import com.telecom.ecloudframework.module.bamboo.model.BambooSSOUser;
import com.telecom.ecloudframework.module.sso.util.RestTemplateUtil;
import com.telecom.ecloudframework.org.api.model.ISSOUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 描述：EWorkerSSO服务
 * </pre>
 *
 * @author 谢石
 * @date 2020-7-30
 */
@Service
public class BambooSSOClient {
    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BambooConfig config;

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @return
     * @author 谢石
     * @date 2020-12-28
     */
    public ISSOUser getSsoUserByAccessToken(String accessToken) {
        BambooSSOUser iSSOUser = RestTemplateUtil.get(config.getGetUserInfoUrl().concat(String.format("?" + config.getAccessToken() + "=%s&client_id=%s", accessToken, config.getClientId())),
                getHeads(), BambooSSOUser.class);
        return iSSOUser;
    }

    /**
     * 获取token信息
     *
     * @param code
     * @return
     * @author 谢石
     * @date 2020-12-28
     */
    public BambooSSOToken getSSOTokenByCode(String code) {
        Map<String, String> heads = getHeads();
        heads.put("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", config.getClientId());
        params.add("client_secret", config.getClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");

        BambooSSOToken bambooSSOToken = RestTemplateUtil.post(config.getGetTokenUrl(),
                getHeads(), params, BambooSSOToken.class);
        return bambooSSOToken;
    }

    /**
     * 刷新token信息
     *
     * @param refreshToken
     * @return
     * @author 谢石
     * @date 2020-12-28
     */
    public BambooSSOToken getSSOTokenByRefreshToken(String refreshToken) {
        Map<String, String> heads = getHeads();
        heads.put("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", config.getClientId());
        params.add("client_secret", config.getClientSecret());
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");

        BambooSSOToken bambooSSOToken = RestTemplateUtil.post(config.getRefreshTokenUrl(),
                getHeads(), params, BambooSSOToken.class);
        return bambooSSOToken;
    }

    /**
     * 注销sso token
     *
     * @param sessionId
     * @author 谢石
     * @date 2020-7-31
     */
    public void quitSso(String sessionId) {

    }

    /**
     * 请求头封装
     *
     * @return
     * @author 谢石
     * @date 2020-7-30
     */
    private Map<String, String> getHeads() {
        Map<String, String> heads = new HashMap<>();
        return heads;
    }

    /**
     * 回收授权
     *
     * @param accessToken
     */
    public void revokeToken(String accessToken) {
        RestTemplateUtil.get(config.getRevokeTokenUrl().concat(String.format("?" + config.getAccessToken() + "=%s", accessToken)),
                getHeads(), BambooSSOUser.class);
    }

    /**
     * 通过账号密码获取code
     *
     * @param account
     * @param password
     * @return
     */
    public String getCodeByUserPassword(String account, String password) {
        String code = "";
        Map<String, String> heads = getHeads();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appId", config.getAppId());
        params.add("userName", account);
        params.add("password", password);
        params.add("authnMethod", "UsernamePassword");
        try {
            params.add("remoteIp", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        BambooMobileTokenInfo bambooMobileTokenInfo = RestTemplateUtil.post(config.getGetLoginUrl(),
                getHeads(), params, BambooMobileTokenInfo.class);
        if (null != bambooMobileTokenInfo) {
            if (null != bambooMobileTokenInfo.getData() && StringUtils.isNotEmpty(bambooMobileTokenInfo.getData().getTokenId())) {
                String tokenId = bambooMobileTokenInfo.getData().getTokenId();
                code = getCodeByTokenId(tokenId);
            } else {
                if (null != bambooMobileTokenInfo.getMessage()) {
                    LOG.error("getCodeByUserPassword：获取竹云tokenId失败：" + bambooMobileTokenInfo.getMessage().getContent());
                } else {
                    LOG.error("getCodeByUserPassword：获取竹云tokenId失败：未知错误");
                }
            }
        } else {
            LOG.error("getCodeByUserPassword：请求竹云tokenId失败");
        }
        return code;
    }

    /**
     * 通过tokenId获取code
     *
     * @param tokenId
     * @return
     */
    private String getCodeByTokenId(String tokenId) {
        String code = "";
        Map<String, String> heads = getHeads();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", tokenId);
        params.add("client_id", config.getClientId());
        params.add("target_client_id", config.getClientId());
        params.add("tokenType", "restful");
        BambooSSOCode bambooSSOCode = RestTemplateUtil.post(config.getGetCodeUrl(),
                getHeads(), params, BambooSSOCode.class);
        if (null != bambooSSOCode) {
            code = bambooSSOCode.getCode();
        } else {
            LOG.error("getCodeByUserPassword：请求竹云tokenId失败");
        }
        return code;
    }
}
