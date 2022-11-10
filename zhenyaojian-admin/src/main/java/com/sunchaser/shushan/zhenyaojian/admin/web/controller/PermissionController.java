package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreatePermissionRequest;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.PermissionTreeNode;
import com.sunchaser.shushan.zhenyaojian.framework.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/permission")
    public IResponse createPermission(@Validated @RequestBody CreatePermissionRequest request) {
        permissionService.createPermission(request);
        return IResponse.SUCCESS;
    }

    @GetMapping("/permissions")
    public MultiResponse<String> permissions() {
        return MultiResponse.success(null);
    }

    @GetMapping("/permissions/tree")
    public MultiResponse<PermissionTreeNode> permissionsTree(@RequestParam String filter) {
        return MultiResponse.success(permissionService.permissionsTree(filter));
    }

    @DeleteMapping("/permission/{id}")
    public IResponse deletePermission(@PathVariable Long id) {
        permissionService.removeById(id);
        return IResponse.SUCCESS;
    }
}
