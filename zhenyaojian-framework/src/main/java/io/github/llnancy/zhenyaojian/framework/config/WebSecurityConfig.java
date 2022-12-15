package io.github.llnancy.zhenyaojian.framework.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import io.github.llnancy.zhenyaojian.framework.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * web security config
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/11/3
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    public static final String LOGIN_SERVLET_PATH = "/auth/login";

    public static final String LOGOUT_SERVLET_PATH = "/auth/logout";

    public static final PasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    private final AdminServerProperties adminServerProperties;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return B_CRYPT_PASSWORD_ENCODER;
    }

    /**
     * {@link CorsConfigurer#configure(HttpSecurityBuilder) }
     * CorsConfigurer#getCorsFilter(ApplicationContext)
     *
     * @return {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> all = Collections.singletonList(CorsConfiguration.ALL);
        config.setAllowedHeaders(all);
        config.setAllowedOriginPatterns(all);
        config.setAllowedMethods(all);
        config.setAllowCredentials(Boolean.TRUE);
        config.setExposedHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable();
        // Disable Frame
        http.headers().frameOptions().disable();

        // Enable Cross-Origin Resource Sharing
        http.cors();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        String contextPath = adminServerProperties.getContextPath();
        http.authorizeRequests()
                // The pattern does not contain the servlet context-path, see AntPathRequestMatcher.matcher method.
                .antMatchers(LOGIN_SERVLET_PATH)
                .permitAll()
                // Spring Boot Admin Server 的安全配置
                .antMatchers(contextPath, contextPath + "/**")
                .permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                // Disallow everything else..
                .anyRequest()
                .authenticated();

        // Handle AuthenticationException and AccessDeniedException
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        // logout
        http.logout()
                .logoutUrl(LOGOUT_SERVLET_PATH)
                .logoutSuccessHandler(logoutSuccessHandler);

        // Add JwtAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
