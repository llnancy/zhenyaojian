package com.sunchaser.shushan.zhenyaojian.framework.security;

import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * login user
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
public class LoginUser extends User {

    @Getter
    @Setter
    private UserEntity userEntity;

    public LoginUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getAccount(), userEntity.getPassword(), authorities);
        this.userEntity = userEntity;
    }
}
