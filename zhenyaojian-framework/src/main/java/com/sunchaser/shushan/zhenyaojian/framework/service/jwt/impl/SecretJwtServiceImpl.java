package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

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
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.signWith(getSecretKey())
                .compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }
}
