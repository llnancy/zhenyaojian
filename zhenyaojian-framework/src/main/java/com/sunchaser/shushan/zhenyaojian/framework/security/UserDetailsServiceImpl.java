package com.sunchaser.shushan.zhenyaojian.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.sunchaser.shushan.zhenyaojian.framework.enums.TableStatusFieldEnum;
import com.sunchaser.shushan.zhenyaojian.framework.service.PermissionService;
import com.sunchaser.shushan.zhenyaojian.framework.util.Streams;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.PermissionEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

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
        List<PermissionEntity> permissions = permissionService.queryPermissionsByUserId(userEntity.getId());
        Set<SimpleGrantedAuthority> authorities = Streams.filterAndMapToSet(
                permissions,
                permission -> StringUtils.isNotBlank(permission.getPermission()),
                permission -> new SimpleGrantedAuthority(permission.getPermission())
        );
        return new LoginUser(userEntity, authorities);
    }
}
