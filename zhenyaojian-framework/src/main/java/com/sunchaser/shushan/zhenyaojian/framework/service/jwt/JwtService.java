package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import io.jsonwebtoken.Claims;

/**
 * jwt service
 * -------------------------------
 * JSON Web Token Service
 * Standard Claims:
 * iss：发行人
 * exp：到期时间
 * sub：主题
 * aud：用户
 * nbf：在此之前不可用
 * iat：发布时间
 * jti：JWT ID 用于标识该 JWT
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public interface JwtService {

    /**
     * 创建 JWT
     *
     * @param user LoginUser
     * @return JWT
     */
    String createJwt(LoginUser user);

    /**
     * 解析 JWT
     *
     * @param jwt JWT
     * @return Claims
     */
    Claims parseJwt(String jwt);
}
