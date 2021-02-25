package com.telecom.ecloudframework.module.bamboo.model;

import com.telecom.ecloudframework.org.api.model.ISSOUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * <pre>
 * 描述：sso用户实体
 * </pre>
 *
 * @author 谢石
 * @date 2020-7-30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BambooSSOUser implements ISSOUser {
    private static final long serialVersionUID = -700694295167942753L;
    private String displayName;
    private String loginName;

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public void setUserId(String userId) {

    }

    @Override
    public String getFullname() {
        return displayName;
    }

    @Override
    public void setFullname(String fullmame) {
        this.displayName = fullmame;
    }

    @Override
    public String getAccount() {
        return loginName;
    }

    @Override
    public void setAccount(String account) {
        this.loginName = account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
