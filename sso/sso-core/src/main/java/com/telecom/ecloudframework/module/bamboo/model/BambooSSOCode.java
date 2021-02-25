package com.telecom.ecloudframework.module.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * <pre>
 * 描述：sso code结果实体
 * </pre>
 *
 * @author 谢石
 * @date 2021-2-3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BambooSSOCode implements Serializable {
    private static final long serialVersionUID = -700694295167942753L;
    private String code;
    private String client_id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
