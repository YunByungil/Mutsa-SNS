package com.example.mutsaSNS.config.filter;

import com.example.mutsaSNS.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = objectMapper.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            log.error("{}", e);
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalUserDetails userDetails = (PrincipalUserDetails) authResult.getPrincipal();
        String token = tokenProvider.createAccessToken(userDetails.getUser());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
