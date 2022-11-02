package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
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
 * @since JDK8 2022/11/2
 */
public class JwtRsaHelper {


    /*
    public static void main(String[] args) {
        generateRsaKey();
    }
    */

    private static void generateRsaKey() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicBase64 = Encoders.BASE64.encode(publicKey.getEncoded());
        String privateBase64 = Encoders.BASE64.encode(privateKey.getEncoded());
        FileUtil.writeString(publicBase64, new File("./zhenyaojian-framework/src/main/resources/public.key"), StandardCharsets.UTF_8);
        FileUtil.writeString(privateBase64, new File("./zhenyaojian-framework/src/main/resources/private.key"), StandardCharsets.UTF_8);
    }
}
