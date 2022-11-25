package com.sunchaser.shushan.zhenyaojian.admin;

import com.sunchaser.shushan.mojian.web.annotation.EnableMjJackson;
import com.sunchaser.shushan.mojian.web.annotation.EnableValidatorFailFast;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot 启动类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/1
 */
@SpringBootApplication
@ComponentScan("com.sunchaser.shushan.zhenyaojian")
@EnableMjJackson
@EnableValidatorFailFast
@EnableAdminServer
public class ZhenYaoJianApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZhenYaoJianApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
