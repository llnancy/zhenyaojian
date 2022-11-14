package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.base.Preconditions;
import com.sunchaser.shushan.mojian.base.util.Optionals;
import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.PermissionOps;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
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

    public void createPermission(PermissionOps create) {
        Long parentId = create.getParentId();
        if (Objects.nonNull(parentId) && parentId != 0L) {
            PermissionEntity parentPermission = this.getById(parentId);
            Preconditions.checkNotNull(parentPermission, "父级菜单不存在");
            // 菜单下只能添加按钮
            Preconditions.checkArgument(!(PermissionTypeEnum.isMenu(parentPermission.getType()) && PermissionTypeEnum.isNotButton(create.getType())), "菜单类型下只能添加按钮类型");
            // 按钮下不能添加子菜单
            Preconditions.checkArgument(PermissionTypeEnum.isNotButton(parentPermission.getType()), "按钮下不能添加子菜单");
        }
        PermissionEntity permission = permissionMapstruct.convert(create);
        // 此处 parentId 不能被删除
        this.save(permission);
    }

    public void updatePermission(PermissionOps update) {
        Long id = update.getId();
        PermissionEntity exist = this.getById(id);
        LambdaQueryWrapper<PermissionEntity> wrapper = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getParentId, id);
        long childrenCount = this.count(wrapper);
        // 如果存在子菜单且修改了 permission 的类型 type
        // 做法一：将所有子菜单的 parentId 修改为 当前菜单的 parentId（需批量更新，业务上较为复杂）
        // 做法二：提示用户存在子菜单不允许修改菜单类型（√）
        Preconditions.checkArgument(!(SqlHelper.retBool(childrenCount) && !exist.getType().equals(update.getType())), "该菜单下存在子菜单，暂不允许修改菜单类型~");
        this.updateById(permissionMapstruct.convert(update));
    }

    public List<PermissionDetailTreeNode> permissionDetailTreeList() {
        return TreeBuilder.build(queryCurrentUserPermissions(), permissionMapstruct::convertToPermissionDetailTreeNode);
    }

    public List<PermissionDetailTreeNode> permissionsSearch(String name) {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .likeRight(PermissionEntity::getName, name);
        return TreeBuilder.build(queryCurrentUserPermissionsByCondition(condition), permissionMapstruct::convertToPermissionDetailTreeNode);
    }

    /**
     * 新建菜单时：
     * 1. 新建目录类型 -> 上级菜单只能选择目录（不能在菜单下建立目录）
     * 2. 新建菜单类型 -> 上级菜单只能选择目录（不能在菜单下建立菜单）
     * 3. 新建按钮类型 -> 上级菜单只能选择菜单（不能在目录下建立按钮）
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

    public void deletePermission(Long id) {
        LambdaQueryWrapper<PermissionEntity> wrapper = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getParentId, id);
        long count = this.count(wrapper);
        Preconditions.checkArgument(!SqlHelper.retBool(count), "该菜单下存在子菜单，不允许删除~");
        LambdaQueryWrapper<RolePermissionEntity> rpWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                .eq(RolePermissionEntity::getPermissionId, id);
        long rpCount = rolePermissionService.count(rpWrapper);
        Preconditions.checkArgument(!SqlHelper.retBool(rpCount), "该菜单已分配给相关角色，不允许删除~");
        this.removeById(id);
    }

    /**
     * 获取当前登录用户拥有的权限
     * userId -> roles -> permissions
     *
     * @return 权限列表
     */
    public List<PermissionEntity> queryCurrentUserPermissions() {
        return queryCurrentUserPermissionsByCondition();
    }

    public List<PermissionEntity> queryCurrentUserPermissionsByCondition() {
        return queryCurrentUserPermissionsByCondition(Wrappers.lambdaQuery());
    }

    public List<PermissionEntity> queryCurrentUserPermissionsByCondition(LambdaQueryWrapper<PermissionEntity> condition) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        condition = Optionals.of(condition, Wrappers.lambdaQuery());
        if (loginUser.isSuperAdmin()) {
            // 内部超级管理员查询所有权限
            return this.list(condition);
        }
        LambdaQueryWrapper<UserRoleEntity> urWrapper = Wrappers.<UserRoleEntity>lambdaQuery()
                .eq(UserRoleEntity::getUserId, loginUser.getUserId());
        // userId -> roles
        List<UserRoleEntity> userRoles = userRoleService.list(urWrapper);
        List<Long> roleIds = Streams.mapToList(userRoles, UserRoleEntity::getRoleId);
        LambdaQueryWrapper<RolePermissionEntity> rpWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                .in(RolePermissionEntity::getRoleId, roleIds);
        // roles -> permissions
        List<RolePermissionEntity> rolePermissions = rolePermissionService.list(rpWrapper);
        List<Long> permissionIds = Streams.mapToList(rolePermissions, RolePermissionEntity::getPermissionId);
        condition.in(PermissionEntity::getId, permissionIds);
        return this.list(condition);
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
