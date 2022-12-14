package io.github.llnancy.zhenyaojian.framework.model.request;

import io.github.llnancy.mojian.web.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * permission ops
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionOpsCommand {

    /**
     * 自增ID
     */
    @NotNull(message = "ID 不能为空", groups = {Update.class})
    private Long id;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Length(max = 32, message = "权限名称不能超过 32 个字符")
    private String name;

    /**
     * 父级权限ID
     */
    @NotNull(message = "父级权限 ID 不能为空")
    private Long parentId;

    /**
     * 权限类型（0：目录；1：菜单；2：按钮）
     */
    @NotNull(message = "权限类型不能为空")
    @Max(value = 2, message = "权限类型取值：{0：目录；1：菜单；2：按钮}")
    private Integer type;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 权限状态（0：显示；1：隐藏）
     */
    private Integer status;
}
