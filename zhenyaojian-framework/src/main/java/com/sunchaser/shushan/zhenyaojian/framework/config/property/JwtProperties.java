package com.sunchaser.shushan.zhenyaojian.framework.config.property;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.springframework.security.config.Elements.JWT;

/**
 * jwt properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@Data
@ConfigurationProperties(prefix = JWT)
public class JwtProperties {

    private Long expiration = 60 * 3600L;

    private SignType signType = SignType.SECRET;

    private String secret;

    private String publicKeyLocation;

    private String privateKeyLocation;

    @Getter
    public enum SignType {

        /**
         * 不签名
         */
        NONE,

        /**
         * SECRET: 秘钥
         */
        SECRET,

        /**
         * 公私钥
         */
        PUBLIC_PRIVATE_KEY
    }
}
