package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunchaser.shushan.mojian.base.enums.ResponseEnum;
import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.exception.ZyjBizException;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreatePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.framework.util.Streams;
import com.sunchaser.shushan.zhenyaojian.framework.util.TreeBuilder;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RolePermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * permission service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Service
@RequiredArgsConstructor
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionEntity> implements IService<PermissionEntity> {

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionMapstruct permissionMapstruct;

    public void createPermission(CreatePermissionRequest request) {
        Long parentId = request.getParentId();
        if (Objects.nonNull(parentId) && parentId != 0L) {
            PermissionEntity parentPermission = this.getById(parentId);
            if (Objects.isNull(parentPermission)) {
                throw new ZyjBizException(ResponseEnum.INVALID_PARAM);
            }
        }
        PermissionEntity permission = permissionMapstruct.convert(request);
        this.save(permission);
    }

    /**
     * 获取当前登录用户拥有的权限
     * userId -> roles -> permissions
     *
     * @return 权限列表
     */
    public List<PermissionEntity> queryCurrentUserPermissions() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser.isSuperAdmin()) {
            // 内部超级管理员查询所有权限
            return this.list();
        }
        LambdaQueryWrapper<UserRoleEntity> userRoleWrapper = Wrappers.<UserRoleEntity>lambdaQuery()
                .eq(UserRoleEntity::getUserId, loginUser.getUserId());
        // userId -> roles
        List<UserRoleEntity> userRoles = userRoleService.list(userRoleWrapper);
        List<Long> roleIds = Streams.mapToList(userRoles, UserRoleEntity::getRoleId);
        LambdaQueryWrapper<RolePermissionEntity> rolePermissionWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                .in(RolePermissionEntity::getRoleId, roleIds);
        // roles -> permissions
        List<RolePermissionEntity> rolePermissions = rolePermissionService.list(rolePermissionWrapper);
        List<Long> permissionIds = Streams.mapToList(rolePermissions, RolePermissionEntity::getPermissionId);
        LambdaQueryWrapper<PermissionEntity> permissionWrapper = Wrappers.<PermissionEntity>lambdaQuery()
                .in(PermissionEntity::getId, permissionIds);
        return this.list(permissionWrapper);
    }


    /**
     * 新建菜单时：
     * 1. 新建目录类型 -> 只能选择目录（不能在菜单下建立目录）
     * 2. 新建菜单类型 -> 只能选择目录（不能在菜单下建立菜单）
     * 3. 新建按钮类型 -> 只能选择菜单（不能在目录下建立按钮）
     *
     * @param filter 过滤条件
     * @return Permission Tree
     */
    public List<PermissionTreeNode> permissionsTree(String filter) {
        return TreeBuilder.build(
                queryCurrentUserPermissions(),
                permissionMapstruct::convertToPermissionTreeNode,
                (root, children) -> PermissionFilterEnum.valueOf(filter).filter(root)
        );
    }

    public enum PermissionFilterEnum {

        /**
         * 获取目录
         */
        FILTER_CATALOG() {
            @Override
            void filter(PermissionTreeNode treeNode) {
                if (PermissionTypeEnum.isNotCatalog(treeNode.getType())) {
                    treeNode.setDisabled(Boolean.TRUE);
                }
            }
        },

        /**
         * 获取菜单
         */
        FILTER_MENU() {
            @Override
            void filter(PermissionTreeNode treeNode) {
                if (PermissionTypeEnum.isNotMenu(treeNode.getType())) {
                    treeNode.setDisabled(Boolean.TRUE);
                }
            }
        };

        abstract void filter(PermissionTreeNode treeNode);
    }
}
