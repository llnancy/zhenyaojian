package io.github.llnancy.zhenyaojian.framework.model.response;

import lombok.Data;

import java.util.List;

/**
 * tree node
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
@Data
public class TreeNode {

    private Long id;

    private Long parentId;

    private List<? extends TreeNode> children;
}
