package org.slw.sso.user;

import java.io.Serializable;

public class SsoUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String loginName;
    private String userName;
    private String sessionId;

    public SsoUser() {
    }

    public SsoUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}