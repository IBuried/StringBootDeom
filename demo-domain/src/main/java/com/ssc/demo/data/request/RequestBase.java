package com.ssc.demo.data.request;

/**
 *
 * @description:请求基本信息
 * @author: LiuDan
 * @date: 2024/2/5 14:19
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
public class RequestBase {
    /**
     * 用户请求令牌
     */
    private String requestAccessToken;
    /**
     * 用户真实ip
     **/
    private String requestIp;
    /**
     * 用户真实ip以及各级代理ip
     */
    private String requestAllIp;

    public String getRequestAccessToken() {
        return requestAccessToken;
    }

    public void setRequestAccessToken(String requestAccessToken) {
        this.requestAccessToken = requestAccessToken;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestAllIp() {
        return requestAllIp;
    }

    public void setRequestAllIp(String requestAllIp) {
        this.requestAllIp = requestAllIp;
    }
}
