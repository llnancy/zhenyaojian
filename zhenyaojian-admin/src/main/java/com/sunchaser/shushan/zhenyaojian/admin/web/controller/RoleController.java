package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiPageResponse;
import com.sunchaser.shushan.mojian.base.entity.response.SingleResponse;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.RoleOpsCommand;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.RolePageRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.RoleItemInfo;
import com.sunchaser.shushan.zhenyaojian.framework.service.RoleService;
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
 * role controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/1
 */
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/role")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:role:create')")
    public SingleResponse<Long> createRole(@Validated @RequestBody RoleOpsCommand command) {
        return SingleResponse.success(roleService.createRole(command));
    }

    @PatchMapping("/role")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:role:update')")
    public IResponse updateRole(@Validated @RequestBody RoleOpsCommand command) {
        roleService.updateRole(command);
        return IResponse.ofSuccess();
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:role:list')")
    public MultiPageResponse<RoleItemInfo> roles(RolePageRequest request) {
        return roleService.roles(request);
    }

    @DeleteMapping("/role/{id}")
    @PreAuthorize("hasRole('super-admin') or hasAuthority('system:role:delete')")
    public IResponse deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return IResponse.ofSuccess();
    }
}
