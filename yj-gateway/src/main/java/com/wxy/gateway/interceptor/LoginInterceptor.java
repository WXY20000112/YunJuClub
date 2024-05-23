package com.wxy.gateway.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.wxy.gateway.constant.GatewayConstant;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: YunJuClub-Flex
 * @description: 登录拦截器
 * @author: 32115
 * @create: 2024-05-23 14:53
 */
@Configuration
public class LoginInterceptor implements GlobalFilter {
    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求信息
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求体参数
        ServerHttpRequest.Builder mutate = request.mutate();
        // 获取请求路径并判断是不是登录请求 是的话直接放行
        if (request.getURI().getPath().equals("/auth/user/doLogin")) {
            return chain.filter(exchange);
        }
        // 使用Satoken获取token中的loginId
        String loginId = (String) StpUtil.getTokenInfo().getLoginId();
        // 判断loginId是否存在
        if (loginId == null) {
            // 若不存在 抛出异常
            throw new Exception("未获取到用户信息");
        }
        // 将loginId添加到请求体中 并重新构建请求
        mutate.header(GatewayConstant.LOGIN_ID, loginId);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }
}
