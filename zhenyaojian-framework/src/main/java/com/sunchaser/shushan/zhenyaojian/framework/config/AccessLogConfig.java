package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import com.sunchaser.shushan.mojian.log.event.AccessLogEventListener;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.SystemLogMapstruct;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.SystemLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/30
 */
@Configuration
@RequiredArgsConstructor
public class AccessLogConfig {

    private final SystemLogMapper systemLogMapper;

    private final SystemLogMapstruct systemLogMapstruct;

    @Bean
    public AccessLogEventListener accessLogEventListener() {
        return new AccessLogEventListener(accessLogBean -> systemLogMapper.insert(systemLogMapstruct.convert(accessLogBean)));
    }
}
