package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * jwt abstract implementation
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractJwtProvider implements JwtProvider {

    protected final JwtProperties jwtProperties;

    /**
     * 创建 JWT
     *
     * @param subject 主题（用户名）
     * @return JWT
     */
    @Override
    public String createJwt(String subject) {
        Claims claims = Jwts.claims();
        claims.setSubject(subject);
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
        Jws<Claims> claimsJws;
        try {
            claimsJws = jwtParser.parseClaimsJws(jwt);
            return claimsJws.getBody();
            // catch exceptions to avoid exception stack printing when jwt expires.
        } catch (ExpiredJwtException e) {
            log.warn("the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.");
        } catch (UnsupportedJwtException e) {
            log.warn("the claimsJws argument does not represent an Claims JWS.");
        } catch (MalformedJwtException e) {
            log.warn("the claimsJws string is not a valid JWS.");
        } catch (SignatureException e) {
            log.warn("the claimsJws JWS signature validation fails.");
        } catch (IllegalArgumentException e) {
            log.warn("the claimsJws string is null or empty or only whitespace.");
        }
        // ignore
        return null;
    }

    protected abstract JwtParser buildJwtParser();
}
