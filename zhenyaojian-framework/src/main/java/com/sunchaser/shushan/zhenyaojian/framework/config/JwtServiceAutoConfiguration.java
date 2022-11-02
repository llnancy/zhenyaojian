package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtService;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.PublicPrivateKeyJwtServiceImpl;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl.SecretJwtServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * JwtService configuration
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({JwtProperties.class})
@ConditionalOnProperty(prefix = "jwt", value = "enabled", matchIfMissing = true)
public class JwtServiceAutoConfiguration {

    // jwt.sign-type 只能用短横线，不能用驼峰

    @Bean
    @ConditionalOnProperty(name = "jwt.sign-type", havingValue = "SECRET")
    public JwtService secretJwtService(JwtProperties properties) {
        return new SecretJwtServiceImpl(properties);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.sign-type", havingValue = "PUBLIC_PRIVATE_KEY")
    public JwtService publicPrivateKeyJwtService(JwtProperties properties) {
        return new PublicPrivateKeyJwtServiceImpl(properties);
    }
}
