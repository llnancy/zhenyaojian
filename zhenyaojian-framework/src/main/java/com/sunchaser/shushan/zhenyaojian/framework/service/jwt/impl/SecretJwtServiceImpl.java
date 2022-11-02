package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import io.jsonwebtoken.Claims;

/**
 * jwt implementation based on secret encryption
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public class SecretJwtServiceImpl extends AbstractJwtService {

    public SecretJwtServiceImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    public String createJwt(LoginUser user) {
        return null;
    }

    @Override
    public Claims parseJwt(String jwt) {
        return null;
    }
}
