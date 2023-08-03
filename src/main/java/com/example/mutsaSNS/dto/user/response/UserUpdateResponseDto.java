package com.example.mutsaSNS.dto.user.response;

import com.example.mutsaSNS.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserUpdateResponseDto {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String image;

    public UserUpdateResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.image = user.getImage();
    }
}
