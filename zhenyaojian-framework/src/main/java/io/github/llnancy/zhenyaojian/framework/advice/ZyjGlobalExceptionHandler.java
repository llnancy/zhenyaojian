package io.github.llnancy.zhenyaojian.framework.advice;

import io.github.llnancy.zhenyaojian.framework.security.handler.ZyjAccessDeniedHandler;
import io.github.llnancy.zhenyaojian.framework.security.handler.ZyjAuthenticationEntryPoint;
import io.github.llnancy.mojian.web.advice.MjDefaultGlobalExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handler
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/1
 */
@RestControllerAdvice
public class ZyjGlobalExceptionHandler extends MjDefaultGlobalExceptionHandler {

    /**
     * 将捕获到的 {@link AccessDeniedException} 异常继续向外抛出，转交给 {@link ZyjAccessDeniedHandler} 进行处理。
     *
     * @param ade {@link AccessDeniedException}
     */
    @ExceptionHandler({AccessDeniedException.class})
    public void handleAccessDeniedException(AccessDeniedException ade) {
        throw ade;
    }

    /**
     * 将捕获到的 {@link AuthenticationException} 异常继续向外抛出，转交给 {@link ZyjAuthenticationEntryPoint} 进行处理。
     *
     * @param ae {@link AuthenticationException}
     */
    @ExceptionHandler({AuthenticationException.class})
    public void handleAuthenticationException(AuthenticationException ae) {
        throw ae;
    }
}
