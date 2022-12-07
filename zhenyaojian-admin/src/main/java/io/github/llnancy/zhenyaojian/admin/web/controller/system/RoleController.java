package io.github.llnancy.zhenyaojian.admin.web.controller.system;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.entity.response.MultiPageResponse;
import io.github.llnancy.mojian.base.entity.response.SingleResponse;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.zhenyaojian.framework.model.request.RoleOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.request.RolePageRequest;
import io.github.llnancy.zhenyaojian.framework.model.response.RoleInfoResponse;
import io.github.llnancy.zhenyaojian.framework.service.system.RoleService;
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
@AccessLog
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/role")
    @PreAuthorize("@ss.hasAuthority('system:role:create')")
    public SingleResponse<Long> createRole(@Validated @RequestBody RoleOpsCommand command) {
        return SingleResponse.success(roleService.createRole(command));
    }

    @PatchMapping("/role")
    @PreAuthorize("@ss.hasAuthority('system:role:update')")
    public IResponse updateRole(@Validated @RequestBody RoleOpsCommand command) {
        roleService.updateRole(command);
        return IResponse.ofSuccess();
    }

    @GetMapping("/roles")
    @PreAuthorize("@ss.hasAuthority('system:role:list')")
    public MultiPageResponse<RoleInfoResponse> roles(RolePageRequest request) {
        return roleService.roles(request);
    }

    @DeleteMapping("/role/{id}")
    @PreAuthorize("@ss.hasAuthority('system:role:delete')")
    public IResponse deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return IResponse.ofSuccess();
    }
}
