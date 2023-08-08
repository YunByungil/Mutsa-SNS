package com.example.mutsaSNS.controller.friend;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.friend.response.FriendCreateResponseDto;
import com.example.mutsaSNS.dto.friend.response.FriendRequestListResponseDto;
import com.example.mutsaSNS.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend/{userId}")
    public Response<FriendCreateResponseDto> requestFriend(@PathVariable final Long userId,
                                                          final Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        return Response.success(friendService.requestFriend(userId, myId));
    }

    @GetMapping("/friend")
    public Response<List<FriendRequestListResponseDto>> getFriendRequest(final Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        return Response.success(friendService.getFriendRequest(myId));
    }
}
