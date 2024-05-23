package com.wxy.subject.common.interceptor;

import com.wxy.subject.common.constant.SubjectConstant;
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
            String loginId = request.getHeader(SubjectConstant.LOGIN_ID);
            if (StringUtils.isNotBlank(loginId)) {
                requestTemplate.header(SubjectConstant.LOGIN_ID, loginId);
            }
        }
    }
}
