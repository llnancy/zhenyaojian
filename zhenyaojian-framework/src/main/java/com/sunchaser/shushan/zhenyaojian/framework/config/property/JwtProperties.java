package com.sunchaser.shushan.zhenyaojian.framework.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.springframework.security.config.Elements.JWT;

/**
 * jwt properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@Getter
@Setter
@ConfigurationProperties(prefix = JWT)
public class JwtProperties {

    /**
     * jwt 过期时间
     */
    private Long expiration = 24 * 60 * 3600L;

    /**
     * jwt 签名类型
     */
    private SignType signType = SignType.SECRET;

    /**
     * 签名类型为秘钥时的秘钥
     */
    private String secret;

    /**
     * 签名类型为公私钥时的公钥路径
     */
    private String publicKeyLocation;

    /**
     * 签名类型为公私钥时的私钥路径
     */
    private String privateKeyLocation;

    /**
     * 签名类型
     */
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
