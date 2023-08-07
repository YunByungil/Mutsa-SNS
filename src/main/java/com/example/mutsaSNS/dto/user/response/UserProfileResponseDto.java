package com.example.mutsaSNS.dto.user.response;

import com.example.mutsaSNS.domain.entity.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserProfileResponseDto {

    private Long id;
    private String username;
    private String imageUrl;

    public UserProfileResponseDto(final User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.imageUrl = user.getImage();
    }
}
