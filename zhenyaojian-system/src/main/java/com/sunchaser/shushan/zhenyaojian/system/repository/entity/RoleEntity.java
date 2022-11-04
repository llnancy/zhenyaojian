package com.sunchaser.shushan.zhenyaojian.system.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 角色表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/04
 */
@Data
@TableName("zyj_role")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    @TableField("`code`")
    private String code;

    /**
     * 角色名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 角色状态（0：正常；1：停用）
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
