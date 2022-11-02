package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import io.jsonwebtoken.Claims;

/**
 * jwt service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public interface JwtService {

    String createJwt(LoginUser user);

    Claims parseJwt(String jwt);
}
