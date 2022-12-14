package io.github.llnancy.zhenyaojian.framework.service.jwt.impl;

import io.github.llnancy.zhenyaojian.framework.config.property.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * jwt implementation with none sign
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public class NoneSignJwtProviderImpl extends AbstractJwtProvider {

    public NoneSignJwtProviderImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .build();
    }
}
