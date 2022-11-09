package com.sunchaser.shushan.zhenyaojian.framework.security.handler;

import com.sunchaser.shushan.mojian.base.enums.ResponseEnum;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理权限异常 AccessDeniedException
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@Component
public class ZyjAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(JsonUtils.toJsonString(ResponseEnum.FORBIDDEN.toResponse()));
    }
}
