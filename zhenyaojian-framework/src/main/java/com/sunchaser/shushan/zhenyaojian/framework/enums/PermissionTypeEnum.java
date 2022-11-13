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
    CATALOG,

    /**
     * 菜单
     */
    MENU,

    /**
     * 按钮
     */
    BUTTON
    ;

    private static final Map<Integer, PermissionTypeEnum> ENUM_MAP = Maps.newHashMap();

    static {
        for (PermissionTypeEnum value : PermissionTypeEnum.values()) {
            ENUM_MAP.put(value.ordinal(), value);
        }
    }

    public static PermissionTypeEnum match(Integer type) {
        return ENUM_MAP.get(type);
    }

    public static boolean isCatalog(Integer type) {
        return CATALOG == match(type);
    }

    public static boolean isNotCatalog(Integer type) {
        return !isCatalog(type);
    }

    public static boolean isMenu(Integer type) {
        return MENU == match(type);
    }

    public static boolean isNotMenu(Integer type) {
        return !isMenu(type);
    }

    public static boolean isButton(Integer type) {
        return BUTTON == match(type);
    }

    public static boolean isNotButton(Integer type) {
        return !isButton(type);
    }
}
