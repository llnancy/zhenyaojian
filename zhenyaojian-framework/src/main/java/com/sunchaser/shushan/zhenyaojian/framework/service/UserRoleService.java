package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return this.list(eqUserIdWrapper(userId));
    }

    public void removeByUserId(Long userId) {
        this.remove(eqUserIdWrapper(userId));
    }

    public void removeByUserIdAndRoleIds(Long userId, Set<Long> roleIds) {
        LambdaQueryWrapper<UserRoleEntity> wrapper = eqUserIdWrapper(userId).in(UserRoleEntity::getRoleId, roleIds);
        this.remove(wrapper);
    }

    private LambdaQueryWrapper<UserRoleEntity> eqUserIdWrapper(Long userId) {
        return Wrappers.<UserRoleEntity>lambdaQuery()
                .eq(Objects.nonNull(userId), UserRoleEntity::getUserId, userId);
    }
}
