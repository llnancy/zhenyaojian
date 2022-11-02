package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import lombok.Data;

/**
 * login request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@Data
public class LoginRequest {

    private String account;

    private String password;

    private String verifyCode;

    private String uuid;
}
