package com.telecom.ecloudframework.module.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * <pre>
 * 描述：sso 手机token实体
 * </pre>
 *
 * @author 谢石
 * @date 2021-2-3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BambooMobileTokenInfo implements Serializable {
    private static final long serialVersionUID = -700694295167942753L;
    private BambooMobileTokenInfoDetail data;
    private BambooMobileTokenInfoMsg message;

    public BambooMobileTokenInfoDetail getData() {
        return data;
    }

    public void setData(BambooMobileTokenInfoDetail data) {
        this.data = data;
    }

    public BambooMobileTokenInfoMsg getMessage() {
        return message;
    }

    public void setMessage(BambooMobileTokenInfoMsg message) {
        this.message = message;
    }

    public static class BambooMobileTokenInfoDetail implements Serializable {
        private static final long serialVersionUID = -700694295167942753L;
        private String tokenId;

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }
    }

    public static class BambooMobileTokenInfoMsg implements Serializable {
        private static final long serialVersionUID = -700694295167942753L;
        private String name;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
