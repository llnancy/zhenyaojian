package io.github.llnancy.zhenyaojian.framework.security;

import io.github.llnancy.zhenyaojian.system.repository.entity.UserEntity;
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

    private static final long serialVersionUID = 4645395678551767717L;

    @Getter
    @Setter
    private UserEntity userEntity;

    public LoginUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getAccount(), userEntity.getPassword(), authorities);
        this.userEntity = userEntity;
    }

    public Long getUserId() {
        return this.userEntity.getId();
    }
}
