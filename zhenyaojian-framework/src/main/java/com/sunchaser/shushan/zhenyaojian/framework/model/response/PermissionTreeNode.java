package com.sunchaser.shushan.zhenyaojian.framework.model.response;

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
public class PermissionTreeNode extends TreeNode {

    private String name;
}
