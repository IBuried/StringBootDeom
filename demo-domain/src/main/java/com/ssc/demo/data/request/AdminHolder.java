package com.ssc.demo.data.request;

/**
 * @description:
 * @author: LiuDan
 * @date: 2024/2/5 14:19
 * @version: v1.0
 * @change: [修改时间] [修改者]@[版本号] [修改内容说明]
 */
public class AdminHolder {
    private final static ThreadLocal<RequestAdmin> ADMIN_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取请求用户信息
     * @return {@link RequestAdmin}
     * @author liaoyz
     * @date 2022/03/24 11:20
     **/
    public static RequestAdmin get() {
        return ADMIN_THREAD_LOCAL.get();
    }

    /**
     * 设置请求用户信息
     * @param eu 用户信息
     * @author liaoyz
     * @date 2022/03/24 11:32
     **/
    public static void set(RequestAdmin eu) {
        ADMIN_THREAD_LOCAL.set(eu);
    }

    /**
     * 释放内存
     * @author liaoyz
     * @date 2022/03/24 14:20
     **/
    public static void remove() {
        ADMIN_THREAD_LOCAL.remove();
    }

    /**
     * 获取管理员id
     * @return {@link Long}
     * @author liaoyz
     * @date 2022/03/24 15:58
     **/
    public static Long getAdminId() {
        RequestAdmin admin = ADMIN_THREAD_LOCAL.get();
        if (admin == null) {
            return null;
        }
        return admin.getId();
    }
}
