package com.wxy.circle.server.context;


import com.wxy.circle.server.constant.CircleConstant;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: YunJuClub-Flex
 * @description: 登陆上下文 将用户信息加入到线程中
 * @author: 32115
 * @create: 2024-05-23 15:47
 */
public class LoginContextHolder {

    // 创建线程本地变量
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL = new InheritableThreadLocal<>();

    // 获取线程本地变量
    public static Object getContext(String key) {
        return getThreadLocalMap().get(key);
    }

    // 设置线程本地变量
    public static void setContext(String key, Object value) {
        // 获取线程本地变量
        Map<String, Object> map = getThreadLocalMap();
        // 将要加入线程的数据放入线程本地变量
        map.put(key, value);
    }

    // 获取loginId
    public static String getLoginId() {
        return (String) getThreadLocalMap().get(CircleConstant.LOGIN_ID);
    }

    // 移除线程本地变量
    public static void removeContext() {
        THREAD_LOCAL.remove();
    }

    /**
     * @author: 32115
     * @description: 初始化一个线程本地变量
     * @date: 2024/5/23
     * @return: Map<String, Object>
     */
    public static Map<String, Object> getThreadLocalMap(){
        // 获取线程本地变量
        Map<String, Object> map = THREAD_LOCAL.get();
        // 如果为空则创建一个线程本地变量
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        // 返回线程本地变量
        return map;
    }
}
