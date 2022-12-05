package com.sunchaser.shushan.zhenyaojian.framework.exception;

import com.sunchaser.shushan.mojian.base.entity.response.Response;
import com.sunchaser.shushan.mojian.base.exception.MjBaseBizException;

/**
 * 业务异常
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
public class ZyjBizException extends MjBaseBizException {

    private static final long serialVersionUID = -8187128395084777090L;

    public ZyjBizException(Response response, Object... args) {
        super(response, args);
    }

    public ZyjBizException(Integer code, String message, Object... args) {
        super(code, message, args);
    }

    public ZyjBizException(String message, Object... args) {
        super(message, args);
    }

    public ZyjBizException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }

    public ZyjBizException(Throwable cause) {
        super(cause);
    }

    public ZyjBizException() {
    }
}
