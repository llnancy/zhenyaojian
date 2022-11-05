package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.google.common.collect.Lists;
import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreateUserRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserService;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * user controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapstruct userMapstruct;

    @GetMapping("/user/info")
    public SingleResponse<UserInfoResponse> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        UserEntity userEntity = loginUser.getUserEntity();
        UserInfoResponse userInfo = userMapstruct.convert(userEntity);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setName("role");
        roleInfo.setPermissions(Lists.newArrayList(new PermissionInfo("name", 0, "icon", "path", "component")));
        userInfo.setRoleInfo(roleInfo);
        return SingleResponse.success(userInfo);
    }

    @PostMapping("/user")
    public IResponse createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return IResponse.SUCCESS;
    }
}
