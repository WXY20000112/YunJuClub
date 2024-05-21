package com.wxy.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxy.gateway.entity.Result;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: YunJuClub
 * @description: 全局异常处理
 * @author: 32115
 * @create: 2024-04-01 18:21
 */
@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Nonnull
    public Mono<Void> handle(ServerWebExchange exchange, @Nonnull Throwable throwable) {
        // 获取请求信息
        // ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // 获取响应信息
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        // 设置状态码和提示信息
        int code;
        String msg;
        // 如果捕获到的异常是SaToken抛出的SaTokenException就重新自定义他的响应信息
        if (throwable instanceof SaTokenException){
            code = 401;
            msg = "该用户无权限";
        }else {
            code = 500;
            msg = "服务器繁忙";
        }
        // 将状态码等信息封装入统一返回结果R类中
        Result<Object> result = Result.error(code, msg);
        // 获取响应头
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 将封装的返回结果写入响应头
        return serverHttpResponse.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = serverHttpResponse.bufferFactory();
            byte[] bytes = null;
            try {
                bytes = objectMapper.writeValueAsBytes(result);
            } catch (JsonProcessingException e) {
                log.error("JsonProcessingException:", e);
            }
            if (bytes == null) return null;
            return dataBufferFactory.wrap(bytes);
        }));
    }
}
