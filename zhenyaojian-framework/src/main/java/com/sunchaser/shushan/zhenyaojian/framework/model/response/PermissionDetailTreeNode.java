package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * permission detail tree node
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PermissionDetailTreeNode extends TreeNode {

    /**
     * 权限类型（0：目录；1：菜单；2：按钮）
     */
    private Integer type;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 权限状态（0：显示；1：隐藏）
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
