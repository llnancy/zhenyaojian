package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import com.sunchaser.shushan.mojian.web.validation.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;

    /**
     * 显示排序
     */
    private Integer sortValue;
}
