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
 * 用户表
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/10/31
 */
@Data
@TableName("zyj_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    @TableField("`account`")
    private String account;

    /**
     * 用户密码
     */
    @TableField("`password`")
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 所属岗位ID
     */
    private Long positionId;

    /**
     * 用户状态（0：正常；1：停用）
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
