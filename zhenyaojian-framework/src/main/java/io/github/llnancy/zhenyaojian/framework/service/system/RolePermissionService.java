package io.github.llnancy.zhenyaojian.framework.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import io.github.llnancy.zhenyaojian.framework.model.request.AssignRolePermissionRequest;
import io.github.llnancy.zhenyaojian.framework.util.Streams;
import io.github.llnancy.zhenyaojian.system.repository.entity.RolePermissionEntity;
import io.github.llnancy.zhenyaojian.system.repository.mapper.RolePermissionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * role permission service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Service
@RequiredArgsConstructor
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements IService<RolePermissionEntity> {

    private final RoleService roleService;

    /**
     * assign role permission
     *
     * @param request {@link AssignRolePermissionRequest}
     */
    public void assignRolePermission(AssignRolePermissionRequest request) {
        Long roleId = request.getRoleId();
        roleService.verifyRoleIdExistence(roleId);
        Set<Long> newPermissionIds = request.getPermissionIds();
        Set<Long> oldPermissionIds = queryPermissionIdsByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(oldPermissionIds)) {
            Sets.SetView<Long> removePermissionIds = Sets.difference(oldPermissionIds, newPermissionIds);
            newPermissionIds = Sets.difference(newPermissionIds, oldPermissionIds);
            removeByRoleIdAndPermissionIds(roleId, removePermissionIds);
        }
        batchInsert(roleId, newPermissionIds);
    }

    /**
     * batch insert
     *
     * @param roleId        roleId {@link Long}
     * @param permissionIds set of permissionId
     */
    private void batchInsert(Long roleId, Set<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }
        List<RolePermissionEntity> list = Streams.mapToList(
                permissionIds,
                permissionId -> RolePermissionEntity.builder()
                        .roleId(roleId)
                        .permissionId(permissionId)
                        .build()
        );
        this.saveBatch(list);
    }

    private static LambdaQueryWrapper<RolePermissionEntity> selectPermissionIdEqRoleIdWrapper(Long roleId) {
        return Wrappers.<RolePermissionEntity>lambdaQuery()
                .select(RolePermissionEntity::getPermissionId)
                .eq(Objects.nonNull(roleId), RolePermissionEntity::getRoleId, roleId);
    }

    /**
     * remove by role id and permissionIds
     *
     * @param roleId        role id {@link Long}
     * @param permissionIds list of permissionId
     */
    private void removeByRoleIdAndPermissionIds(Long roleId, Set<Long> permissionIds) {
        if (CollectionUtils.isEmpty(permissionIds)) {
            return;
        }
        LambdaQueryWrapper<RolePermissionEntity> wrapper = selectPermissionIdEqRoleIdWrapper(roleId)
                .in(RolePermissionEntity::getPermissionId, permissionIds);
        this.remove(wrapper);
    }

    /**
     * query permissionIds by role id
     *
     * @param roleId role id
     * @return set of permissionId
     */
    public Set<Long> queryPermissionIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermissionEntity> wrapper = selectPermissionIdEqRoleIdWrapper(roleId);
        List<RolePermissionEntity> list = this.list(wrapper);
        return Streams.mapToSet(list, RolePermissionEntity::getPermissionId);
    }
}
