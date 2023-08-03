package com.example.mutsaSNS.dto.user.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserUpdateRequestDto {

    private String username;
    private String password;
    private String email;
    private String phone;
    private MultipartFile image;

    @Builder
    public UserUpdateRequestDto(String username, String password, String email, String phone, MultipartFile image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
    }
}
