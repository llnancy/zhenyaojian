package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.google.common.collect.Lists;
import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiPageResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.zhenyaojian.framework.mapstruct.UserMapstruct;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.UserPageRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.MenuTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfo;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.UserInfoResponse;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.PermissionService;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserService;
import com.sunchaser.shushan.zhenyaojian.framework.util.SecurityUtils;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.UserEntity;
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

    private final PermissionService permissionService;

    private final UserMapstruct userMapstruct;

    @GetMapping("/user/info")
    public SingleResponse<UserInfoResponse> info() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserEntity userEntity = loginUser.getUserEntity();
        UserInfoResponse userInfo = userMapstruct.convert(userEntity);
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setName("role");
        roleInfo.setPermissions(Lists.newArrayList(new PermissionInfo("name", 0, "icon", "path", "component")));
        userInfo.setRoleInfo(roleInfo);
        return SingleResponse.success(userInfo);
    }

    @GetMapping("/user/menu")
    public MultiResponse<MenuTreeNode> menuInfo() {
        return MultiResponse.success(permissionService.menuInfo());
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:user:create')")
    public SingleResponse<Long> createUser(@Validated @RequestBody UserOpsCommand request) {
        return SingleResponse.success(userService.createUser(request));
    }

    @PatchMapping("/user")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:user:update')")
    public IResponse updateUser(@Validated @RequestBody UserOpsCommand request) {
        userService.updateUser(request);
        return IResponse.ofSuccess();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:user:list')")
    public MultiPageResponse<UserInfo> users(UserPageRequest request) {
        return userService.users(request);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:user:delete')")
    public IResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return IResponse.ofSuccess();
    }
}
