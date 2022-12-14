package io.github.llnancy.zhenyaojian.admin.web.controller.system;

import io.github.llnancy.mojian.base.entity.response.IResponse;
import io.github.llnancy.mojian.base.entity.response.MultiResponse;
import io.github.llnancy.mojian.base.entity.response.SingleResponse;
import io.github.llnancy.mojian.log.annotation.AccessLog;
import io.github.llnancy.mojian.log.enums.AccessType;
import io.github.llnancy.mojian.web.validation.groups.Update;
import io.github.llnancy.zhenyaojian.framework.model.request.PermissionOpsCommand;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionBaseTreeNode;
import io.github.llnancy.zhenyaojian.framework.model.response.PermissionDetailTreeNode;
import io.github.llnancy.zhenyaojian.framework.service.system.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * permission controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@RestController
@RequiredArgsConstructor
@AccessLog
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/permission")
    @PreAuthorize("@ss.hasAuthority('system:permission:create')")
    public SingleResponse<Long> createPermission(@Validated @RequestBody PermissionOpsCommand command) {
        return SingleResponse.success(permissionService.createPermission(command));
    }

    @PatchMapping("/permission")
    @PreAuthorize("@ss.hasAuthority('system:permission:update')")
    public IResponse updatePermission(@Validated({Update.class}) @RequestBody PermissionOpsCommand command) {
        permissionService.updatePermission(command);
        return IResponse.ofSuccess();
    }

    @GetMapping("/permissions")
    @PreAuthorize("@ss.hasAuthority('system:permission:list')")
    public MultiResponse<PermissionDetailTreeNode> permissions(@RequestParam(required = false) String name) {
        return MultiResponse.success(permissionService.permissionDetailTreeList(name));
    }

    @GetMapping("/permissions/tree")
    @AccessLog(type = AccessType.SELECT)
    @PreAuthorize("@ss.hasAuthority('system:permission:list')")
    public MultiResponse<PermissionBaseTreeNode> permissionsTree(@RequestParam String filter) {
        return MultiResponse.success(permissionService.permissionsTree(filter));
    }

    @DeleteMapping("/permission/{id}")
    @PreAuthorize("@ss.hasAuthority('system:permission:delete')")
    public IResponse deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return IResponse.ofSuccess();
    }
}
