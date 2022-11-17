package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/17
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRoleMapstruct {

    // @Mapping(source = "userId", target = "userId")
    // @Mapping(source = "roleIds", target = "roleId")
    // List<UserRoleEntity> convert(Long userId, List<Long> roleIds);
}
