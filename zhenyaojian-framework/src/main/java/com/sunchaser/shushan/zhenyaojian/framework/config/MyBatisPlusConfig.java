package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 插件配置
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/13
 */
@Configuration
@RequiredArgsConstructor
@MapperScan("com.sunchaser.shushan.zhenyaojian.system.repository.mapper")
public class MyBatisPlusConfig implements MetaObjectHandler {

    /**
     * 配置分页插件
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页查询
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    /**
     * updateTime field constant
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * updateUser field constant
     */
    private static final String UPDATE_USER = "updateUser";

    /**
     * 配置执行 insert 语句时的字段自动填充
     *
     * @param metaObject {@link MetaObject}
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createUser", String.class, SecurityUtils.getLoginUsername());
        this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, UPDATE_USER, String.class, SecurityUtils.getLoginUsername());
    }

    /**
     * 配置执行 update 语句时的字段自动填充
     *
     * @param metaObject {@link MetaObject}
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, UPDATE_USER, String.class, SecurityUtils.getLoginUsername());
    }
}
