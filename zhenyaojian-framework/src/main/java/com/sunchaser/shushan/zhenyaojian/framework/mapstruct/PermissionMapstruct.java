package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.PermissionOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionTreeNode;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
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
     * convert {@link PermissionEntity} to {@link MenuTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link MenuTreeNode}
     */
    @Mapping(source = "name", target = "meta.title")
    @Mapping(source = "icon", target = "meta.icon")
    @Mapping(source = "permission", target = "meta.permission")
    MenuTreeNode convertToMenuTreeNode(PermissionEntity permissionEntity);

    /**
     * convert {@link PermissionEntity} to {@link PermissionTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link PermissionTreeNode}
     */
    PermissionTreeNode convertToPermissionTreeNode(PermissionEntity permissionEntity);

    /**
     * convert {@link PermissionEntity} to {@link PermissionDetailTreeNode}
     *
     * @param permissionEntity {@link PermissionEntity}
     * @return {@link PermissionDetailTreeNode}
     */
    PermissionDetailTreeNode convertToPermissionDetailTreeNode(PermissionEntity permissionEntity);
}
