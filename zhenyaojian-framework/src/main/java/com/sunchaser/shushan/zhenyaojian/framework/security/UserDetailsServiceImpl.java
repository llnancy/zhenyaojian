package com.sunchaser.shushan.zhenyaojian.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sunchaser.shushan.zhenyaojian.framework.enums.ResponseEnum;
import com.sunchaser.shushan.zhenyaojian.framework.enums.TableStatusFieldEnum;
import com.sunchaser.shushan.zhenyaojian.framework.exception.ZyjBizException;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import com.sunchaser.shushan.zhenyaojian.system.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
            throw new UsernameNotFoundException("用户 " + username + " 不存在！");
        }
        if (TableStatusFieldEnum.isForbidden(userEntity.getStatus())) {
            throw new ZyjBizException(ResponseEnum.USER_DISABLE, username);
        }
        return new LoginUser(userEntity.getAccount(), userEntity.getPassword(), Collections.emptyList());
    }
}
