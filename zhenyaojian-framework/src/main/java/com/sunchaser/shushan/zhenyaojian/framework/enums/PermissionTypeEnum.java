package com.sunchaser.shushan.zhenyaojian.framework.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * permission type enum
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/8
 */
@AllArgsConstructor
@Getter
public enum PermissionTypeEnum {

    /**
     * 目录
     */
    CATALOG(0),

    /**
     * 菜单
     */
    MENU(1),

    /**
     * 按钮
     */
    BUTTON(2)
    ;

    private final Integer type;

    private static final Map<Integer, PermissionTypeEnum> ENUM_MAP = Maps.newHashMap();

    static {
        for (PermissionTypeEnum value : PermissionTypeEnum.values()) {
            ENUM_MAP.put(value.type, value);
        }
    }

    public static PermissionTypeEnum match(Integer type) {
        return ENUM_MAP.get(type);
    }

    public static boolean isCatalog(Integer type) {
        return CATALOG == match(type);
    }
}
