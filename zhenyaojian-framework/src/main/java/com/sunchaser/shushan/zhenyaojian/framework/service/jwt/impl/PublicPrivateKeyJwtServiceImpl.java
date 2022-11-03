package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import cn.hutool.core.io.FileUtil;
import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * jwt implementation based on public-private key encryption
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public class PublicPrivateKeyJwtServiceImpl extends AbstractJwtService {

    public PublicPrivateKeyJwtServiceImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    /**
     * 创建 JWT
     *
     * @param user LoginUser
     * @return JWT
     */
    @Override
    public String createJwt(LoginUser user) {
        Claims claims = Jwts.claims();
        claims.setSubject(user.getUsername());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration()))
                .signWith(getPrivateKey())
                .compact();
    }

    /**
     * 解析 JWT
     *
     * @param jwt JWT
     * @return Claims
     */
    @Override
    public Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    @SneakyThrows
    private PublicKey getPublicKey() {
        String publicStr = FileUtil.readString(jwtProperties.getPublicKeyLocation(), StandardCharsets.UTF_8);
        byte[] decode = Decoders.BASE64.decode(publicStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @SneakyThrows
    private PrivateKey getPrivateKey() {
        String privateStr = FileUtil.readString(jwtProperties.getPrivateKeyLocation(), StandardCharsets.UTF_8);
        byte[] decode = Decoders.BASE64.decode(privateStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
