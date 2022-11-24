package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.sunchaser.shushan.mojian.base.entity.response.MultiPageResponse;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.RoleMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.RoleOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.RolePageRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleItemInfo;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * role service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/7
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> implements IService<RoleEntity> {

    private final RoleMapstruct roleMapstruct;

    /**
     * create {@link RoleEntity}
     *
     * @param command {@link RoleOpsCommand}
     * @return role ID {@link Long}
     */
    public Long createRole(RoleOpsCommand command) {
        verifyRoleNameUniqueness(command);
        RoleEntity role = roleMapstruct.convert(command);
        // todo code?
        role.setCode(StringUtils.EMPTY);
        this.save(role);
        return role.getId();
    }

    /**
     * 校验角色名称唯一性
     *
     * @param command {@link RoleOpsCommand}
     */
    private void verifyRoleNameUniqueness(RoleOpsCommand command) {
        String name = command.getName();
        if (StringUtils.isBlank(name)) {
            return;
        }
        LambdaQueryWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaQuery()
                .eq(RoleEntity::getName, name);
        RoleEntity exist = this.getOne(wrapper);
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "角色名称[" + name + "]已存在");
    }

    /**
     * 校验角色存在性
     *
     * @param roleId role id {@link Long}
     */
    public void verifyRoleIdExistence(Long roleId) {
        if (Objects.isNull(roleId) || roleId < 0L) {
            return;
        }
        RoleEntity exist = this.getById(roleId);
        Preconditions.checkArgument(Objects.nonNull(exist), "角色不存在");
    }

    /**
     * update {@link RoleEntity}
     *
     * @param command {@link RoleOpsCommand}
     */
    public void updateRole(RoleOpsCommand command) {
        Long id = command.getId();
        verifyOpsLegality(id);
        verifyRoleNameUniqueness(command);
        RoleEntity role = roleMapstruct.convert(command);
        LambdaUpdateWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaUpdate()
                .set(StringUtils.isNotBlank(command.getName()), RoleEntity::getName, role.getName())
                .set(Objects.nonNull(command.getSortValue()), RoleEntity::getSortValue, role.getSortValue())
                .set(Objects.nonNull(command.getStatus()), RoleEntity::getStatus, role.getStatus())
                .eq(RoleEntity::getId, id);
        this.update(wrapper);
    }

    /**
     * 校验操作合法性：禁止操作超级管理员角色
     *
     * @param id role id {@link Long}
     */
    private static void verifyOpsLegality(Long id) {
        Preconditions.checkArgument(SecurityUtils.isNotSuperAdmin(id), "不允许操作超级管理员角色");
    }

    /**
     * Paging query roles.
     *
     * @param request {@link RolePageRequest}
     * @return paging data of {@link RoleItemInfo}
     */
    public MultiPageResponse<RoleItemInfo> roles(RolePageRequest request) {
        String name = request.getName();
        LambdaQueryWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaQuery()
                .likeRight(StringUtils.isNotBlank(name), RoleEntity::getName, name)
                .orderByAsc(RoleEntity::getSortValue);
        Page<RoleEntity> page = Page.of(request.getPageNo(), request.getPageSize());
        Page<RoleEntity> list = this.getBaseMapper().selectPage(page, wrapper);
        return MultiPageResponse.success(list, roleMapstruct::convert);
    }

    /**
     * delete role by id.
     *
     * @param id role id {@link Long}
     */
    public void deleteRole(Long id) {
        verifyOpsLegality(id);
        this.removeById(id);
    }

    public List<RoleEntity> listByRoleIds(Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaQuery()
                .select(RoleEntity::getCode)
                .in(RoleEntity::getId, roleIds);
        return this.list(wrapper);
    }
}
