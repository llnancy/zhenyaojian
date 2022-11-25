package com.sunchaser.shushan.zhenyaojian.framework.security.handler;

import com.google.common.net.MediaType;
import com.sunchaser.shushan.mojian.base.enums.ResponseEnum;
import com.sunchaser.shushan.mojian.base.util.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Component
public class ZyjLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.JSON_UTF_8.toString());
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JsonUtils.toJsonString(ResponseEnum.LOGOUT_SUCCESS.toResponse()));
    }
}
