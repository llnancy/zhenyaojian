package com.sunchaser.shushan.zhenyaojian.framework.security.handler;

import com.sunchaser.shushan.mojian.base.enums.ResponseEnum;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理认证异常 AuthenticationException
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@Component
public class ZyjAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JsonUtils.toJsonString(ResponseEnum.UNAUTHORIZED.toResponse()));
    }
}
