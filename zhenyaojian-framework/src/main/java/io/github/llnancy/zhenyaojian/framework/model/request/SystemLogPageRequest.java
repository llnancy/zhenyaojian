package io.github.llnancy.zhenyaojian.framework.model.request;

import io.github.llnancy.mojian.base.entity.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * system log page request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/12/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemLogPageRequest extends BasePageRequest {

    private static final long serialVersionUID = 6088724312647831281L;

    private String type;
}
