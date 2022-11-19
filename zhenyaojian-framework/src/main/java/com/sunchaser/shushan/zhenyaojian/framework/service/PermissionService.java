package com.sunchaser.shushan.zhenyaojian.framework.service;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.base.Preconditions;
import com.sunchaser.shushan.mojian.base.util.Optionals;
import com.sunchaser.shushan.zhenyaojian.framework.enums.PermissionTypeEnum;
import com.sunchaser.shushan.zhenyaojian.framework.enums.TableStatusFieldEnum;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.PermissionOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.framework.util.Streams;
import com.sunchaser.shushan.zhenyaojian.framework.util.TreeBuilder;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RolePermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

    public void createPermission(PermissionOpsCommand command) {
        Long parentId = command.getParentId();
        if (Objects.nonNull(parentId) && parentId != 0L) {
            PermissionEntity parentPermission = this.getById(parentId);
            Preconditions.checkNotNull(parentPermission, "父级菜单不存在");
            // 菜单下只能添加按钮
            Preconditions.checkArgument(!(PermissionTypeEnum.isMenu(parentPermission.getType()) && PermissionTypeEnum.isNotButton(command.getType())), "菜单类型下只能添加按钮类型");
            // 按钮下不能添加子菜单
            Preconditions.checkArgument(PermissionTypeEnum.isNotButton(parentPermission.getType()), "按钮下不能添加子菜单");
        }
        // 校验权限名称唯一性
        verifyNameUniqueness(command);
        // 校验路由地址唯一性
        verifyPathUniqueness(command);
        PermissionEntity permission = permissionMapstruct.convert(command);
        // 此处 parentId 不能被删除
        this.save(permission);
    }

    private void verifyPathUniqueness(PermissionOpsCommand command) {
        String path = command.getPath();
        if (ReUtil.isMatch(PatternPool.URL_HTTP, path)) {
            return;
        }
        // 不是网址则校验路由地址的唯一性
        LambdaQueryWrapper<PermissionEntity> wrapper = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getPath, path);
        PermissionEntity exist = this.getOne(wrapper);
        // 修改时如果查询到的 exist.id 和传入的 ops.id 不相等，说明路由地址已存在，进行相应错误提示；否则 exist 就是待修改记录本身，允许修改。
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "路由地址为[" + path + "]的菜单已存在");
    }

    private void verifyNameUniqueness(PermissionOpsCommand command) {
        Long parentId = command.getParentId();
        String name = command.getName();
        LambdaQueryWrapper<PermissionEntity> wrapper = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getName, name)
                .eq(PermissionEntity::getParentId, parentId);
        PermissionEntity exist = this.getOne(wrapper);
        // 修改时如果查询到的 exist.id 和传入的 ops.id 不相等，说明菜单名称已存在，进行相应错误提示；否则 exist 就是待修改记录本身，允许修改。
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "菜单名称[" + name + "]已存在");
    }

    public void updatePermission(PermissionOpsCommand command) {
        Long id = command.getId();
        PermissionEntity exist = this.getById(id);
        long childrenCount = this.count(
                Wrappers.<PermissionEntity>lambdaQuery()
                        .eq(PermissionEntity::getParentId, id)
        );
        // 如果存在子菜单且修改了 permission 的类型 type
        // 做法一：将所有子菜单的 parentId 修改为 当前菜单的 parentId（需批量更新，业务上较为复杂）
        // 做法二：提示用户存在子菜单不允许修改菜单类型（√）
        Preconditions.checkArgument(!(SqlHelper.retBool(childrenCount) && ObjectUtils.notEqual(exist.getType(), command.getType())), "该菜单下存在子菜单，暂不允许修改菜单类型。");
        // 校验权限名称唯一性
        verifyNameUniqueness(command);
        // 校验路由地址唯一性
        verifyPathUniqueness(command);
        PermissionEntity permission = permissionMapstruct.convert(command);
        // 动态 SQL
        LambdaUpdateWrapper<PermissionEntity> updateWrapper = Wrappers.<PermissionEntity>lambdaUpdate()
                .set(StringUtils.isNotBlank(command.getName()), PermissionEntity::getName, permission.getName())
                .set(Objects.nonNull(command.getParentId()), PermissionEntity::getParentId, permission.getParentId())
                .set(Objects.nonNull(command.getType()), PermissionEntity::getType, permission.getType())
                .set(StringUtils.isNotBlank(command.getIcon()), PermissionEntity::getIcon, permission.getIcon())
                .set(StringUtils.isNotBlank(command.getPath()), PermissionEntity::getPath, permission.getPath())
                .set(StringUtils.isNotBlank(command.getComponent()), PermissionEntity::getComponent, permission.getComponent())
                .set(StringUtils.isNotBlank(command.getPermission()), PermissionEntity::getPermission, permission.getPermission())
                .set(Objects.nonNull(command.getSortValue()), PermissionEntity::getSortValue, permission.getSortValue())
                .set(Objects.nonNull(command.getStatus()), PermissionEntity::getStatus, permission.getStatus())
                .eq(PermissionEntity::getId, id);
        this.update(updateWrapper);
    }

    public List<PermissionDetailTreeNode> permissionDetailTreeList(String name) {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .likeRight(StringUtils.isNotBlank(name), PermissionEntity::getName, name);
        return TreeBuilder.build(queryCurrentUserPermissionsByCondition(condition), permissionMapstruct::convertToPermissionDetailTreeNode);
    }

    /**
     * 新建菜单时：
     * 1. 新建目录类型 -> 上级菜单只能选择目录（不能在菜单下建立目录）
     * 2. 新建菜单类型 -> 上级菜单只能选择目录（不能在菜单下建立菜单）
     * 3. 新建按钮类型 -> 上级菜单只能选择菜单（不能在目录下建立按钮）
     * 且不能选择已隐藏的菜单
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
        Preconditions.checkArgument(!SqlHelper.retBool(count), "该菜单下存在子菜单，不允许删除");
        LambdaQueryWrapper<RolePermissionEntity> rpWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                .eq(RolePermissionEntity::getPermissionId, id);
        long rpCount = rolePermissionService.count(rpWrapper);
        Preconditions.checkArgument(!SqlHelper.retBool(rpCount), "该菜单已分配给相关角色，不允许删除");
        this.removeById(id);
    }

    /**
     * 获取当前登录用户拥有的权限
     * userId -> roles -> permissions
     *
     * @return 权限列表
     */
    public List<PermissionEntity> queryCurrentUserPermissions() {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getStatus, TableStatusFieldEnum.NORMAL.ordinal());
        return queryCurrentUserPermissionsByCondition(condition);
    }

    public List<PermissionEntity> queryCurrentUserPermissionsByCondition(LambdaQueryWrapper<PermissionEntity> condition) {
        Long userId = SecurityUtils.getLoginUserId();
        condition = Optionals.of(condition, Wrappers.lambdaQuery());
        if (SecurityUtils.isSuperAdmin(userId)) {
            // 内部超级管理员查询所有权限
            return this.list(condition);
        }
        LambdaQueryWrapper<UserRoleEntity> urWrapper = Wrappers.<UserRoleEntity>lambdaQuery()
                .eq(UserRoleEntity::getUserId, userId);
        // userId -> roles
        List<UserRoleEntity> userRoles = userRoleService.list(urWrapper);
        List<Long> roleIds = Streams.mapToList(userRoles, UserRoleEntity::getRoleId);
        LambdaQueryWrapper<RolePermissionEntity> rpWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                .in(CollectionUtils.isNotEmpty(roleIds), RolePermissionEntity::getRoleId, roleIds);
        // roles -> permissions
        List<RolePermissionEntity> rolePermissions = rolePermissionService.list(rpWrapper);
        List<Long> permissionIds = Streams.mapToList(rolePermissions, RolePermissionEntity::getPermissionId);
        condition.in(CollectionUtils.isNotEmpty(permissionIds), PermissionEntity::getId, permissionIds);
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
