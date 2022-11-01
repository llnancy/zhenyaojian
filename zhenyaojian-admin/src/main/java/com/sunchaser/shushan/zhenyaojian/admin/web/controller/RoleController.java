package com.sunchaser.shushan.zhenyaojian.admin.web.controller;

import com.sunchaser.shushan.mojian.base.entity.response.MultiResponse;
import com.sunchaser.shushan.zhenyaojian.system.repository.entity.RoleEntity;
import com.sunchaser.shushan.zhenyaojian.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * role controller
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/1
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    public MultiResponse<RoleEntity> list() {
        return null;
    }
}
