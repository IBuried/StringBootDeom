package com.ssc.demo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class IpUtil {

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress iNet = InetAddress.getLocalHost();
                    ipAddress = iNet.getHostAddress();
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    public static String ipAddrLog(HttpServletRequest request) {
        String ipAddress = getIpAddr(request);
        log.info("IP地址(多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割):" + ipAddress);
        log.info("用户访问来源：" + request.getHeader("User-Agent"));
        log.info("访问URL：" + request.getRequestURL());
        return ipAddress;
    }
}
