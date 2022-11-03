package com.sunchaser.shushan.zhenyaojian.framework.service.jwt;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * JWT Helper
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
public class JwtHelper {

    public static void main(String[] args) {
        generateSecret();
    }

    public static void generateRsaKey() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicBase64 = Encoders.BASE64.encode(publicKey.getEncoded());
        String privateBase64 = Encoders.BASE64.encode(privateKey.getEncoded());
        FileUtil.writeString(publicBase64, new File("./zhenyaojian-framework/src/main/resources/public.key"), StandardCharsets.UTF_8);
        FileUtil.writeString(privateBase64, new File("./zhenyaojian-framework/src/main/resources/private.key"), StandardCharsets.UTF_8);
    }

    public static void generateSecret() {
        // 创建随机安全密钥
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // base64 编码保存
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
    }
}
