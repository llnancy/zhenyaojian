package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import com.sunchaser.shushan.mojian.web.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * user ops command
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOpsCommand {

    /**
     * 自增ID
     */
    @NotNull(message = "ID 不能为空", groups = {Update.class})
    private Long id;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户密码
     */
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
     * 用户状态
     */
    private Integer status;
}
