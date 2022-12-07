package io.github.llnancy.zhenyaojian.framework.security.handler;

import com.google.common.net.MediaType;
import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.enums.ResponseEnum;
import io.github.llnancy.mojian.base.util.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理认证异常 {@link AuthenticationException}
 * 匿名用户访问无权限资源时的异常处理
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@Component
public class ZyjAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        IResponse res;
        if (authException instanceof BadCredentialsException) {
            res = ResponseEnum.BAD_CREDENTIALS.toResponse();
        } else {
            res = ResponseEnum.UNAUTHORIZED.toResponse();
        }
        authException.printStackTrace();
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JsonUtils.toJsonString(res));
    }
}
