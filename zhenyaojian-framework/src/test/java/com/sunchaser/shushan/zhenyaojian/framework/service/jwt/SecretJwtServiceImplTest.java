package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.SecretJwtServiceImpl;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;

import java.util.Collections;

/**
 * SecretJwtServiceImpl Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
class SecretJwtServiceImplTest {

    private LoginUser loginUser;

    private JwtService jwtService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignType(JwtProperties.SignType.SECRET);
        jwtProperties.setSecret("K7S8Kri2kAdzFOTTz1Emf8bVN/L+Gku4i9CqMVs7+j8=");
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("aaa");
        userEntity.setPassword("bbb");
        loginUser = new LoginUser(userEntity, Collections.emptyList());
        jwtService = new SecretJwtServiceImpl(jwtProperties);
    }

    @org.junit.jupiter.api.Test
    void createJwt() {
        String jwt = jwtService.createJwt(loginUser);
        System.out.println(jwt);
    }

    @org.junit.jupiter.api.Test
    void parseJwt() {
        Claims claims = jwtService.parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWEiLCJpYXQiOjE2Njc0ODg3OTIsImV4cCI6MTY2NzQ4OTAwOH0.h7i6BhLtHevLDlGtbtGpfffMRuAhdPlGG7oZaVutvdA");
        System.out.println(claims);
    }
}