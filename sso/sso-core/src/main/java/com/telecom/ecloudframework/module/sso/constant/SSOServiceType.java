package com.telecom.ecloudframework.module.sso.constant;

/**
 * <pre>
 * 描述：SSO服务类型
 * </pre>
 *
 * @author 谢石
 * @date 2020-7-31
 */
public enum SSOServiceType {
    /**
     * 竹云 sso
     */
    BAMBOO("bamboo");
    /**
     * 键
     */
    private String key;

    /**
     * 构造方法
     *
     * @param key
     */
    SSOServiceType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    /**
     * 通过key获取对象
     *
     * @param key
     * @return
     */
    public static SSOServiceType fromKey(String key) {
        for (SSOServiceType c : SSOServiceType.values()) {
            if (c.getKey().equalsIgnoreCase(key)) {
                return c;
            }
        }
        throw new IllegalArgumentException(key);
    }

}
