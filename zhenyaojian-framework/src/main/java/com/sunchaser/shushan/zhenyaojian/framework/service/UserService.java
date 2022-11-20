package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.sunchaser.shushan.mojian.base.entity.response.MultiPageResponse;
import com.sunchaser.shushan.zhenyaojian.framework.config.WebSecurityConfig;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserPageRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfo;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.framework.util.Streams;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserRoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * user service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Service
@RequiredArgsConstructor
public class UserService extends ServiceImpl<UserMapper, UserEntity> implements IService<UserEntity> {

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    private final UserMapstruct userMapstruct;

    /**
     * 提供给 {@link UserMapstruct} 转换器使用的密码加密静态方法。
     * 需要放置在 {@link UserMapstruct} 外部，否则会应用于所有 {@link String} 类型的属性。
     *
     * @param password 明文密码
     * @return 密文
     */
    public static String encryptPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return password;
        }
        return WebSecurityConfig.B_CRYPT_PASSWORD_ENCODER.encode(password);
    }

    /**
     * create {@link UserEntity}.
     *
     * @param command {@link UserOpsCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserOpsCommand command) {
        verifyAccountUniqueness(command);
        verifyPhoneNumberUniqueness(command);
        verifyEmailUniqueness(command);
        verifyRoleExistence(command);
        UserEntity user = userMapstruct.convert(command);
        this.save(user);
        userRoleService.batchInsert(user.getId(), command.getRoleIds());
    }

    /**
     * 校验角色存在性
     *
     * @param command {@link UserOpsCommand}
     */
    private void verifyRoleExistence(UserOpsCommand command) {
        Set<Long> roleIds = command.getRoleIds();
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        LambdaQueryWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaQuery()
                .in(RoleEntity::getId, roleIds);
        long count = roleService.count(wrapper);
        Preconditions.checkArgument(roleIds.size() == count, "分配的角色不存在");
    }

    /**
     * 校验邮箱唯一性
     *
     * @param command {@link UserOpsCommand}
     */
    private void verifyEmailUniqueness(UserOpsCommand command) {
        String email = command.getEmail();
        if (StringUtils.isBlank(email)) {
            return;
        }
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getEmail, email);
        UserEntity exist = this.getOne(wrapper);
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "邮箱[" + "]已存在");
    }

    /**
     * 校验手机号码唯一性
     *
     * @param command {@link UserOpsCommand}
     */
    private void verifyPhoneNumberUniqueness(UserOpsCommand command) {
        String phoneNumber = command.getPhoneNumber();
        if (StringUtils.isBlank(phoneNumber)) {
            return;
        }
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getPhoneNumber, phoneNumber);
        UserEntity exist = this.getOne(wrapper);
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "手机号码[" + phoneNumber + "]已存在");
    }

    /**
     * 校验账号唯一性
     *
     * @param command {@link UserOpsCommand}
     */
    private void verifyAccountUniqueness(UserOpsCommand command) {
        String account = command.getAccount();
        if (StringUtils.isBlank(account)) {
            return;
        }
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getAccount, account);
        UserEntity exist = this.getOne(wrapper);
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "账户名[" + account + "]已存在");
    }

    /**
     * update {@link UserEntity}.
     *
     * @param command {@link UserOpsCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserOpsCommand command) {
        Long userId = command.getId();
        verifyOpsLegality(userId);
        verifyAccountUniqueness(command);
        verifyPhoneNumberUniqueness(command);
        verifyEmailUniqueness(command);
        verifyRoleExistence(command);
        Set<Long> newRoleIds = command.getRoleIds();
        if (CollectionUtils.isNotEmpty(newRoleIds)) {
            List<UserRoleEntity> oldUserRoles = userRoleService.listByUserId(userId);
            Set<Long> oldRoleIds = Streams.mapToSet(oldUserRoles, UserRoleEntity::getRoleId);
            // 待删除：旧角色列表相对于新角色列表的差集
            Sets.SetView<Long> removeRoleIds = Sets.difference(oldRoleIds, newRoleIds);
            // 待新增：新角色列表相对于旧角色列表的差集
            Sets.SetView<Long> insertRoleIds = Sets.difference(newRoleIds, oldRoleIds);
            userRoleService.removeByUserIdAndRoleIds(userId, removeRoleIds);
            userRoleService.batchInsert(userId, insertRoleIds);
        }
        UserEntity user = userMapstruct.convert(command);
        // 动态 SQL
        LambdaUpdateWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaUpdate()
                .set(StringUtils.isNotBlank(command.getPassword()), UserEntity::getPassword, user.getPassword())
                .set(StringUtils.isNotBlank(command.getNickName()), UserEntity::getNickName, user.getNickName())
                .set(StringUtils.isNotBlank(command.getAvatar()), UserEntity::getAvatar, user.getAvatar())
                .set(StringUtils.isNotBlank(command.getSex()), UserEntity::getSex, user.getSex())
                .set(StringUtils.isNotBlank(command.getEmail()), UserEntity::getEmail, user.getEmail())
                .set(StringUtils.isNotBlank(command.getPhoneNumber()), UserEntity::getPhoneNumber, user.getPhoneNumber())
                .set(Objects.nonNull(command.getStatus()), UserEntity::getStatus, user.getStatus())
                .eq(UserEntity::getId, userId);
        this.update(wrapper);
    }

    /**
     * 校验操作合法性：禁止操作超级管理员账户
     *
     * @param userId {@link Long}
     */
    private void verifyOpsLegality(Long userId) {
        Preconditions.checkArgument(SecurityUtils.isNotSuperAdmin(userId), "不允许操作超级管理员账户");
    }

    /**
     * Paging query users.
     *
     * @param request {@link UserPageRequest}
     * @return paging data
     */
    public MultiPageResponse<UserInfo> users(UserPageRequest request) {
        String account = request.getAccount();
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery()
                .likeRight(StringUtils.isNotBlank(account), UserEntity::getAccount, account);
        Page<UserEntity> page = new Page<>(request.getPageNo(), request.getPageSize());
        Page<UserEntity> list = this.getBaseMapper().selectPage(page, wrapper);
        return MultiPageResponse.success(list, userMapstruct::convertToUserInfo);
    }

    /**
     * delete a {@link UserEntity} based on userId.
     *
     * @param userId {@link Long}
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        verifyOpsLegality(userId);
        Preconditions.checkArgument(SecurityUtils.isNotLoginUser(userId), "不允许操作当前登录账户");
        userRoleService.removeByUserId(userId);
        this.removeById(userId);
    }
}
