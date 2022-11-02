package com.sunchaser.shushan.zhenyaojian.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 表 status 字段值枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@AllArgsConstructor
@Getter
public enum TableStatusFieldEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 停用、禁用
     */
    FORBIDDEN(1, "停用");

    private final Integer status;

    private final String desc;

    public static boolean isForbidden(Integer status) {
        return FORBIDDEN.getStatus().equals(status);
    }
}
