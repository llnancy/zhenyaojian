package com.sunchaser.shushan.zhenyaojian.framework.service.jwt.impl;

import com.sunchaser.shushan.zhenyaojian.framework.config.property.JwtProperties;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;

/**
 * jwt abstract implementation
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@RequiredArgsConstructor
public abstract class AbstractJwtService implements JwtService {

    protected final JwtProperties jwtProperties;
}
