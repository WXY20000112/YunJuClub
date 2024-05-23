package com.wxy.subject.common.interceptor;

import com.wxy.subject.common.constant.SubjectConstant;
import com.wxy.subject.common.context.LoginContextHolder;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @program: YunJuClub-Flex
 * @description: 登录拦截器
 * @author: 32115
 * @create: 2024-05-23 15:26
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @author: 32115
     * @description: 前置拦截器 拦截请求并获取用户信息并加入线程中
     * @date: 2024/5/23
     * @param: request
     * @param: response
     * @param: handler
     * @return: boolean
     */
    @Override
    public boolean preHandle(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler) {
        // 获取用户信息
        String loginId = request.getHeader(SubjectConstant.LOGIN_ID);
        // 将用户信息加入线程中
        LoginContextHolder.setContext(SubjectConstant.LOGIN_ID, loginId);
        return true;
    }

    /**
     * @author: 32115
     * @description: 后置拦截器 当请求结束后执行 此时在方法内执行将线程中的用户信息移除
     * @date: 2024/5/23
     * @param: request
     * @param: response
     * @param: handler
     * @param: ex
     * @return: void
     */
    @Override
    public void afterCompletion(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull Object handler,
            @Nullable Exception ex) {
        // 移除线程中的用户信息
        LoginContextHolder.removeContext();
    }
}
