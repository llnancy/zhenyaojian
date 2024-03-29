package io.github.llnancy.zhenyaojian.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import io.github.llnancy.zhenyaojian.framework.service.system.PermissionService;
import io.github.llnancy.zhenyaojian.framework.service.system.RoleService;
import io.github.llnancy.zhenyaojian.framework.service.system.UserRoleService;
import io.github.llnancy.zhenyaojian.framework.util.Streams;
import io.github.llnancy.zhenyaojian.system.repository.entity.PermissionEntity;
import io.github.llnancy.zhenyaojian.system.repository.entity.RoleEntity;
import io.github.llnancy.zhenyaojian.system.repository.entity.UserEntity;
import io.github.llnancy.zhenyaojian.system.repository.mapper.UserMapper;
import io.github.llnancy.mojian.base.enums.TableStatusFieldEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * UserDetailsService Impl
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private final UserMapper userMapper;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getAccount, username);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(userEntity)) {
            // UsernameNotFoundException 异常默认会被 Spring Security 转化为 BadCredentialsException
            // 对应错误信息为：用户名或密码错误。详见 AbstractUserDetailsAuthenticationProvider.java:141
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Preconditions.checkArgument(ObjectUtils.notEqual(TableStatusFieldEnum.FORBIDDEN.ordinal(), userEntity.getStatus()), "对不起，您的账号已被停用，请联系管理员");
        Long userId = userEntity.getId();
        Set<Long> roleIds = userRoleService.queryRoleIdsByUserId(userId);
        List<RoleEntity> roles = roleService.listByRoleIds(roleIds);
        Set<SimpleGrantedAuthority> roleAuthorities = Streams.filterAndMapToSet(
                roles,
                role -> StringUtils.isNotBlank(role.getCode()),
                role -> new SimpleGrantedAuthority(DEFAULT_ROLE_PREFIX + role.getCode())
        );
        List<PermissionEntity> permissions = permissionService.queryNormalPermissionsByUserIdAndRoleIds(userId, roleIds);
        Set<SimpleGrantedAuthority> permissionAuthorities = Streams.filterAndMapToSet(
                permissions,
                permission -> StringUtils.isNotBlank(permission.getPermission()),
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        );
        return new LoginUser(userEntity, Sets.union(roleAuthorities, permissionAuthorities));
    }
}
