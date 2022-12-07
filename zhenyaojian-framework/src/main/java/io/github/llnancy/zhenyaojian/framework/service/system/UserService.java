package io.github.llnancy.zhenyaojian.framework.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import io.github.llnancy.zhenyaojian.framework.config.WebSecurityConfig;
import io.github.llnancy.zhenyaojian.framework.config.property.ZyjFrameworkProperties;
import io.github.llnancy.zhenyaojian.framework.mapstruct.UserMapstruct;
import io.github.llnancy.zhenyaojian.framework.model.request.UserOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.request.UserPageRequest;
import io.github.llnancy.zhenyaojian.framework.model.response.UserInfoResponse;
import io.github.llnancy.zhenyaojian.framework.util.SecurityUtils;
import io.github.llnancy.zhenyaojian.system.repository.entity.RoleEntity;
import io.github.llnancy.zhenyaojian.system.repository.entity.UserEntity;
import io.github.llnancy.zhenyaojian.system.repository.mapper.UserMapper;
import io.github.llnancy.mojian.base.entity.response.MultiPageResponse;
import io.github.llnancy.mojian.base.util.Optionals;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

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
public class UserService extends ServiceImpl<UserMapper, UserEntity> implements IService<UserEntity>, ApplicationContextAware, InitializingBean {

    private static String DEFAULT_PASSWORD;

    private ApplicationContext applicationContext;

    private final RoleService roleService;

    private final UserMapstruct userMapstruct;

    private static ZyjFrameworkProperties zyjFrameworkProperties;

    @Autowired
    public void setZyjFrameworkProperties(ZyjFrameworkProperties zyjFrameworkProperties) {
        UserService.zyjFrameworkProperties = zyjFrameworkProperties;
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DEFAULT_PASSWORD = applicationContext.getId();
    }

    /**
     * 提供给 {@link UserMapstruct} 转换器使用的密码加密静态方法。
     * 需要放置在 {@link UserMapstruct} 外部，否则会应用于所有 {@link String} 类型的属性。
     *
     * @param password 明文密码
     * @return 密文
     */
    public static String encryptPassword(String password, UserOpsCommand.UserOpsTypeEnum userOpsType) {
        if (userOpsType == UserOpsCommand.UserOpsTypeEnum.RESET_PASSWORD) {
            password = Optionals.of(zyjFrameworkProperties.getDefaultPassword(), DEFAULT_PASSWORD);
        }
        if (StringUtils.isBlank(password)) {
            return password;
        }
        return WebSecurityConfig.B_CRYPT_PASSWORD_ENCODER.encode(password);
    }

    /**
     * create {@link UserEntity}.
     *
     * @param command {@link UserOpsCommand}
     * @return user ID {@link Long}
     */
    public Long createUser(UserOpsCommand command) {
        verifyAccountUniqueness(command);
        verifyPhoneNumberUniqueness(command);
        verifyEmailUniqueness(command);
        UserEntity user = userMapstruct.convert(command);
        this.save(user);
        return user.getId();
    }

    /**
     * 校验角色存在性
     *
     * @param roleIds set of role id
     */
    public void verifyRoleIdsExistence(Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }
        LambdaQueryWrapper<RoleEntity> wrapper = Wrappers.<RoleEntity>lambdaQuery()
                .in(RoleEntity::getId, roleIds);
        long count = roleService.count(wrapper);
        Preconditions.checkArgument(roleIds.size() == count, "分配的角色不存在");
    }

    /**
     * 校验用户 ID 存在性
     *
     * @param userId user id {@link Long}
     */
    public void verifyUserIdExistence(Long userId) {
        UserEntity exist = this.getById(userId);
        Preconditions.checkArgument(Objects.nonNull(exist), "用户不存在");
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
        Preconditions.checkArgument(!(Objects.nonNull(exist) && ObjectUtils.notEqual(exist.getId(), command.getId())), "邮箱[" + email + "]已存在");
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
    public void updateUser(UserOpsCommand command) {
        Long userId = command.getId();
        verifyOpsLegality(userId);
        verifyAccountUniqueness(command);
        verifyPhoneNumberUniqueness(command);
        verifyEmailUniqueness(command);
        UserEntity user = userMapstruct.convert(command);
        // 动态 SQL
        LambdaUpdateWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaUpdate()
                .set(StringUtils.isNotBlank(user.getPassword()), UserEntity::getPassword, user.getPassword())
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
     * @return paging data of {@link UserInfoResponse}
     */
    public MultiPageResponse<UserInfoResponse> users(UserPageRequest request) {
        String account = request.getAccount();
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery()
                .likeRight(StringUtils.isNotBlank(account), UserEntity::getAccount, account);
        Page<UserEntity> page = Page.of(request.getPageNo(), request.getPageSize());
        page = this.getBaseMapper().selectPage(page, wrapper);
        return MultiPageResponse.success(page, userMapstruct::convert);
    }

    /**
     * delete user by id.
     *
     * @param id user id {@link Long}
     */
    public void deleteUser(Long id) {
        verifyOpsLegality(id);
        Preconditions.checkArgument(SecurityUtils.isNotLoginUser(id), "不允许操作当前登录账户");
        this.removeById(id);
    }
}
