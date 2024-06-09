package com.wxy.circle.server.interceptor;

import com.wxy.circle.server.constant.CircleConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: Feign拦截器
 * @author: 32115
 * @create: 2024-05-23 19:44
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        if (Objects.nonNull(request)) {
            String loginId = request.getHeader(CircleConstant.LOGIN_ID);
            if (StringUtils.isNotBlank(loginId)) {
                requestTemplate.header(CircleConstant.LOGIN_ID, loginId);
            }
        }
    }
}
