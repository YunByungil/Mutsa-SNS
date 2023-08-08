package com.example.mutsaSNS.domain.entity.friend;

import com.example.mutsaSNS.domain.entity.enums.FriendRequestStatus;
import com.example.mutsaSNS.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @Builder
    public Friend(final User sender, final User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = LocalDateTime.now();
        this.status = FriendRequestStatus.PENDING;
    }
}
