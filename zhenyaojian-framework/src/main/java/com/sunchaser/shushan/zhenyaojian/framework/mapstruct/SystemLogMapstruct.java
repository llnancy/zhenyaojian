package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.SystemLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * system log mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/30
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SystemLogMapstruct {

    /**
     * convert {@link AccessLogBean} to {@link SystemLogEntity}
     *
     * @param accessLogBean {@link AccessLogBean}
     * @return {@link SystemLogEntity}
     */
    @Mapping(target = "status", expression = "java(accessLogBean.getStatus().ordinal())")
    SystemLogEntity convert(AccessLogBean accessLogBean);
}
