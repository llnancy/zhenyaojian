package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create role request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;
}
