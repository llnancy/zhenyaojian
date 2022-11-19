package com.sunchaser.shushan.zhenyaojian.framework.config;

import com.sunchaser.shushan.zhenyaojian.framework.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * web security config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_SERVLET_PATH = "/auth/login";

    public static final String LOGOUT_SERVLET_PATH = "/auth/logout";

    public static final PasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return B_CRYPT_PASSWORD_ENCODER;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // Enable Cross-Origin Resource Sharing
        http.cors();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // The pattern does not contain the servlet context-path, see AntPathRequestMatcher.matcher method.
                .antMatchers(LOGIN_SERVLET_PATH)
                .permitAll()
                // Disallow everything else..
                .anyRequest()
                .authenticated();

        // Handle AuthenticationException and AccessDeniedException
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http.logout()
                .logoutUrl(LOGOUT_SERVLET_PATH)
                .logoutSuccessHandler(logoutSuccessHandler);

        // Add JwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
