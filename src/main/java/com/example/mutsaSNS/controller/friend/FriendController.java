package com.example.mutsaSNS.controller.friend;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.friend.request.FriendUpdateRequestDto;
import com.example.mutsaSNS.dto.friend.response.FriendCreateResponseDto;
import com.example.mutsaSNS.dto.friend.response.FriendRequestListResponseDto;
import com.example.mutsaSNS.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/friend/{friendId}")
    public Response<FriendCreateResponseDto> updateFriendRequest(@RequestBody final FriendUpdateRequestDto updateDto,
                                                                 @PathVariable final Long friendId,
                                                                 final Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        return Response.success(friendService.updateFriendRequest(updateDto, friendId, myId));
    }
}
