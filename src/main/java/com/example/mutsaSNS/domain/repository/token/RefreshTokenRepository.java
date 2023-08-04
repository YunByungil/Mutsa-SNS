package com.example.mutsaSNS.domain.repository.token;

import com.example.mutsaSNS.domain.entity.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
