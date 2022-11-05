package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create permission request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePermissionRequest {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 父级权限ID
     */
    private Long parentId;

    /**
     * 权限类型（0：目录；1：菜单；2：按钮）
     */
    private Integer type;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permissions;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 权限状态（0：显示；1：隐藏）
     */
    private Integer status;
}
