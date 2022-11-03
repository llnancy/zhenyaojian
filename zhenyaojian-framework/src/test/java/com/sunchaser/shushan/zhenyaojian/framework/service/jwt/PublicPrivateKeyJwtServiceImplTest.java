package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.PublicPrivateKeyJwtServiceImpl;
import io.jsonwebtoken.Claims;

import java.util.Collections;

/**
 * PublicPrivateKeyJwtServiceImpl Test
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
class PublicPrivateKeyJwtServiceImplTest {

    private LoginUser loginUser;

    private JwtService jwtService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignType(JwtProperties.SignType.PUBLIC_PRIVATE_KEY);
        jwtProperties.setPublicKeyLocation("public.key");
        jwtProperties.setPrivateKeyLocation("private.key");
        loginUser = new LoginUser("aaa", "bbb", Collections.emptyList());
        jwtService = new PublicPrivateKeyJwtServiceImpl(jwtProperties);
    }

    @org.junit.jupiter.api.Test
    void createJwt() {
        String jwt = jwtService.createJwt(loginUser);
        System.out.println(jwt);
    }

    @org.junit.jupiter.api.Test
    void parseJwt() {
        Claims claims = jwtService.parseJwt("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhYWEiLCJpYXQiOjE2Njc0OTAwMjcsImV4cCI6MTY2NzQ5MDI0M30.hGnAxAnJKabFLI9ufPcEXnOehwgj7dlryfvr3ZFN47qFzZHmxEPymD3elPJsIIh9_CJVoNU4JZa3uVSm-T49ydmGWZxMFjzRQyeH2IfiMAMUJLRiMfHwW6KXESVYswGFScndK3V_N8_eYHdKIH85-0qrY9wCBeaYSNeOljr2J96oQozr8kuUPj_FDU3mS2K5aaVKcGdoHP8_1LBvnvnfV1P_tP0-v-Jw83Ymwqq7rZsjbqj9tisAPQNI4uk4U4uHMTaAIVCe6pyAOJ2MNs2nx90QkdNkzLhvFmDozDqU5o5ZW7LTW0guJBuQ8nI1rdJoFFk-lFnRrSOdmoj9DxhqDQ");
        System.out.println(claims);
    }
}