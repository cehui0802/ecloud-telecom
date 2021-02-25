package com.telecom.ecloudframework.module.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * <pre>
 * 描述：sso token 实体
 * </pre>
 *
 * @author 谢石
 * @date 2020-7-30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BambooSSOToken implements Serializable {
    private static final long serialVersionUID = -700694295167942753L;
    private String access_token;
    private String refresh_token;
    private String uid;
    private Long expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}
