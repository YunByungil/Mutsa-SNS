package com.example.mutsaSNS.service.follow;

import com.example.mutsaSNS.domain.entity.follow.Follow;
import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.follow.FollowRepository;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.follow.response.FollowCreateResponseDto;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowCreateResponseDto createFollow(final Long userId, final Long myId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        User myAccount = userRepository.findById(myId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        Optional<Follow> findFollow = followRepository.findByFollowerIdAndFollowingId(myId, userId);

        if (findFollow.isEmpty()) {
            followRepository.save(Follow.builder()
                    .follower(myAccount)
                    .following(user)
                    .build());
            return new FollowCreateResponseDto(userId, true);
        }

        followRepository.delete(findFollow.get());
        return new FollowCreateResponseDto(userId, false);
    }
}
