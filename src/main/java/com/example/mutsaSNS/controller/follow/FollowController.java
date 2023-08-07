package com.example.mutsaSNS.controller.follow;

import com.example.mutsaSNS.domain.Response;
import com.example.mutsaSNS.dto.follow.response.FollowCreateResponseDto;
import com.example.mutsaSNS.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{userId}")
    public Response<FollowCreateResponseDto> createFollow(@PathVariable final Long userId,
                                                          final Authentication authentication) {
        Long myId = Long.parseLong(authentication.getName());
        return Response.success(followService.createFollow(userId, myId));
    }
}
