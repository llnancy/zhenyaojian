package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreatePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * permission mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapstruct {

    /**
     * convert CreatePermissionRequest to PermissionEntity
     *
     * @param request CreatePermissionRequest
     * @return PermissionEntity
     */
    PermissionEntity convert(CreatePermissionRequest request);
}
