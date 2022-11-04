package com.sunchaser.shushan.zhenyaojian.framework.security.handler;

import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
public class ZyjAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(SingleResponse.failure());
        response.getWriter().flush();
    }
}
