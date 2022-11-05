package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * permission info
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionInfo {

    private String name;

    private Integer type;

    private String icon;

    private String path;

    private String component;
}
