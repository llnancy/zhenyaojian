package io.github.llnancy.zhenyaojian.framework.mapstruct;

import io.github.llnancy.zhenyaojian.framework.model.request.PermissionOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionBaseTreeNode;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
import io.github.llnancy.zhenyaojian.framework.model.response.RouterTreeNode;
import io.github.llnancy.zhenyaojian.system.repository.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     * convert {@link PermissionOpsCommand} to {@link PermissionEntity}
     *
     * @param command {@link PermissionOpsCommand}
     * @return {@link PermissionEntity}
     */
    PermissionEntity convert(PermissionOpsCommand command);

    /**
     * convert {@link PermissionEntity} to {@link RouterTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link RouterTreeNode}
     */
    @Mapping(source = "name", target = "meta.title")
    @Mapping(source = "icon", target = "meta.icon")
    @Mapping(source = "permission", target = "meta.permission")
    RouterTreeNode convertToRouterTreeNode(PermissionEntity permissionEntity);

    /**
     * convert {@link PermissionEntity} to {@link PermissionBaseTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link PermissionBaseTreeNode}
     */
    PermissionBaseTreeNode convertToPermissionBaseTreeNode(PermissionEntity permissionEntity);

    /**
     * convert {@link PermissionEntity} to {@link PermissionDetailTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link PermissionDetailTreeNode}
     */
    PermissionDetailTreeNode convertToPermissionDetailTreeNode(PermissionEntity permissionEntity);
}
