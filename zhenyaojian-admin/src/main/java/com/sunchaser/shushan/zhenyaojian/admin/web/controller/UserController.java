package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiPageResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.mojian.log.annotation.LogIgnore;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserPageRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RouterTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.framework.service.PermissionService;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserService;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * user controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/4
 */
@RestController
@RequiredArgsConstructor
@AccessLog
public class UserController {

    private final UserService userService;

    private final PermissionService permissionService;

    private final UserMapstruct userMapstruct;

    @GetMapping("/user/info")
    @LogIgnore
    public SingleResponse<UserInfoResponse> info() {
        return SingleResponse.success(userMapstruct.convert(SecurityUtils.getLoginUserEntity()));
    }

    @GetMapping("/user/router")
    @LogIgnore
    public MultiResponse<RouterTreeNode> routerInfo() {
        List<RouterTreeNode> routerTreeNodes = permissionService.routerInfo();
        return MultiResponse.success(routerTreeNodes);
    }

    @PostMapping("/user")
    @PreAuthorize("@ss.hasAuthority('system:user:create')")
    public SingleResponse<Long> createUser(@Validated @RequestBody UserOpsCommand request) {
        return SingleResponse.success(userService.createUser(request));
    }

    @PatchMapping("/user")
    @PreAuthorize("@ss.hasAuthority('system:user:update')")
    public IResponse updateUser(@Validated @RequestBody UserOpsCommand request) {
        userService.updateUser(request);
        return IResponse.ofSuccess();
    }

    @GetMapping("/users")
    @PreAuthorize("@ss.hasAuthority('system:user:list')")
    public MultiPageResponse<UserInfoResponse> users(UserPageRequest request) {
        return userService.users(request);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("@ss.hasAuthority('system:user:delete')")
    public IResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return IResponse.ofSuccess();
    }
}
