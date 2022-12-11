package io.github.llnancy.zhenyaojian.system.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("zyj_permission")
public class PermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 父级权限ID
     */
    private Long parentId;

    /**
     * 权限类型（0：目录；1：菜单；2：按钮）
     */
    @TableField("`type`")
    private Integer type;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 路由地址
     */
    @TableField("`path`")
    private String path;

    /**
     * 组件名称
     */
    @TableField("`component`")
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
    @TableField("`status`")
    private Integer status;

    /**
     * 逻辑删除（0：正常；1：删除）
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
