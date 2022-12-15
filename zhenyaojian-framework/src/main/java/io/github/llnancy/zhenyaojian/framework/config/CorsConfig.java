package io.github.llnancy.zhenyaojian.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * cors config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/15
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .maxAge(3600L); // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    }
}
