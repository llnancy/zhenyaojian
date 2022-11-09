package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreatePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
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
     * convert CreatePermissionRequest to PermissionEntity
     *
     * @param request CreatePermissionRequest
     * @return PermissionEntity
     */
    PermissionEntity convert(CreatePermissionRequest request);

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
    PermissionTreeNode convert(PermissionEntity permissionEntity);
}
