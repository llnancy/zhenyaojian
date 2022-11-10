package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.util.TreeBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;

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
        return TreeBuilder.build(
                permissionService.queryCurrentUserPermissions(),
                permissionMapstruct::convertToMenuTreeNode,
                MENU_TREE_BI_CONSUMER
        );
    }

    private static final BiConsumer<MenuTreeNode, List<MenuTreeNode>> MENU_TREE_BI_CONSUMER = (root, children) -> {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        if (PermissionTypeEnum.isCatalog(root.getType())) {
            MenuTreeNode first = children.get(0);
            root.setRedirect(first.getPath());
        }
    };
}
