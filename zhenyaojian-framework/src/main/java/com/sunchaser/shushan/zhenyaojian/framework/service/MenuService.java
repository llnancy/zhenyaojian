package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.Menu;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.security.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final UserRoleService userRoleService;

    /**
     * TODO
     *
     * @return menu tree
     */
    public List<Menu> menuInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserEntity userEntity = loginUser.getUserEntity();
        Long userId = userEntity.getId();
        List<Menu> menuTree = null;
        if (userId == 1L) {
            // 内部超级管理员拥有全部权限
            List<PermissionEntity> all = permissionService.list();
            List<Menu> menuList = all.stream()
                    .map(el -> {
                                Menu.Meta meta = Menu.Meta.builder()
                                        .title(el.getName())
                                        .icon(el.getIcon())
                                        .permission(el.getPermission())
                                        .build();
                                return Menu.builder()
                                        .path(el.getPath())
                                        .id(el.getId())
                                        .parentId(el.getParentId())
                                        .type(el.getType())
                                        .component(el.getComponent())
                                        .meta(meta)
                                        .build();
                            }
                    ).collect(Collectors.toList());

            Map<Long, List<Menu>> parentIdMenuMap = menuList.stream()
                    .collect(Collectors.groupingBy(Menu::getParentId));

            List<Menu> root = menuList.stream()
                    .filter(el -> el.getParentId() == 0L)
                    .collect(Collectors.toList());

            menuTree = buildMenuTree(parentIdMenuMap, root);
        } else {
            LambdaQueryWrapper<UserRoleEntity> queryWrapper = Wrappers.<UserRoleEntity>lambdaQuery()
                    .eq(UserRoleEntity::getUserId, userId);
            List<UserRoleEntity> userRoleEntityList = userRoleService.list(queryWrapper);
        }
        return menuTree;
    }

    private static List<Menu> buildMenuTree(Map<Long, List<Menu>> parentIdMenuMap, List<Menu> root) {
        return root.stream()
                .peek(el -> {
                    Long id = el.getId();
                    List<Menu> children = parentIdMenuMap.get(id);
                    if (CollectionUtils.isNotEmpty(children)) {
                        buildMenuTree(parentIdMenuMap, children);
                        el.setChildren(children);
                        Integer type = el.getType();
                        if (PermissionTypeEnum.isCatalog(type)) {
                            Menu first = children.get(0);
                            el.setRedirect(first.getPath());
                        }
                    }
                })
                .collect(Collectors.toList());
    }
}
