package com.sunchaser.shushan.zhenyaojian.framework.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.AssignUserRoleRequest;
import com.sunchaser.shushan.zhenyaojian.framework.util.Streams;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * user role service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/7
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements IService<UserRoleEntity> {

    private final UserService userService;

    public void batchInsert(Long userId, Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        List<UserRoleEntity> list = Streams.mapToList(
                roleIds,
                roleId -> UserRoleEntity.builder()
                        .userId(userId)
                        .roleId(roleId)
                        .build()
        );
        this.saveBatch(list);
    }

    public List<UserRoleEntity> listByUserId(Long userId) {
        return this.list(selectRoleIdEqUserIdWrapper(userId));
    }

    public void removeByUserIdAndRoleIds(Long userId, Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        LambdaQueryWrapper<UserRoleEntity> wrapper = selectRoleIdEqUserIdWrapper(userId)
                .in(UserRoleEntity::getRoleId, roleIds);
        this.remove(wrapper);
    }

    private LambdaQueryWrapper<UserRoleEntity> selectRoleIdEqUserIdWrapper(Long userId) {
        return Wrappers.<UserRoleEntity>lambdaQuery()
                .select(UserRoleEntity::getRoleId)
                .eq(Objects.nonNull(userId), UserRoleEntity::getUserId, userId);
    }

    public void assignUserRole(AssignUserRoleRequest request) {
        Long userId = request.getUserId();
        userService.verifyUserIdExistence(userId);
        Set<Long> newRoleIds = request.getRoleIds();
        userService.verifyRoleIdsExistence(newRoleIds);
        Set<Long> oldRoleIds = queryRoleIdsByUserId(userId);
        if (CollectionUtils.isNotEmpty(oldRoleIds)) {
            Sets.SetView<Long> removeRoleIds = Sets.difference(oldRoleIds, newRoleIds);
            newRoleIds = Sets.difference(newRoleIds, oldRoleIds);
            removeByUserIdAndRoleIds(userId, removeRoleIds);
        }
        batchInsert(userId, newRoleIds);
    }

    public Set<Long> queryRoleIdsByUserId(Long userId) {
        List<UserRoleEntity> list = listByUserId(userId);
        return Streams.mapToSet(list, UserRoleEntity::getRoleId);
    }
}
