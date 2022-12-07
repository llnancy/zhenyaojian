package io.github.llnancy.zhenyaojian.framework.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * zyj system properties
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/25
 */
@Getter
@Setter
@ConfigurationProperties(prefix = ZyjFrameworkProperties.ZYJ)
@Configuration
public class ZyjFrameworkProperties {

    public static final String ZYJ = "zyj";

    /**
     * 重置用户密码时的默认密码
     */
    private String defaultPassword;

    /**
     * jwt 配置
     */
    @NestedConfigurationProperty
    private JwtProperties jwt;
}
