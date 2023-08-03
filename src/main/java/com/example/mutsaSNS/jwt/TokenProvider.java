package com.example.mutsaSNS.jwt;

import com.example.mutsaSNS.domain.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    private final JwtParser jwtParser;

    public TokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build();
    }

    public String createAccessToken(User user) {
        return Jwts.builder()
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(7200)))
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(7200)))
                .claim("id", user.getId())
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));

        return new UsernamePasswordAuthenticationToken(claims.get("id"), null, authorities);
    }

    public boolean validToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("invalidToken", e);
            return false;
        }
    }

    public Claims getClaims(String token) {
        return jwtParser.parseClaimsJws(token)
                .getBody();
    }
}
