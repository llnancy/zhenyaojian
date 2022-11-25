package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * role item info
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/20
 */
@Data
public class RoleInfoResponse {

    /**
     * 角色 ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 角色状态（0：正常；1：停用）
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
