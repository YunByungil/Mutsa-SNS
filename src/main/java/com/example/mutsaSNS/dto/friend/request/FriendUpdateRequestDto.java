package com.example.mutsaSNS.dto.friend.request;

import com.example.mutsaSNS.domain.entity.enums.FriendRequestStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FriendUpdateRequestDto {

    private FriendRequestStatus state;

    @Builder
    public FriendUpdateRequestDto(final FriendRequestStatus state) {
        this.state = state;
    }
}
