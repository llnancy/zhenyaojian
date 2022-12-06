package com.sunchaser.shushan.zhenyaojian.framework.security.handler;

import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.enums.AccessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * logout handler
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/5
 */
// @Component
@Slf4j
public class ZyjLogoutHandler implements LogoutHandler {

    @Override
    @AccessLog(type = AccessType.LOGIN)
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("todo 异步记录日志时 SecurityContextHolder 已被清理，无法获取用户信息");
    }
}
