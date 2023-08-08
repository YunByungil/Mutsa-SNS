package com.example.mutsaSNS.domain.entity.enums;

import lombok.Getter;

@Getter
public enum FriendRequestStatus {
    PENDING("보류"), ACCEPT("수락"), REJECT("거절");

    private String status;

    FriendRequestStatus(final String status) {
        this.status = status;
    }
}
