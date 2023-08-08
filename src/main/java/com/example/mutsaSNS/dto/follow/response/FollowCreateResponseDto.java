package com.example.mutsaSNS.dto.follow.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FollowCreateResponseDto {

    private Long userId;
    private boolean state;

    public FollowCreateResponseDto(final Long userId, final boolean state) {
        this.userId = userId;
        this.state = state;
    }
}
