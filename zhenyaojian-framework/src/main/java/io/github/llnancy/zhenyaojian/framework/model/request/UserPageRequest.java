package io.github.llnancy.zhenyaojian.framework.model.request;

import io.github.llnancy.mojian.base.entity.request.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user page request
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequest extends BasePageRequest {

    private static final long serialVersionUID = -5058583303514509872L;

    private String account;
}
