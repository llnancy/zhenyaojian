package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.mojian.log.annotation.AccessLog;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.AssignUserRoleRequest;
import com.sunchaser.shushan.zhenyaojian.framework.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * user-role controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/23
 */
@RestController
@RequiredArgsConstructor
@AccessLog
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/user/role")
    @PreAuthorize("@ss.hasAuthority('system:user-role:assign')")
    public IResponse assignUserRole(@Validated @RequestBody AssignUserRoleRequest request) {
        userRoleService.assignUserRole(request);
        return IResponse.ofSuccess();
    }

    @GetMapping("/user/{userId}/role")
    @PreAuthorize("@ss.hasAuthority('system:user-role:assign')")
    public MultiResponse<Long> userRoles(@PathVariable Long userId) {
        return MultiResponse.success(userRoleService.queryRoleIdsByUserId(userId));
    }
}
