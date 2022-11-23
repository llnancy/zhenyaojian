package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.AssignRolePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.framework.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * role-permission controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/21
 */
@RestController
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping("/role/permission")
    @PreAuthorize("hasAuthority('system:role-permission:assign')")
    public IResponse assignRolePermission(@Validated @RequestBody AssignRolePermissionRequest request) {
        rolePermissionService.assignRolePermission(request);
        return IResponse.ofSuccess();
    }

    @GetMapping("/role/{roleId}/permission")
    @PreAuthorize("hasAuthority('system:role-permission:assign')")
    public MultiResponse<Long> rolePermissions(@PathVariable Long roleId) {
        return MultiResponse.success(rolePermissionService.queryPermissionIdsByRoleId(roleId));
    }
}
