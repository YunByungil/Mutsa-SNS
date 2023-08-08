package com.example.mutsaSNS.dto.friend.response;

import com.example.mutsaSNS.domain.entity.friend.Friend;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FriendCreateResponseDto {

    private Long id;
    private String sender;
    private String receiver;
    private String status;

    public FriendCreateResponseDto(final Friend friend) {
        this.id = friend.getId();
        this.sender = friend.getSender().getUsername();
        this.receiver = friend.getReceiver().getUsername();
        this.status = friend.getStatus().name();
    }
}
