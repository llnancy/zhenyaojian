package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.mojian.log.entity.AccessLogBean;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.SystemLogResponse;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
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
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {SecurityUtils.class})
public interface SystemLogMapstruct {

    /**
     * convert {@link AccessLogBean} to {@link SystemLogEntity}
     *
     * @param accessLogBean {@link AccessLogBean}
     * @return {@link SystemLogEntity}
     */
    @Mapping(target = "status", expression = "java(accessLogBean.getStatus().ordinal())")
    @Mapping(target = "type", expression = "java(accessLogBean.getAccessType().name())")
    @Mapping(target = "userId", expression = "java(SecurityUtils.getLoginUserId().toString())")
    @Mapping(target = "userAccount", expression = "java(SecurityUtils.getLoginUsername())")
    SystemLogEntity convert(AccessLogBean accessLogBean);

    /**
     * convert {@link SystemLogEntity} to {@link SystemLogResponse}
     *
     * @param systemLogEntity {@link SystemLogEntity}
     * @return {@link SystemLogResponse}
     */
    SystemLogResponse convert(SystemLogEntity systemLogEntity);
}
