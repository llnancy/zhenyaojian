package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.IResponse;
import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.zhenyaojian.framework.model.request.CreateRoleRequest;
import com.sunchaser.shushan.zhenyaojian.framework.service.RoleService;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
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

    public MultiResponse<RoleEntity> list() {
        return MultiResponse.success(roleService.list());
    }

    @PostMapping("/role")
    public IResponse create(@RequestBody CreateRoleRequest request) {
        roleService.save(
                RoleEntity.builder()
                        .code(request.getCode())
                        .name(request.getName())
                        .build()
        );
        return IResponse.SUCCESS;
    }
}
