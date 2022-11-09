package com.sunchaser.shushan.zhenyaojian.framework.mapstruct;

import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * menu mapstruct
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuMapstruct {

    /**
     * convert PermissionEntity to Menu
     *
     * @param permissionEntity PermissionEntity
     * @return Menu
     */
    @Mapping(source = "name", target = "meta.title")
    @Mapping(source = "icon", target = "meta.icon")
    @Mapping(source = "permission", target = "meta.permission")
    MenuTreeNode convert(PermissionEntity permissionEntity);
}
