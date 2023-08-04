package com.example.mutsaSNS.service.token;

import com.example.mutsaSNS.domain.entity.token.RefreshToken;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.token.RefreshTokenRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Transactional
@Service
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public String generateAccessToken(final RefreshToken refreshToken) {
        RefreshToken refreshToken1 = refreshTokenRepository.findById(refreshToken.getRefreshToken())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findById(refreshToken1.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return tokenProvider.createAccessToken(user);
    }
}
