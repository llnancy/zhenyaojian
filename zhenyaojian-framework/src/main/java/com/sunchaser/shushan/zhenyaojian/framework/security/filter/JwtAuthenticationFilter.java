package com.sunchaser.shushan.zhenyaojian.framework.security.filter;

import com.sunchaser.shushan.zhenyaojian.framework.config.WebSecurityConfig;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * jwt filter
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/2
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (Objects.nonNull(jwt) && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }
        Claims claims = jwtService.parseJwt(jwt);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 登录接口不过滤
        return WebSecurityConfig.LOGIN_URI.equals(request.getRequestURI());
    }
}
