package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * jwt abstract implementation
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@RequiredArgsConstructor
public abstract class AbstractJwtService implements JwtService {

    protected final JwtProperties jwtProperties;

    /**
     * 创建 JWT
     *
     * @param user LoginUser
     * @return JWT
     */
    @Override
    public String createJwt(LoginUser user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration()));
        return doCreateJwt(jwtBuilder);
    }

    protected abstract String doCreateJwt(JwtBuilder jwtBuilder);

    /**
     * 解析 JWT
     *
     * @param jwt JWT
     * @return Claims
     */
    @Override
    public Claims parseJwt(String jwt) {
        JwtParser jwtParser = buildJwtParser();
        return jwtParser.parseClaimsJws(jwt)
                .getBody();
    }

    protected abstract JwtParser buildJwtParser();
}
