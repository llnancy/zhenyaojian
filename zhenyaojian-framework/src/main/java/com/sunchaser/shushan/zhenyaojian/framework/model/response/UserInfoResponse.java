package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * user info
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/17
 */
@Data
public class UserInfoResponse {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String account;

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
     * 用户状态（0：正常；1：停用）
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
