package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.annotation.LogIgnore;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.LoginRequest;
import com.sunchaser.shushan.zhenyaojian.framework.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@AccessLog
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @LogIgnore
    public SingleResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return SingleResponse.success(loginService.login(loginRequest));
    }
}
