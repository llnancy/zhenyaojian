package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

/**
 * jwt implementation with none sign
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public class NoneSignJwtServiceImpl extends AbstractJwtService {

    public NoneSignJwtServiceImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    public String createJwt(LoginUser user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration()))
                .compact();
    }

    @Override
    public Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(jwt)
                .getBody();
    }
}
