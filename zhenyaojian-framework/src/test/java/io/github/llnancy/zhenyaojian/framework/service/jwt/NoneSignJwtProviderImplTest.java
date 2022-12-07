package io.github.llnancy.zhenyaojian.framework.service.jwt;

import io.github.llnancy.zhenyaojian.framework.config.property.JwtProperties;
import io.github.llnancy.zhenyaojian.framework.security.LoginUser;
import io.github.llnancy.zhenyaojian.framework.service.jwt.impl.NoneSignJwtProviderImpl;
import io.github.llnancy.zhenyaojian.system.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * NoneSignJwtServiceImpl Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
class NoneSignJwtProviderImplTest {

    private LoginUser loginUser;

    private JwtProvider jwtProvider;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("aaa");
        userEntity.setPassword("bbb");
        loginUser = new LoginUser(userEntity, Collections.emptyList());
        jwtProvider = new NoneSignJwtProviderImpl(jwtProperties);
    }

    @Test
    void createJwt() {
        String jwt = jwtProvider.createJwt(loginUser.getUsername());
        System.out.println(jwt);
    }

    @Test
    void parseJwt() {
        Claims claims = jwtProvider.parseJwt("eyJhbGciOiJub25lIn0.eyJzdWIiOiJhYWEiLCJpYXQiOjE2Njc0ODk5MDEsImV4cCI6MTY2NzQ5MDExN30.");
        System.out.println(claims);
    }
}