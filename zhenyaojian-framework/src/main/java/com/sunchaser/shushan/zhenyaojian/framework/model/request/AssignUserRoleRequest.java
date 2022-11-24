package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * assign user-role request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/24
 */
@Data
public class AssignUserRoleRequest {

    /**
     * 用户 ID
     */
    @NotNull(message = "userId 不能为空")
    @Min(value = 0L, message = "userId 不合法")
    private Long userId;

    /**
     * 角色 ID 集合
     */
    private Set<Long> roleIds;
}
