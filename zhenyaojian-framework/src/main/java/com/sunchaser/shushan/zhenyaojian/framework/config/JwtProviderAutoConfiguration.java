package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtProvider;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.NoneSignJwtProviderImpl;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.PublicPrivateKeyJwtProviderImpl;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.SecretJwtProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JwtProvider configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@Configuration
@EnableConfigurationProperties({JwtProperties.class})
@ConditionalOnProperty(prefix = JwtProperties.ZYJ_JWT, value = "enabled", matchIfMissing = true)
public class JwtProviderAutoConfiguration {

    /**
     * zyj.jwt.sign-type 中只能用短横线，不能用驼峰
     */
    public static final String JWT_SIGN_TYPE = JwtProperties.ZYJ_JWT + ".sign-type";

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "NONE")
    public JwtProvider noneSignJwtService(JwtProperties properties) {
        return new NoneSignJwtProviderImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "SECRET")
    public JwtProvider secretJwtService(JwtProperties properties) {
        return new SecretJwtProviderImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = JWT_SIGN_TYPE, havingValue = "PUBLIC_PRIVATE_KEY")
    public JwtProvider publicPrivateKeyJwtService(JwtProperties properties) {
        return new PublicPrivateKeyJwtProviderImpl(properties);
    }
}
