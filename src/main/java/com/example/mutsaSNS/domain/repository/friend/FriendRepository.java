package com.example.mutsaSNS.domain.repository.friend;

import com.example.mutsaSNS.domain.entity.enums.FriendRequestStatus;
import com.example.mutsaSNS.domain.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsBySenderIdAndReceiverIdAndStatus(Long senderId, Long receiverId, FriendRequestStatus status);

    List<Friend> findAllByReceiverIdAndStatus(Long receiverId, FriendRequestStatus status);
}
