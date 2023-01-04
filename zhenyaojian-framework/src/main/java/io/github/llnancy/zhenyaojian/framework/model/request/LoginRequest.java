package io.github.llnancy.zhenyaojian.framework.model.request;

import io.github.llnancy.mojian.desensitize.annotation.Desensitize;
import io.github.llnancy.mojian.desensitize.strategy.impl.PasswordDesensitizeStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * login request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String account;

    @Desensitize(strategy = PasswordDesensitizeStrategy.class)
    private String password;

    private String verifyCode;

    private String uuid;
}
