package io.github.llnancy.zhenyaojian.framework.service.jwt;

import io.github.llnancy.zhenyaojian.framework.config.property.JwtProperties;
import io.github.llnancy.zhenyaojian.framework.security.LoginUser;
import io.github.llnancy.zhenyaojian.framework.service.jwt.impl.SecretJwtProviderImpl;
import io.github.llnancy.zhenyaojian.system.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;

import java.util.Collections;

/**
 * SecretJwtServiceImpl Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
class SecretJwtProviderImplTest {

    private LoginUser loginUser;

    private JwtProvider jwtProvider;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignType(JwtProperties.SignType.SECRET);
        jwtProperties.setSecret("K7S8Kri2kAdzFOTTz1Emf8bVN/L+Gku4i9CqMVs7+j8=");
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("aaa");
        userEntity.setPassword("bbb");
        loginUser = new LoginUser(userEntity, Collections.emptyList());
        jwtProvider = new SecretJwtProviderImpl(jwtProperties);
    }

    @org.junit.jupiter.api.Test
    void createJwt() {
        String jwt = jwtProvider.createJwt(loginUser.getUsername());
        System.out.println(jwt);
    }

    @org.junit.jupiter.api.Test
    void parseJwt() {
        Claims claims = jwtProvider.parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWEiLCJpYXQiOjE2Njc0ODg3OTIsImV4cCI6MTY2NzQ4OTAwOH0.h7i6BhLtHevLDlGtbtGpfffMRuAhdPlGG7oZaVutvdA");
        System.out.println(claims);
    }
}