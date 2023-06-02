package com.ssc.demo.common.swagger;

/**
 * 类说明 （必填）
 * @author Administrator
 * @date 2022/04/02 17:59
 * @change 2022/04/02 17:59 Administrator@v1.0 创建 
 */
public enum ApiVersionEnum {
    /**
     * 版本号
     */
    ADMIN_1_0_0("admin-1.0.0", 1),
    APP_1_0_0("applet-1.0.0", 2),
    ;
    private String v;
    private Integer sysType;

    ApiVersionEnum(String v, Integer sysType) {
        this.v = v;
        this.sysType = sysType;
    }

    public Integer sysType() {
        return sysType;
    }

    public void setSysType(Integer sysType) {
        this.sysType = sysType;
    }

    public String v() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
