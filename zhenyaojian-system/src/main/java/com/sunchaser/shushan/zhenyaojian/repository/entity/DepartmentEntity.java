package com.sunchaser.shushan.zhenyaojian.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 部门表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/31
 */
@Data
@TableName("zyj_department")
public class DepartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    @TableField("`name`")
    private String name;

    /**
     * 父级部门ID
     */
    private Long parentId;

    /**
     * 祖先部门结构
     */
    private String ancestorPath;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 部门负责人
     */
    private String leader;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 部门状态（0：正常；1：停用）
     */
    @TableField("`status`")
    @TableLogic
    private Integer status;

    /**
     * 逻辑删除（0：正常；1：删除）
     */
    private Integer isDeleted;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
