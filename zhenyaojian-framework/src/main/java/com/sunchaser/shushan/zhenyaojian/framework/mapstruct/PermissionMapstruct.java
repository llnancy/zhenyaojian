package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.PermissionOps;
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
     * convert PermissionOps to PermissionEntity
     *
     * @param ops PermissionOps
     * @return PermissionEntity
     */
    PermissionEntity convert(PermissionOps ops);

    /**
     * convert PermissionEntity to MenuTreeNode
     *
     * @param permissionEntity PermissionEntity
     * @return MenuTreeNode
     */
    @Mapping(source = "name", target = "meta.title")
    @Mapping(source = "icon", target = "meta.icon")
    @Mapping(source = "permission", target = "meta.permission")
    MenuTreeNode convertToMenuTreeNode(PermissionEntity permissionEntity);

    /**
     * convert PermissionEntity to PermissionTreeNode
     *
     * @param permissionEntity PermissionEntity
     * @return PermissionTreeNode
     */
    PermissionTreeNode convertToPermissionTreeNode(PermissionEntity permissionEntity);

    /**
     * convert PermissionEntity to PermissionDetailTreeNode
     *
     * @param permissionEntity PermissionEntity
     * @return PermissionDetailTreeNode
     */
    PermissionDetailTreeNode convertToPermissionDetailTreeNode(PermissionEntity permissionEntity);
}
