package com.example.mutsaSNS.dto.user.request;

import com.example.mutsaSNS.domain.entity.enums.Role;
import com.example.mutsaSNS.domain.entity.user.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequestDto {

    private String username;
    private String password;
    private String email;
    private String phone;

    @Builder
    public UserJoinRequestDto(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User toEntity(String password) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .role(Role.USER)
                .build();
    }
}
