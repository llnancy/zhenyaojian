package com.sunchaser.shushan.zhenyaojian.framework.service;

import com.sunchaser.shushan.zhenyaojian.framework.model.request.LoginRequest;
import com.sunchaser.shushan.zhenyaojian.framework.security.LoginUser;
import com.sunchaser.shushan.zhenyaojian.framework.service.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public String login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getAccount(), loginRequest.getPassword());
        // 内部会调用 UserDetailsServiceImpl.loadUserByUsername 方法
        Authentication authenticate = authenticationManager.authenticate(authentication);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        return jwtProvider.createJwt(loginUser.getUsername());
    }
}
