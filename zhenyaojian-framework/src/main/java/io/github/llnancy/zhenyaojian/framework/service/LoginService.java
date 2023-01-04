package io.github.llnancy.zhenyaojian.framework.service;

import io.github.llnancy.zhenyaojian.framework.model.request.LoginRequest;
import io.github.llnancy.zhenyaojian.framework.security.LoginUser;
import io.github.llnancy.zhenyaojian.framework.service.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * login service
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    /**
     * {@link UsernamePasswordAuthenticationToken}
     *
     * @param loginRequest {@link LoginRequest}
     * @return jwt
     */
    public String login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getAccount(), loginRequest.getPassword());
        // 内部会调用 UserDetailsServiceImpl.loadUserByUsername 方法
        Authentication authenticate = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 需手动设置上下文
        successfulAuthentication(authenticate);
        return jwtProvider.createJwt(loginUser.getUsername());
    }

    /**
     * {@link AbstractAuthenticationProcessingFilter#doFilter(ServletRequest, ServletResponse, FilterChain)}
     *
     * @param authenticate {@link Authentication}
     */
    private void successfulAuthentication(Authentication authenticate) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticate);
        SecurityContextHolder.setContext(context);
    }
}
