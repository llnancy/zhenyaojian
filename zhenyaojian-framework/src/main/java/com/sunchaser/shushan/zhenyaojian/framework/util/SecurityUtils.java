package com.sunchaser.shushan.zhenyaojian.framework.util;

import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * security utils
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/8
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUser) authentication.getPrincipal();
    }

    public static Long getLoginUserId() {
        return getLoginUser().getUserId();
    }

    public static boolean isLoginUser(Long userId) {
        return Objects.equals(getLoginUserId(), userId);
    }

    public static boolean isNotLoginUser(Long userId) {
        return !isLoginUser(userId);
    }

    public static boolean isSuperAdmin(Long userId) {
        return userId == 1L;
    }

    public static boolean isNotSuperAdmin(Long userId) {
        return !isSuperAdmin(userId);
    }
}
