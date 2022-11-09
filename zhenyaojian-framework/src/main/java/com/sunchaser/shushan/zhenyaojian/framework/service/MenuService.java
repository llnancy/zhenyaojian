package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.util.TreeBuilder;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * menu service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final PermissionService permissionService;

    private final PermissionMapstruct permissionMapstruct;

    /**
     * build menu tree node
     *
     * @return menu tree
     */
    public List<MenuTreeNode> menuInfo() {
        return new TreeBuilder<PermissionEntity, MenuTreeNode>() {
            @Override
            protected void postProcessAfterBuildTree(MenuTreeNode root, List<MenuTreeNode> children) {
                if (PermissionTypeEnum.isCatalog(root.getType())) {
                    MenuTreeNode first = children.get(0);
                    root.setRedirect(first.getPath());
                }
            }
        }.build(permissionService.queryCurrentUserPermissions(), permissionMapstruct::convertToMenuTreeNode);
    }

}
