package com.wxy.auth.common.utils;


import com.wxy.auth.common.context.LoginContextHolder;

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
