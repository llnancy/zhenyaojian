package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * menu tree node
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MenuTreeNode extends TreeNode {

    private String path;

    private String component;

    private Meta meta;

    private String redirect;

    @Data
    public static class Meta {

        private String title;

        private String icon;

        private String permission;
    }

}
