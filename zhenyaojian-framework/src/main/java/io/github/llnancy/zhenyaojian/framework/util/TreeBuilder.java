package io.github.llnancy.zhenyaojian.framework.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.github.llnancy.zhenyaojian.framework.model.response.TreeNode;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 树形结构构建工具类
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/9
 */
public final class TreeBuilder {

    public static <T, R extends TreeNode> List<R> build(List<T> sources,
                                                        Function<T, R> mapper) {
        return build(sources, mapper, (r, rs) -> {
        });
    }

    public static <T, R extends TreeNode> List<R> build(List<T> sources,
                                                        Function<T, R> mapper,
                                                        BiConsumer<R, List<R>> postProcessAfterBuildTree) {
        List<R> treeNodes = Streams.mapToList(sources, mapper);
        Map<Long, List<R>> parentIdNodeMap = Streams.groupingBy(treeNodes, TreeNode::getParentId);
        List<Long> ids = Streams.mapToList(treeNodes, TreeNode::getId);
        // parentId 不在 ids 中的节点是根节点
        List<R> rootNodes = Streams.filterToList(treeNodes, node -> !ids.contains(node.getParentId()));
        return doBuildTree(rootNodes, parentIdNodeMap, postProcessAfterBuildTree);
    }

    private static <R extends TreeNode> List<R> doBuildTree(List<R> rootNodes,
                                                            Map<Long, List<R>> parentIdNodeMap,
                                                            BiConsumer<R, List<R>> postProcessHook) {
        for (R root : rootNodes) {
            Long id = root.getId();
            List<R> children = parentIdNodeMap.get(id);
            if (CollectionUtils.isNotEmpty(children)) {
                // Recursive children
                doBuildTree(children, parentIdNodeMap, postProcessHook);
                root.setChildren(children);
            }
            postProcessHook.accept(root, children);
        }
        return rootNodes;
    }
}
