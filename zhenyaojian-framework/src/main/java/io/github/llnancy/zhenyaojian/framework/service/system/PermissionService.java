package io.github.llnancy.zhenyaojian.framework.service.system;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.base.Preconditions;
import io.github.llnancy.zhenyaojian.framework.enums.PermissionTypeEnum;
import io.github.llnancy.zhenyaojian.framework.mapstruct.PermissionMapstruct;
import io.github.llnancy.zhenyaojian.framework.model.request.PermissionOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionBaseTreeNode;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
import io.github.llnancy.zhenyaojian.framework.model.response.RouterTreeNode;
import io.github.llnancy.zhenyaojian.framework.util.SecurityUtils;
import io.github.llnancy.zhenyaojian.framework.util.Streams;
import io.github.llnancy.zhenyaojian.framework.util.TreeBuilder;
import io.github.llnancy.zhenyaojian.system.repository.entity.PermissionEntity;
import io.github.llnancy.zhenyaojian.system.repository.entity.RolePermissionEntity;
import io.github.llnancy.zhenyaojian.system.repository.mapper.PermissionMapper;
import io.github.llnancy.mojian.base.enums.TableStatusFieldEnum;
import io.github.llnancy.mojian.base.util.Optionals;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

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

    private static final BiConsumer<RouterTreeNode, List<RouterTreeNode>> MENU_TREE_BI_CONSUMER = (root, children) -> {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        if (PermissionTypeEnum.isCatalog(root.getType())) {
            RouterTreeNode first = children.get(0);
            root.setRedirect(first.getPath());
        }
    };

    /**
     * build menu tree node
     *
     * @return list of {@link RouterTreeNode}
     */
    public List<RouterTreeNode> routerInfo() {
        return TreeBuilder.build(
                queryCurrentUserPermissions(),
                permissionMapstruct::convertToRouterTreeNode,
                MENU_TREE_BI_CONSUMER
        );
    }

    /**
     * create {@link PermissionEntity}
     *
     * @param command {@link PermissionOpsCommand}
     * @return permission ID {@link Long}
     */
    public Long createPermission(PermissionOpsCommand command) {
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
        return permission.getId();
    }

    /**
     * 校验路由地址唯一性
     *
     * @param command {@link PermissionOpsCommand}
     */
    private void verifyPathUniqueness(PermissionOpsCommand command) {
        String path = command.getPath();
        if (PermissionTypeEnum.isButton(command.getType())) {
            return;
        }
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

    /**
     * 校验权限名称唯一性
     *
     * @param command {@link PermissionOpsCommand}
     */
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

    /**
     * update {@link PermissionEntity}
     *
     * @param command {@link PermissionOpsCommand}
     */
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
                .set(Objects.nonNull(command.getIcon()), PermissionEntity::getIcon, permission.getIcon())
                .set(Objects.nonNull(command.getPath()), PermissionEntity::getPath, permission.getPath())
                .set(Objects.nonNull(command.getComponent()), PermissionEntity::getComponent, permission.getComponent())
                .set(Objects.nonNull(command.getPermission()), PermissionEntity::getPermission, permission.getPermission())
                .set(Objects.nonNull(command.getSortValue()), PermissionEntity::getSortValue, permission.getSortValue())
                .set(Objects.nonNull(command.getStatus()), PermissionEntity::getStatus, permission.getStatus())
                .eq(PermissionEntity::getId, id);
        this.update(updateWrapper);
    }

    /**
     * 根据条件查询全部菜单列表
     *
     * @param name 菜单名称
     * @return list of {@link PermissionDetailTreeNode}
     */
    public List<PermissionDetailTreeNode> permissionDetailTreeList(String name) {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .likeRight(StringUtils.isNotBlank(name), PermissionEntity::getName, name);
        return TreeBuilder.build(
                queryCurrentUserPermissionsByCondition(condition),
                permissionMapstruct::convertToPermissionDetailTreeNode
        );
    }

    /**
     * 新建菜单时：
     * 1. 新建目录类型 -> 上级菜单只能选择目录（不能在菜单下建立目录）
     * 2. 新建菜单类型 -> 上级菜单只能选择目录（不能在菜单下建立菜单）
     * 3. 新建按钮类型 -> 上级菜单只能选择菜单（不能在目录下建立按钮）
     * 且不能选择已隐藏的菜单
     *
     * @param filter 过滤条件
     * @return list of {@link PermissionBaseTreeNode}
     */
    public List<PermissionBaseTreeNode> permissionsTree(String filter) {
        return TreeBuilder.build(
                queryCurrentUserPermissions(),
                permissionMapstruct::convertToPermissionBaseTreeNode,
                (root, children) -> PermissionFilterEnum.valueOf(filter).filter(root)
        );
    }

    /**
     * delete permission by id
     *
     * @param id permission id {@link Long}
     */
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
     * 获取当前登录用户拥有的权限（不包含已隐藏的菜单，不包含按钮权限）
     *
     * @return 权限列表
     */
    public List<PermissionEntity> queryCurrentUserPermissions() {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getStatus, TableStatusFieldEnum.NORMAL.ordinal())
                .in(PermissionEntity::getType, PermissionTypeEnum.CATALOG.ordinal(), PermissionTypeEnum.MENU.ordinal());
        return queryCurrentUserPermissionsByCondition(condition);
    }

    /**
     * 根据条件查询当前登录用户拥有的权限
     *
     * @param condition query condition {@link LambdaQueryWrapper}
     * @return list of {@link PermissionEntity}
     */
    private List<PermissionEntity> queryCurrentUserPermissionsByCondition(LambdaQueryWrapper<PermissionEntity> condition) {
        Long userId = SecurityUtils.getLoginUserId();
        return queryPermissionsByUserIdAndRoleIdsAndCondition(userId, userRoleService.queryRoleIdsByUserId(userId), condition);
    }

    /**
     * 查询指定用户拥有的权限
     *
     * @param userId user id
     * @return list of {@link PermissionEntity}
     */
    public List<PermissionEntity> queryNormalPermissionsByUserIdAndRoleIds(Long userId, Set<Long> roleIds) {
        LambdaQueryWrapper<PermissionEntity> condition = Wrappers.<PermissionEntity>lambdaQuery()
                .eq(PermissionEntity::getStatus, TableStatusFieldEnum.NORMAL.ordinal());
        return queryPermissionsByUserIdAndRoleIdsAndCondition(userId, roleIds, condition);
    }

    /**
     * 根据条件查询指定用户所拥有的权限
     *
     * @param userId    user id
     * @param roleIds   user's role id set
     * @param condition query condition {@link LambdaQueryWrapper}
     * @return list of {@link PermissionEntity}
     */
    public List<PermissionEntity> queryPermissionsByUserIdAndRoleIdsAndCondition(Long userId, Set<Long> roleIds, LambdaQueryWrapper<PermissionEntity> condition) {
        boolean notSuperAdmin = SecurityUtils.isNotSuperAdmin(userId);
        if (notSuperAdmin && CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        condition = Optionals.of(condition, Wrappers.lambdaQuery());
        if (notSuperAdmin) {
            LambdaQueryWrapper<RolePermissionEntity> rpWrapper = Wrappers.<RolePermissionEntity>lambdaQuery()
                    .in(RolePermissionEntity::getRoleId, roleIds);
            // roleIds -> permissions
            List<RolePermissionEntity> rolePermissions = rolePermissionService.list(rpWrapper);
            List<Long> permissionIds = Streams.mapToList(rolePermissions, RolePermissionEntity::getPermissionId);
            if (CollectionUtils.isEmpty(permissionIds)) {
                return Collections.emptyList();
            }
            condition.in(PermissionEntity::getId, permissionIds);
        }
        condition.orderByAsc(PermissionEntity::getSortValue);
        return this.list(condition);
    }

    /**
     * permission filter enum
     */
    public enum PermissionFilterEnum {

        /**
         * 获取全部
         */
        FILTER_NONE,

        /**
         * 获取目录
         */
        FILTER_CATALOG() {
            @Override
            void filter(PermissionBaseTreeNode treeNode) {
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
            void filter(PermissionBaseTreeNode treeNode) {
                if (PermissionTypeEnum.isNotMenu(treeNode.getType())) {
                    treeNode.setDisabled(Boolean.TRUE);
                }
            }
        };

        /**
         * filter strategy
         *
         * @param treeNode {@link PermissionBaseTreeNode}
         */
        void filter(PermissionBaseTreeNode treeNode) {
        }
    }
}
