package io.github.llnancy.zhenyaojian.framework.mapstruct;

import io.github.llnancy.zhenyaojian.framework.model.request.RoleOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.response.RoleInfoResponse;
import io.github.llnancy.zhenyaojian.system.repository.entity.RoleEntity;
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
     * convert {@link RoleEntity} to {@link RoleInfoResponse}
     *
     * @param roleEntity {@link RoleEntity}
     * @return {@link RoleInfoResponse}
     */
    RoleInfoResponse convert(RoleEntity roleEntity);
}
