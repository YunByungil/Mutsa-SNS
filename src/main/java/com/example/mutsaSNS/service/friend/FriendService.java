package com.example.mutsaSNS.service.friend;

import com.example.mutsaSNS.domain.entity.friend.Friend;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.friend.FriendRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.friend.response.FriendCreateResponseDto;
import com.example.mutsaSNS.dto.friend.response.FriendRequestListResponseDto;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mutsaSNS.domain.entity.enums.FriendRequestStatus.PENDING;
import static com.example.mutsaSNS.exception.ErrorCode.DUPLICATE_SUGGEST;
import static com.example.mutsaSNS.exception.ErrorCode.NOT_FOUND_USER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public FriendCreateResponseDto requestFriend(final Long userId, final Long myId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        User myAccount = userRepository.findById(myId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        if (friendRepository.existsBySenderIdAndReceiverIdAndStatus(myId, userId, PENDING)) {
            throw new MutsaSnsAppException(DUPLICATE_SUGGEST, DUPLICATE_SUGGEST.getMessage());
        }

        // TODO: 상대방이 나한테 요청한 상태면 수락으로 변경

        Friend friend = friendRepository.save(Friend.builder()
                .sender(myAccount)
                .receiver(user)
                .build());
        return new FriendCreateResponseDto(friend);
    }

    public List<FriendRequestListResponseDto> getFriendRequest(final Long myId) {
        List<Friend> allByReceiverId = friendRepository.findAllByReceiverIdAndStatus(myId, PENDING);

        return allByReceiverId.stream()
                .map(FriendRequestListResponseDto::new)
                .collect(Collectors.toList());
    }


}
