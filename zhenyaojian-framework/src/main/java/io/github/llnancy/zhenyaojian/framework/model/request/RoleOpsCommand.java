package io.github.llnancy.zhenyaojian.framework.model.request;

import io.github.llnancy.mojian.web.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * create role request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleOpsCommand {

    /**
     * 自增 ID
     */
    @NotNull(message = "ID 不能为空", groups = {Update.class})
    private Long id;

    /**
     * 角色编码
     */
    // private String code;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    @Length(max = 16, message = "角色名称不能超过 16 个字符")
    private String name;

    /**
     * 显示排序
     */
    private Integer sortValue;

    /**
     * 角色状态（0：正常；1：停用）
     */
    private Integer status;
}
