package com.sunchaser.shushan.zhenyaojian.framework.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.sunchaser.shushan.zhenyaojian.framework.model.response.TreeNode;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 树形结构构建工具类
 * 默认约定 parentId = 0 的为根节点
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
public final class TreeBuilder {

    public static <T, R extends TreeNode> List<R> build(List<T> sources,
                                                        Function<T, R> mapper) {
        return build(sources, mapper, (r, rs) -> {});
    }

    public static <T, R extends TreeNode> List<R> build(List<T> sources,
                                                        Function<T, R> mapper,
                                                        BiConsumer<R, List<R>> postProcessAfterBuildTree) {
        List<R> treeNodes = Streams.mapToList(sources, mapper);
        Map<Long, List<R>> parentIdNodeMap = Streams.groupingBy(treeNodes, TreeNode::getParentId);
        List<R> rootNodes = Streams.filterToList(treeNodes, (node) -> node.getParentId() == 0L);
        return doBuildTree(parentIdNodeMap, rootNodes, postProcessAfterBuildTree);
    }

    private static <R extends TreeNode> List<R> doBuildTree(Map<Long, List<R>> parentIdNodeMap,
                                                            List<R> rootNodes,
                                                            BiConsumer<R, List<R>> postProcessHook) {
        for (R root : rootNodes) {
            Long id = root.getId();
            List<R> children = parentIdNodeMap.get(id);
            if (CollectionUtils.isNotEmpty(children)) {
                // Recursive children
                doBuildTree(parentIdNodeMap, children, postProcessHook);
                root.setChildren(children);
            }
            postProcessHook.accept(root, children);
        }
        return rootNodes;
    }
}
