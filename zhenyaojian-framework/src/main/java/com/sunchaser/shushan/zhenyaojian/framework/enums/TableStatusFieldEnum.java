package com.sunchaser.shushan.zhenyaojian.framework.enums;

import java.util.Objects;

/**
 * 表 status 字段值枚举
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
public enum TableStatusFieldEnum {

    /**
     * 正常
     */
    NORMAL,

    /**
     * 停用、禁用
     */
    FORBIDDEN;

    public static boolean isForbidden(Integer status) {
        return Objects.equals(FORBIDDEN.ordinal(), status);
    }
}
