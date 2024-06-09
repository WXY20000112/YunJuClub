package com.wxy.circle.server.utils;


import com.wxy.circle.server.context.LoginContextHolder;

/**
 * @program: YunJuClub-Flex
 * @description: 线程工具类
 * @author: 32115
 * @create: 2024-05-23 16:03
 */
public class ThreadLocalUtil {

    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}
