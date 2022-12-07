package io.github.llnancy.zhenyaojian.framework.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * router tree node
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RouterTreeNode extends TreeNode {

    @JsonIgnore
    private Integer type;

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
