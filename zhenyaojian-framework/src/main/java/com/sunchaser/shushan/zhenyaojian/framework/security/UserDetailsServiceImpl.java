package com.sunchaser.shushan.zhenyaojian.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.sunchaser.shushan.zhenyaojian.framework.enums.TableStatusFieldEnum;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getAccount, username);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(userEntity)) {
            // UsernameNotFoundException 异常会被 Spring Security 将错误信息转化为：用户名或密码错误
            // 详见 AbstractUserDetailsAuthenticationProvider.java:141
            throw new UsernameNotFoundException("用户 " + username + " 不存在！");
        }
        Preconditions.checkArgument(ObjectUtils.notEqual(TableStatusFieldEnum.FORBIDDEN.ordinal(), userEntity.getStatus()), "对不起，您的账号已被停用，请联系管理员");
        return new LoginUser(userEntity, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
