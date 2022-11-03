package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.NoneSignJwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * NoneSignJwtServiceImpl Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
class NoneSignJwtServiceImplTest {

    private LoginUser loginUser;

    private JwtService jwtService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        loginUser = new LoginUser("aaa", "bbb", Collections.emptyList());
        jwtService = new NoneSignJwtServiceImpl(jwtProperties);
    }

    @Test
    void createJwt() {
        String jwt = jwtService.createJwt(loginUser);
        System.out.println(jwt);
    }

    @Test
    void parseJwt() {
        Claims claims = jwtService.parseJwt("eyJhbGciOiJub25lIn0.eyJzdWIiOiJhYWEiLCJpYXQiOjE2Njc0ODk5MDEsImV4cCI6MTY2NzQ5MDExN30.");
        System.out.println(claims);
    }
}