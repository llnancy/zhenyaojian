package com.sunchaser.shushan.zhenyaojian.framework.enums;

import com.sunchaser.shushan.mojian.base.entity.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * response
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum implements Response {

    /**
     * 业务码
     */
    LOGOUT_SUCCESS(20002, "退出成功")
    ;

    private final Integer code;

    private final String msg;
}
