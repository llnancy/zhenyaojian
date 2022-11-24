package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * assign role-permission request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/20
 */
@Data
public class AssignRolePermissionRequest {

    /**
     * 角色 ID
     */
    @NotNull(message = "roleId 不能为空")
    private Long roleId;

    /**
     * 权限 ID 集合
     */
    private Set<Long> permissionIds;
}
