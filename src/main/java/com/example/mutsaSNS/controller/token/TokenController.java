package com.example.mutsaSNS.controller.token;

import com.example.mutsaSNS.domain.entity.token.RefreshToken;
import com.example.mutsaSNS.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public String getToken(@RequestBody RefreshToken refreshToken) {
        return tokenService.generateAccessToken(refreshToken);
    }
}
