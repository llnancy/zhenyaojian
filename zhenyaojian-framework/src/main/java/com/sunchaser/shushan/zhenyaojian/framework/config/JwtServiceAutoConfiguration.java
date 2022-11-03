package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtService;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.NoneSignJwtServiceImpl;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.PublicPrivateKeyJwtServiceImpl;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.SecretJwtServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Elements;

/**
 * JwtService configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
@ConditionalOnProperty(prefix = Elements.JWT, value = "enabled", matchIfMissing = true)
public class JwtServiceAutoConfiguration {

    // jwt.sign-type 只能用短横线，不能用驼峰

    public static final String JWT_SIGN_TYPE = "jwt.sign-type";

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "NONE")
    public JwtService noneSignJwtService(JwtProperties properties) {
        return new NoneSignJwtServiceImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "SECRET")
    public JwtService secretJwtService(JwtProperties properties) {
        return new SecretJwtServiceImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "PUBLIC_PRIVATE_KEY")
    public JwtService publicPrivateKeyJwtService(JwtProperties properties) {
        return new PublicPrivateKeyJwtServiceImpl(properties);
    }
}
