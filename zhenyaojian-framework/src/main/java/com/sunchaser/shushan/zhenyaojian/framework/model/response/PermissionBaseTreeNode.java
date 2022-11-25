package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * permission tree node
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PermissionBaseTreeNode extends TreeNode {

    @JsonIgnore
    private Integer type;

    private String name;

    private Boolean disabled = Boolean.FALSE;
}
