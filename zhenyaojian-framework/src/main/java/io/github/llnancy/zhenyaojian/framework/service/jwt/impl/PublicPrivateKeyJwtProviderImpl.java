package io.github.llnancy.zhenyaojian.framework.service.jwt.impl;

import cn.hutool.core.io.FileUtil;
import io.github.llnancy.zhenyaojian.framework.config.property.JwtProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * jwt implementation based on public-private key encryption
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
public class PublicPrivateKeyJwtProviderImpl extends AbstractJwtProvider {

    public PublicPrivateKeyJwtProviderImpl(JwtProperties jwtProperties) {
        super(jwtProperties);
    }

    @Override
    protected String doCreateJwt(JwtBuilder jwtBuilder) {
        return jwtBuilder.signWith(getPrivateKey())
                .compact();
    }

    @Override
    protected JwtParser buildJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build();
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
