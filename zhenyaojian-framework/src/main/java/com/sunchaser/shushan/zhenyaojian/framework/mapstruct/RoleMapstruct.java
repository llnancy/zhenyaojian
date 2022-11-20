package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.RoleOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleItemInfo;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * role mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/20
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapstruct {

    /**
     * convert {@link RoleOpsCommand} to {@link RoleEntity}
     *
     * @param command {@link RoleOpsCommand}
     * @return {@link RoleEntity}
     */
    RoleEntity convert(RoleOpsCommand command);

    /**
     * convert {@link RoleEntity} to {@link RoleItemInfo}
     *
     * @param roleEntity {@link RoleEntity}
     * @return {@link RoleItemInfo}
     */
    RoleItemInfo convert(RoleEntity roleEntity);
}
