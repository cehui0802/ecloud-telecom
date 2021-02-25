package com.telecom.ecloudframework.module.bamboo.service;

import com.telecom.ecloudframework.base.core.cache.ICache;
import com.telecom.ecloudframework.module.bamboo.BambooConfig;
import com.telecom.ecloudframework.module.bamboo.client.BambooSSOClient;
import com.telecom.ecloudframework.module.bamboo.model.BambooSSOToken;
import com.telecom.ecloudframework.module.sso.constant.SSOServiceType;
import com.telecom.ecloudframework.org.api.model.ISSOUser;
import com.telecom.ecloudframework.org.api.service.SSOService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 描述： 竹云 SSO 服务
 * </pre>
 *
 * @author 谢石
 * @date 2020-7-30
 */
@Service
public class BambooSSOService implements SSOService {
    private static final String BAMBOO_TOKEN_CACHE_KEY = "ecloudframework:bambooToken:";
    private static final String LOGINNAME_CACHE_KEY = "ecloudframework:loginName:";
    @Autowired
    public BambooConfig config;

    @Autowired
    BambooSSOClient client;
    @Resource
    ICache<BambooSSOToken> iCache;
    @Resource
    ICache<String> iCacheloginName;

    /**
     * 获取sso token
     *
     * @param request
     * @return
     * @author 谢石
     * @date 2020-7-31
     */
    private String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public ISSOUser getSSOUser(HttpServletRequest request, HttpServletResponse response) {
        String audience = request.getHeader("audience");
        if (StringUtils.isEmpty(audience)) {
            audience = "pc";
        }
        ISSOUser user = null;
        String accessToken = getCookie(request, config.getAccessToken());
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = request.getHeader(config.getAccessToken());
        }
        if (StringUtils.isNotEmpty(accessToken)) {
            String loginName = iCacheloginName.getByKey(LOGINNAME_CACHE_KEY.concat(accessToken));
            if (StringUtils.isNotEmpty(loginName)) {
                BambooSSOToken bambooSSOToken = iCache.getByKey(BAMBOO_TOKEN_CACHE_KEY.concat(audience + ":").concat(loginName));
                if (null != bambooSSOToken) {
                    String accessTokenOld = bambooSSOToken.getAccess_token();
                    if (accessToken.equals(accessTokenOld)) {
                        user = getUserByAccessToken(request, response, bambooSSOToken, audience);
                    }
                }
            }
            if (user == null) {
                iCacheloginName.delByKey(LOGINNAME_CACHE_KEY.concat(accessToken));
                if (StringUtils.isNotEmpty(loginName)) {
                    iCache.delByKey(BAMBOO_TOKEN_CACHE_KEY.concat(audience + ":").concat(loginName));
                }
            }
        }
        if (user == null) {
            //获取code
            String code = request.getParameter(config.getCodeKey());
            if (StringUtils.isNotEmpty(code)) {
                //用code换token
                BambooSSOToken bambooSSOToken = client.getSSOTokenByCode(code);
                accessToken = bambooSSOToken.getAccess_token();
                if (StringUtils.isNotEmpty(accessToken)) {
                    user = getUserByAccessToken(request, response, bambooSSOToken, audience);
                }
            }
        }
        return user;
    }

    /**
     * 通过accessToken 获取用户
     *
     * @param bambooSSOToken
     * @param audience
     * @return
     * @author 谢石
     * @date 2020-12-30
     */
    private ISSOUser getUserByAccessToken(HttpServletRequest request, HttpServletResponse response, BambooSSOToken bambooSSOToken, String audience) {
        ISSOUser user = null;
        String accessToken = bambooSSOToken.getAccess_token();
        try {
            //获取用户信息
            user = client.getSsoUserByAccessToken(accessToken);
            if (null != user && StringUtils.isNotEmpty(user.getAccount())) {
                if (null != request) {
                    addAccessTokenCookie(request, response, accessToken);
                }
                iCacheloginName.add(LOGINNAME_CACHE_KEY.concat(accessToken), user.getAccount());
                iCache.add(BAMBOO_TOKEN_CACHE_KEY.concat(audience + ":").concat(user.getAccount()), bambooSSOToken);
            }
        } catch (Exception e) {
            //通过刷新token重新获取用户信息
            iCacheloginName.delByKey(LOGINNAME_CACHE_KEY.concat(accessToken));
            if (accessToken.equals(bambooSSOToken.getAccess_token())) {
                bambooSSOToken = client.getSSOTokenByRefreshToken(bambooSSOToken.getRefresh_token());
                if (null != bambooSSOToken && StringUtils.isNotEmpty(bambooSSOToken.getAccess_token())
                        && StringUtils.isNotEmpty(bambooSSOToken.getRefresh_token())) {
                    user = client.getSsoUserByAccessToken(bambooSSOToken.getAccess_token());
                    iCacheloginName.add(LOGINNAME_CACHE_KEY.concat(accessToken), user.getAccount());
                    iCache.add(BAMBOO_TOKEN_CACHE_KEY.concat(audience + ":").concat(user.getAccount()), bambooSSOToken);
                    if (null != request) {
                        addAccessTokenCookie(request, response, bambooSSOToken.getAccess_token());
                        response.setHeader(config.getAccessToken(), bambooSSOToken.getAccess_token());
                    }
                }
            }
        }
        return user;
    }

    /**
     * 注销sso token
     *
     * @param request
     * @author 谢石
     * @date 2020-7-31
     */
    @Override
    public void invalidSSOToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = getCookie(request, config.getAccessToken());
        client.revokeToken(accessToken);
        cleanCookie(request, response);
    }

    private void addAccessTokenCookie(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        String domain = "";
        //临时增加只适用于bpm
        String serverName = request.getServerName();
        if (StringUtils.isNotEmpty(serverName)) {
            String[] serverNames = serverName.split("\\.");
            if (serverNames.length == 3) {
                domain = serverNames[1].concat(".") + serverNames[2];
            }
        }
        Cookie cookie = new Cookie(config.getAccessToken(), accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        if (StringUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }

        response.addCookie(cookie);
    }

    @Override
    public String getType() {
        return SSOServiceType.BAMBOO.getKey();
    }

    /**
     * 清理cookie
     *
     * @param request
     * @param response
     * @author 谢石
     * @date 2020-7-28
     */
    private static void cleanCookie(HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            Cookie temp = new Cookie(cookie.getName(), null);
            temp.setPath("/");
            temp.setMaxAge(0);
            response.addCookie(temp);
        }
    }

    public String login(String account, String password) {
        String audience = "mobile";
        String accessToken = "";
        //获取code

        String code = client.getCodeByUserPassword(account, password);
        if (StringUtils.isNotEmpty(code)) {
            //用code换token
            BambooSSOToken bambooSSOToken = client.getSSOTokenByCode(code);
            accessToken = bambooSSOToken.getAccess_token();
            if (StringUtils.isNotEmpty(accessToken)) {
                getUserByAccessToken(null, null, bambooSSOToken, audience);
            }
        }
        return accessToken;
    }
}
