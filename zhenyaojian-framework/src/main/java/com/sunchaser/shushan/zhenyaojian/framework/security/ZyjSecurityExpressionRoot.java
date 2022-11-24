package com.sunchaser.shushan.zhenyaojian.framework.security;

import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

/**
 * custom {@link SecurityExpressionRoot}
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/25
 */
@Component("ss")
public class ZyjSecurityExpressionRoot {

    public boolean hasAuthority(String authority) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (Objects.isNull(loginUser)) {
            return false;
        }
        if (SecurityUtils.isSuperAdmin(loginUser)) {
            return true;
        }
        if (StringUtils.isBlank(authority)) {
            return false;
        }
        Collection<GrantedAuthority> authorities = loginUser.getAuthorities();
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        return AuthorityUtils.authorityListToSet(authorities).contains(authority);
    }
}
