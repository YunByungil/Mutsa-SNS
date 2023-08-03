package com.example.mutsaSNS.config;

import com.example.mutsaSNS.config.filter.JwtFilter;
import com.example.mutsaSNS.config.filter.LoginFilter;
import com.example.mutsaSNS.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
    private final TokenProvider tokenProvider;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http
                .addFilter(new LoginFilter(authenticationManager, tokenProvider))
                .addFilterAfter(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
