package io.github.llnancy.zhenyaojian.framework.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.llnancy.mojian.log.event.AccessLogAsyncEventListener;
import io.github.llnancy.mojian.log.event.AccessLogEventListener;
import io.github.llnancy.zhenyaojian.framework.mapstruct.SystemLogMapstruct;
import io.github.llnancy.zhenyaojian.system.repository.mapper.SystemLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AccessLog config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/30
 */
@Configuration
@EnableAsync
@RequiredArgsConstructor
public class AccessLogConfig implements AsyncConfigurer {

    private final SystemLogMapper systemLogMapper;

    private final SystemLogMapstruct systemLogMapstruct;

    @Bean
    public AccessLogEventListener accessLogEventListener() {
        return new AccessLogAsyncEventListener(accessLogBean -> systemLogMapper.insert(systemLogMapstruct.convert(accessLogBean)));
    }

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolExecutor(
                10,
                10,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder().setNameFormat("log-pool-%d").build()
        );
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
