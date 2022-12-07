package io.github.llnancy.zhenyaojian.admin.web.controller;

import io.github.llnancy.mojian.base.entity.response.SingleResponse;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.enums.AccessType;
import io.github.llnancy.zhenyaojian.framework.model.request.LoginRequest;
import io.github.llnancy.zhenyaojian.framework.service.LoginService;
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
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @AccessLog(type = AccessType.LOGIN)
    public SingleResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return SingleResponse.success(loginService.login(loginRequest));
    }
}
