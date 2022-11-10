package com.sunchaser.shushan.zhenyaojian.framework.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Integer type;

    private List<? extends TreeNode> children;
}
