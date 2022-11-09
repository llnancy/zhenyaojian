package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * role info
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleInfo {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 权限集合
     */
    private List<PermissionInfo> permissions;
}
