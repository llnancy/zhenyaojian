package com.sunchaser.shushan.zhenyaojian.framework.model.request;

import com.sunchaser.shushan.mojian.base.entity.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * role page request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageRequest extends BasePageRequest {

    private static final long serialVersionUID = -7733344332191151168L;

    /**
     * 角色名称
     */
    private String name;
}
